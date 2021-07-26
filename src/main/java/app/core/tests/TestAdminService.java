package app.core.tests;

import java.util.List;

import org.springframework.stereotype.Component;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;

@Component
public class TestAdminService {

	public TestAdminService() {
	}
	
	/**
	 * Adds companies and customers to database,
	 * testing the methods addCompany(company) and addCustomer(customer) of AdminFacade
	 * @param adminFacade
	 */
	public void initializeCompaniesAndCustomers(AdminService adminService) {
		for(int i = 0; i < 5; i++) {
			try {
				adminService.addCompany(createCompany("company"+i+"@gmail.com", "company"+i, "pass"+i));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("addCompany succeeded"); 
		for(int i = 0; i < 5; i++) {
			try {
				adminService.addCustomer(createCustomer("customer"+i+"@gmail.com", "firstName"+i, "lastName"+i, "pass"+i));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("addCustomer succeeded"); 
	}
	
	/**
	 * Testing methods of AdminFacade:
	 * getAllCompanies(), getAllCustomers(), deleteCompany(companyID),
	 * deleteCustomer(customerID), getOneCompany(companyID), 
	 * getOneCustomer(customerID), updateCompany(company), updateCustomer(customer),
	 * getCompanyCoupons(companyID), getCustomerCoupons(customerID)
	 * @param adminFacade
	 */
	public void start(AdminService adminService) {
		List<Company> companies = null;
		companies = adminService.getAllCompanies();
		System.out.println("getAllCompanies succeeded");
		List<Customer> customers = null;
		customers = adminService.getAllCustomers();
		System.out.println("getAllCustomers succeeded");
		if(companies != null && companies.size() > 0) {
			try {
				adminService.deleteCompany(companies.get(0).getId());
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("deleteCompany succeeded");
		} else {
			System.out.println("ERROR: companies array is empty");
		}
		if(customers != null && customers.size() > 0) {
			try {
				adminService.deleteCustomer(customers.get(3).getId());
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("deleteCustomer succeeded");
		} else {
			System.out.println("ERROR: customers array is empty");
		}
		try {
			if(companies != null && companies.size() > 0) {
				System.out.println(adminService.getOneCompany(companies.get(1).getId()).toString());
				System.out.println("getOneCompany succeeded");
			} else {
				System.out.println("ERROR: companies array is empty");
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(customers != null && customers.size() > 0) {
				System.out.println(adminService.getOneCustomer(customers.get(1).getId()).toString());
				System.out.println("getOneCustomer succeeded");
			} else {
				System.out.println("ERROR: customers array is empty");
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(companies != null && companies.size() > 0) {
				companies.get(1).setPassword("passUpdate");
				adminService.updateCompany(companies.get(1));
				System.out.println("updateCompany succeeded");
			} else {
				System.out.println("ERROR: companies array is empty");
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(customers != null && customers.size() > 0) {
				customers.get(1).setPassword("passUpdate");
				adminService.updateCustomer(customers.get(1));
				System.out.println("updateCustomer succeeded");
			} else {
				System.out.println("ERROR: customers array is empty");
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		List<Coupon> companyCoupons = null;
		companyCoupons = adminService.getCompanyCoupons(companies.get(2).getId());
		System.out.println("\ncompany coupons:");
		for(Coupon c: companyCoupons) {
			System.out.println("	" + c.toString());
		}
		if(companyCoupons.size() == 0) {
			System.out.println("	this company don't have coupons");
		}
		System.out.println("getCompanyCoupons succeeded");
		List<Coupon> customerCoupons = null;
		customerCoupons = adminService.getCustomerCoupons(customers.get(2).getId());
		System.out.println("\ncustomer coupons:");
		for(Coupon c: customerCoupons) {
			System.out.println("	" + c.toString());
		}
		if(customerCoupons.size() == 0) {
			System.out.println("	this customer don't have coupons");
		}
		System.out.println("getCustomerCoupons succeeded");
	}
	
	private Company createCompany(String email, String name, String password) {
		Company company = new Company();
		company.setEmail(email);
		company.setName(name);
		company.setPassword(password);
		return company;
	}
	
	private Customer createCustomer(String email, String firstName, String lastName, String password) {
		Customer customer = new Customer();
		customer.setEmail(email);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setPassword(password);
		return customer;
	}
	
}
