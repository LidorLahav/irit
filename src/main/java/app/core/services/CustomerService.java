package app.core.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

@Service
@Transactional
public class CustomerService extends ClientService {
	
	private int customerId;
	
	@Autowired
	public CustomerService(CompanyRepository companyRepository, CouponRepository couponRepository,
			CustomerRepository customerRepository) {
		super(companyRepository, couponRepository, customerRepository);
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * First checks if customer exists in database by his email,
	 * then validate the password.
	 * Finally initialize the filed customerId to be the customer id.
	 * @param email
	 * @param password
	 * @return true if customer exists in database
	 * @throws CouponSystemException
	 */
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		Customer customer = customerRepository.findFirstByEmail(email);
		if(customer == null) {
			throw new CouponSystemException("Customer email: " + email + ", was not found.");
		}
		if(!customer.getPassword().equals(password)) {
			throw new CouponSystemException("Wrong password");
		}
		this.customerId = customer.getId();
		return true;
	}
	
	/**
	 * Adds coupon purchase to database if:
	 * 1. The customer does not purchased it before
	 * 2. The amount of the coupon is above zero
	 * 3. The coupon is still valid
	 * Then reduce the amount of the coupon by one.
	 * @throws CouponSystemException
	 */
	public void purchaseCoupon(int couponId) throws CouponSystemException {
		Coupon dbCoupon = couponRepository.findFirstById(couponId);
		if(dbCoupon == null) {
			throw new CouponSystemException("The coupon with id: " + couponId + " not exists in the database.");
		}
		if (couponRepository.existsByIdAndCustomersId(dbCoupon.getId(), this.customerId)) {
			throw new CouponSystemException("You already purchesed this coupon " + dbCoupon.toString() + " in the past . you can't purches the same coupon more than once");
		}
		if (dbCoupon.getAmount() == 0) {
			throw new CouponSystemException("The coupon " + dbCoupon.toString() + "  is currently out of stock. Please select another coupon"); 
		}
		if (dbCoupon.getEndDate().isBefore(LocalDate.now())) {
			throw new CouponSystemException("The expiration date of the coupon : " + dbCoupon.toString()+ " has arrived so it is not possible to purchase it. please select another coupon");
		}
		dbCoupon.addCustomer(getCustomerDetails());
		dbCoupon.setAmount(dbCoupon.getAmount()-1);
	}
	
	public List<Coupon> getCustomerCoupons() {
		return couponRepository.findByCustomersId(this.customerId); 
	}
	
	public List<Coupon> getCustomerCoupons(Category category) {
		return couponRepository.findByCustomersIdAndCategory(this.customerId, category);
	}
	
	public List<Coupon> getCustomerCoupons(double maxPrice) {
		return couponRepository.findByCustomersIdAndPriceLessThanEqual(this.customerId, maxPrice);
	}
	
	public List<Coupon> getCoupons() {
		return couponRepository.findAll();
	}
	
	public List<Coupon> getCouponsByCategory(Category category) {
		return couponRepository.findByCustomersIdAndCategory(this.customerId, category);
	}
	
	public Coupon getCouponByTitle(String title) throws CouponSystemException {
		Coupon dbCoupon = couponRepository.findFirstByTitle(title);
		if(dbCoupon == null) {
			throw new CouponSystemException("The coupon with title: " + title + ", does not exist in database.");
		}
		return dbCoupon;
	}
	
	public Customer getCustomerDetails() {
		return customerRepository.findFirstById(this.customerId);
	}
	
}
