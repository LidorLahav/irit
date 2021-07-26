package app.core.tests;

import java.util.List;

import org.springframework.stereotype.Component;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.services.CustomerService;

@Component
public class TestCustomerService {
	
	public TestCustomerService() {
	}
	
	/**
	 * Testing methods of CustomerFacade:
	 * getCoupons(), purchaseCoupon(coupon), getCustomerCoupons(), 
	 * getCustomerCoupons(category), getCustomerCoupons(maxPrice),
	 * getCustomerDetails()
	 * @param customerFacade
	 */
	public void start(CustomerService customerService) {
		List<Coupon> coupons = null;
		coupons = customerService.getCoupons();
		System.out.println("\nall coupons:");
		for(Coupon c: coupons) {
			System.out.println("	" + c.toString());
		}
		if(coupons.size() == 0) {
			System.out.println("	there are no coupons in the system");
		}
		System.out.println("getCoupons succeeded");
		List<Coupon> couponsByCategory = customerService.getCouponsByCategory(Category.Vacation);
		System.out.println("\nall coupons with category: " + Category.Vacation.toString());
		for(Coupon c: couponsByCategory) {
			System.out.println("	" + c.toString());
		}
		if(couponsByCategory.size() == 0) {
			System.out.println("	there are no coupons with category: " + Category.Vacation.toString() + ", in the system");
		}
		System.out.println("getCouponsByCategory succeeded");
		try {
			customerService.purchaseCoupon(coupons.get(1).getId());
			customerService.purchaseCoupon(coupons.get(5).getId());
			System.out.println("purchaseCoupon succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		List<Coupon> customerCoupons = null;
		customerCoupons = customerService.getCustomerCoupons();
		System.out.println("\ncustomer coupons:");
		for(Coupon c: customerCoupons) {
			System.out.println("	" + c.toString());
		}
		if(customerCoupons.size() == 0) {
			System.out.println("	this customer don't purchase coupons");
		}
		System.out.println("getCustomerCoupons succeeded");
		List<Coupon> customerCouponsByCategory = null;
		Category category = Category.Spa;
		customerCouponsByCategory = customerService.getCustomerCoupons(category);
		System.out.println("\ncustomer coupons by category: " + category.toString());
		for(Coupon c: customerCouponsByCategory) {
			System.out.println("	" + c.toString());
		}
		if(customerCouponsByCategory.size() == 0) {
			System.out.println("	this customer don't purchase coupons with category " + category);
		}
		System.out.println("getCustomerCoupons by category succeeded");
		List<Coupon> customerCouponsMaxPrice = null;
		double maxPrice = 90;
		customerCouponsMaxPrice = customerService.getCustomerCoupons(maxPrice);
		System.out.println("\ncustomer coupons by max price: " + maxPrice);
		for(Coupon c: customerCouponsMaxPrice) {
			System.out.println("	" + c.toString());
		}
		if(customerCouponsByCategory.size() == 0) {
			System.out.println("	this customer don't purchase coupons with price less then " + maxPrice);
		}
		System.out.println("getCustomerCoupons by max price succeeded");
		try {
			String title = "coupon2";
			Coupon couponByTitle = null;
			couponByTitle =	customerService.getCouponByTitle(title);
			if(couponByTitle != null) {
				System.out.println("coupon with title: " + title + " is: " + couponByTitle.toString());
			} else {
				System.out.println("There is not coupon with title: " + title + " in the database");
			}
			System.out.println("getCouponByTitle succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(customerService.getCustomerDetails().toString());
		System.out.println("getCustomerDetails succeeded");
	}
}
