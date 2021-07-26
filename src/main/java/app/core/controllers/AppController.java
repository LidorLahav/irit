package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.services.SharedService;

@RestController
@CrossOrigin
@RequestMapping("/App")
public class AppController {
	
	@Autowired
	private SharedService service;
	
	@GetMapping("/coupons")
	public List<Coupon> getCoupons(@RequestHeader String sortBy ) {
		return service.getCoupons(sortBy);
	}
	
	@GetMapping("/coupons/category")
	public List<Coupon> getCouponsByCategory(@RequestHeader String category, @RequestHeader String sortBy ) {
		return service.getCouponsByCategory(Category.valueOf(category), sortBy);
	}
	
	@GetMapping("/coupons/one")
	public Coupon getCoupon(@RequestHeader int id ) {
		try {
			return service.getCoupon(id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
}
