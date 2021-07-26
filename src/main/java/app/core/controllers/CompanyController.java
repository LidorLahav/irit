package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.jwt.JwtUtil;
import app.core.services.CompanyService;
import app.core.services.SharedService;

@RestController
@CrossOrigin
@RequestMapping("/api/Company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	@Autowired
	private SharedService sharedService;
	@Autowired
	private JwtUtil jwt;
	
	private CompanyService getService(String token) {
		companyService.setCompanyId(Integer.parseInt(jwt.extractId(token)));
		return companyService;
	}
	
	@GetMapping("/details")
	public Company getCompanyDetails(@RequestHeader String token) {
		return getService(token).getCompanyDetails();
	}
	
	@GetMapping("/coupons")
	public List<Coupon> getCompanyCoupons(@RequestHeader String token){
		return sharedService.setCouponImages(getService(token).getCompanyCoupons());
	}
	
	@GetMapping("/coupons/max-price")
	public List<Coupon> getCompanyCoupons(@RequestHeader String token, @RequestHeader double maxPrice){
		return sharedService.setCouponImages(getService(token).getCompanyCoupons(maxPrice));
	}
	
	@GetMapping("/coupons/category")
	public List<Coupon> getCompanyCoupons(@RequestHeader String token, @RequestHeader Category category) {
		return sharedService.setCouponImages(getService(token).getCompanyCoupons(category));
	}
	
	@PostMapping("/coupons/images")
	public int addCoupon(@RequestHeader String token, @ModelAttribute Coupon coupon, @RequestParam("images") MultipartFile[] files) {
		try {
			int id = getService(token).addCoupon(coupon);
			getService(token).addImages(id, coupon.getCategory(), files);
			return id;
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	
	@PostMapping("/coupons")
	public int addCoupon(@RequestHeader String token, @ModelAttribute Coupon coupon) {
		try {
			return getService(token).addCoupon(coupon);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	
	@PutMapping("/coupons/images")
	public void updateCoupon(@RequestHeader String token, @ModelAttribute Coupon coupon, @RequestParam("images") MultipartFile[] imagesFiles, @RequestHeader String[] imagesToDelete, @RequestHeader String oldCategory) {
		try {
			getService(token).deleteImages(coupon.getId(), Category.valueOf(oldCategory), imagesToDelete);
			if(!coupon.getCategory().equals(Category.valueOf(oldCategory))) {
				getService(token).changeCouponDirectory(coupon.getId(), Category.valueOf(oldCategory), coupon.getCategory());
			}
			getService(token).addImages(coupon.getId(), coupon.getCategory(), imagesFiles);
			getService(token).updateCoupon(coupon);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@PutMapping("/coupons")
	public void updateCoupon(@RequestHeader String token, @ModelAttribute Coupon coupon, @RequestHeader String[] imagesToDelete, @RequestHeader String oldCategory) {
		try {
			getService(token).deleteImages(coupon.getId(), Category.valueOf(oldCategory), imagesToDelete);
			if(!coupon.getCategory().equals(Category.valueOf(oldCategory))) {
				getService(token).changeCouponDirectory(coupon.getId(), Category.valueOf(oldCategory), coupon.getCategory());
			}
			getService(token).updateCoupon(coupon);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@DeleteMapping("/coupons")
	public void deleteCoupon(@RequestHeader String token, @RequestHeader int couponId, @RequestHeader String category) {
		try {
			getService(token).deleteCouponDirectory(couponId, Category.valueOf(category));
			getService(token).deleteCoupon(couponId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
}
