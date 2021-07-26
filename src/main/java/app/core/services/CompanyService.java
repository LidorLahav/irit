package app.core.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
@Transactional
@Scope("prototype")
public class CompanyService extends ClientService {
	
	private int companyId;
	
	@Autowired
	public CompanyService(CompanyRepository companyRepository, CouponRepository couponRepository,
			CustomerRepository customerRepository) {
		super(companyRepository, couponRepository, customerRepository);
	}
	
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	
	/**
	 * First checks if company exists in database by his email,
	 * then validate the password.
	 * Finally initialize the filed companyId to be the company id.
	 * @param email
	 * @param password
	 * @return true if company exists in database
	 * @throws CouponSystemException
	 */
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		Company company = companyRepository.findFirstByEmail(email);
		if(company == null) {
			throw new CouponSystemException("Company email: " + email + ", was not found.");
		}
		if(!company.getPassword().equals(password)) {
			throw new CouponSystemException("Wrong password");
		}
		this.companyId = company.getId();
		return true;
	}
	
	/**
	 * First checks if there is not another coupon with the same id,
	 * after that checks if the coupon title of the @param(coupon) is exists in database,
	 * than if not, adds the @param(coupon) of the company to database.
	 * @throws CouponSystemException in case the coupon title of the company already exist in the database
	 */
	public int addCoupon(Coupon coupon) throws CouponSystemException {
		if(couponRepository.existsById(coupon.getId())) {
			throw new CouponSystemException("The id of the coupon already exist in the system. Can't add coupon with the same id.");
		}
		Coupon dbCoupon = couponRepository.findFirstByTitle(coupon.getTitle());
		if(dbCoupon != null) {
			throw new CouponSystemException("The coupon title : " + coupon.getTitle() +  " of company with id: " + this.companyId  + " already exist in the database. Can't add coupon with the same title.");
		}
		coupon.setCompany(companyRepository.findFirstById(this.companyId));
		int id = couponRepository.save(coupon).getId();
		coupon.setId(id);
		return id;
	}
	
	/**
	 * First checks if the @param(coupon) exists in database,
	 * then checks if the @param(coupon) belong to another company.
	 * Finally Updates the @param(coupon) properties in the database
	 * @throws CouponSystemException in case the coupon not exists in the database
	 * or belongs to other company
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Coupon dbCoupon = couponRepository.findFirstById(coupon.getId());
		if(dbCoupon == null) {
			throw new CouponSystemException("The coupon with id: " + coupon.getId() + " not exists in the database.");
		}
		if(coupon.getIdC() != this.companyId) {
			throw new CouponSystemException("The coupon with id: " +coupon.getId()+ " belongs to other company");
		}
		dbCoupon.setTitle(coupon.getTitle());
		dbCoupon.setCategory(coupon.getCategory());
		dbCoupon.setDescription(coupon.getDescription());
		dbCoupon.setAmount(coupon.getAmount());
		dbCoupon.setPrice(coupon.getPrice());
		dbCoupon.setStartDate(coupon.getStartDate());
		dbCoupon.setEndDate(coupon.getEndDate());
	}
	
	public void deleteCoupon(int couponId) throws CouponSystemException {
		if(!couponRepository.existsById(couponId)) {
			throw new CouponSystemException("The coupon not exist in the system.");
		}
		couponRepository.deleteById(couponId);
	}
	
	public List<Coupon> getCompanyCoupons() {
		return couponRepository.findByCompanyId(this.companyId);
	}
	
	public List<Coupon> getCompanyCoupons(Category category) {
		return couponRepository.findByCompanyIdAndCategory(this.companyId, category);
	}
	
	public List<Coupon> getCompanyCoupons(double maxPrice) {
		return couponRepository.findByCompanyIdAndPriceLessThanEqual(this.companyId, maxPrice);
	}
	
	public Company getCompanyDetails() {
		return companyRepository.findFirstById(this.companyId);
	}
	
	public void deleteImages(int couponId, Category category, String[] imagesToDelete) throws CouponSystemException {
		String directoryPath =  "C:\\Users\\lidor\\eclipse-workspace\\serverCouponProjectJWT\\src\\main\\resources\\static\\pics\\"
				+ category.toString() + "\\" + couponId;
		File directory = new File(directoryPath);
		File[] images = directory.listFiles();
		for(int i = 0; i < imagesToDelete.length; i++) {
			for(int j = 0; j < images.length; j++) {
				if(images[j].getName().equals(imagesToDelete[i])) {
					try {
						images[j].delete();
					} catch(Exception e) {
						throw new CouponSystemException(e.getMessage());
					}
					
					break;
				}
			}
		}
	}
	
	public void addImages(int couponId, Category category, MultipartFile[] imagesFiles) throws CouponSystemException {
		String directoryPath =  "C:\\Users\\lidor\\eclipse-workspace\\serverCouponProjectJWT\\src\\main\\resources\\static\\pics\\"
				+ category.toString() + "\\" + couponId;
		Path fileStoragePath = Paths.get(directoryPath).toAbsolutePath();
		File directory = new File(directoryPath);
		if(!directory.exists()) {
			 directory.mkdir();
		}
		for(int i = 0; i < imagesFiles.length; i++) {
			String fileName = imagesFiles[i].getOriginalFilename();
			if (fileName.contains("..")) {
				throw new CouponSystemException("file name contains ilegal caharacters");
			}
			try {
				Path targetLocation = fileStoragePath.resolve(fileName);
				Files.copy(imagesFiles[i].getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new CouponSystemException("storing file " + fileName + " failed", e);
			}
		}
	}
	
	public void changeCouponDirectory(int couponId, Category oldCategory, Category newCategory) throws CouponSystemException {
		String oldDirectoryPath =  "C:\\Users\\lidor\\eclipse-workspace\\serverCouponProjectJWT\\src\\main\\resources\\static\\pics\\"
				+ oldCategory.toString() + "\\" + couponId;
		String newDirectoryPath =  "C:\\Users\\lidor\\eclipse-workspace\\serverCouponProjectJWT\\src\\main\\resources\\static\\pics\\"
				+ newCategory.toString() + "\\" + couponId;
		Path fileStoragePath = Paths.get(newDirectoryPath).toAbsolutePath();
		File oldDirectory = new File(oldDirectoryPath);
		File newDirectory = new File(newDirectoryPath);
		if(!newDirectory.exists()) {
			newDirectory.mkdir();
		}
		if(oldDirectory.exists()) {
			File[] images = oldDirectory.listFiles();
			if(images != null) {
				for(int i = 0; i < images.length; i++) {
					String fileName = images[i].getName();
					if (fileName.contains("..")) {
						throw new CouponSystemException("file name contains ilegal caharacters");
					}
					try {
						Path targetLocation = fileStoragePath.resolve(fileName);
						Files.copy(images[i].toPath(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						throw new CouponSystemException("storing file " + fileName + " failed", e);
					}
				}
			}
			deleteCouponDirectory(couponId, oldCategory);
		}
	}
	
	public void deleteCouponDirectory(int couponId, Category category) throws CouponSystemException {
		String directoryPath =  "C:\\Users\\lidor\\eclipse-workspace\\serverCouponProjectJWT\\src\\main\\resources\\static\\pics\\"
				+ category.toString() + "\\" + couponId;
		File directory = new File(directoryPath);
		File[] images = directory.listFiles();
		if(images != null) {
			for(int i = 0; i < images.length; i++) {
				try {
					images[i].delete();
				} catch(Exception e) {
					throw new CouponSystemException(e.getMessage());
				}
			}
		}
		directory.delete();
	}

}
