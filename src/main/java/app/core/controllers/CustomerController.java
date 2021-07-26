package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.jwt.JwtUtil;
import app.core.services.CustomerService;
import app.core.services.SharedService;

@RestController
@CrossOrigin
@RequestMapping("/api/Customer")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	@Autowired
	private SharedService sharedService;
	@Autowired
	private JwtUtil jwt;
	
	private CustomerService getService(String token) {
		service.setCustomerId(Integer.parseInt(jwt.extractId(token)));
		return service;
	}
	
	@GetMapping("/details")
	public Customer getCustomerDetails(@RequestHeader String token) {
		return getService(token).getCustomerDetails();
	}
	
	@GetMapping("/coupons")
	public List<Coupon> getCustomerCoupons(@RequestHeader String token) {
		return sharedService.setCouponImages(getService(token).getCustomerCoupons());
	}
	
	@GetMapping("/coupons/category")
	public List<Coupon> getCustomerCoupons(@RequestHeader String token, @RequestHeader Category category) {
		return sharedService.setCouponImages(getService(token).getCustomerCoupons(category));
	}
	
	@GetMapping("/coupons/max-price")
	public List<Coupon> getCustomerCoupons(@RequestHeader String token, @RequestHeader double maxPrice) {
		return sharedService.setCouponImages(getService(token).getCustomerCoupons(maxPrice));
	}
	
	@PutMapping("/coupons")
	public void purchaseCoupon(@RequestHeader String token, @RequestHeader int couponId) {
		try {
			getService(token).purchaseCoupon(couponId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
