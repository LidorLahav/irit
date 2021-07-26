package app.core.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.ClientService;
import app.core.services.ClientType;
import app.core.services.CompanyService;
import app.core.services.CustomerService;
import app.core.services.LoginManager;

@Component
public class Test {
	
	private ApplicationContext ctx;
	private LoginManager loginManager;

	@Autowired
	public Test(ApplicationContext ctx, LoginManager loginManager) {
		this.ctx = ctx;
		this.loginManager = loginManager;
	}
	
	public void testAll() {
		System.out.println("***WELCOME TO MY COUPON PROJECT***\n");
		TestAdminService adminTest = ctx.getBean(TestAdminService.class);
		TestCompanyService companyTest = ctx.getBean(TestCompanyService.class);
		TestCustomerService customerTest = ctx.getBean(TestCustomerService.class);
		try {
			initialize(adminTest, companyTest);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("\n***GOODBAY***");	
	}
	
	private void initialize(TestAdminService adminTest, TestCompanyService companyTest) throws CouponSystemException {
		ClientService service;
		service = loginManager.login("admin@admin.com", "admin", ClientType.valueOf("Administrator"));
		if(service instanceof AdminService) {
			System.out.println("\n--------------------Admin was able to login successfully--------------------");
			adminTest.initializeCompaniesAndCustomers((AdminService) service);
		}
		service = loginManager.login("company2@gmail.com", "pass2", ClientType.valueOf("Company"));
		if(service instanceof CompanyService) {
			System.out.println("\n--------------------company2 was able to login successfully--------------------");
			companyTest.initializeCoupons1((CompanyService) service);
		}
		service = loginManager.login("company3@gmail.com", "pass3", ClientType.valueOf("Company"));
		if(service instanceof CompanyService) {
			System.out.println("\n--------------------company3 was able to login successfully--------------------");
			companyTest.initializeCoupons2((CompanyService) service);
		}
		service = loginManager.login("company4@gmail.com", "pass4", ClientType.valueOf("Company"));
		if(service instanceof CompanyService) {
			System.out.println("\n--------------------company4 was able to login successfully--------------------");
			companyTest.initializeCoupons3((CompanyService) service);
		}
	}
	
	private void startOperations(TestAdminService adminTest, TestCompanyService companyTest, TestCustomerService customerTest) throws CouponSystemException {
		ClientService service;
		service = loginManager.login("customer2@gmail.com", "pass2", ClientType.valueOf("Customer"));
		if(service instanceof CustomerService) {
			System.out.println("\n--------------------customer2 was able to login successfully--------------------");
			customerTest.start((CustomerService) service);
		}
		service = loginManager.login("admin@admin.com", "admin", ClientType.valueOf("Administrator"));
		if(service instanceof AdminService) {
			System.out.println("\n--------------------Admin was able to login successfully--------------------");
			adminTest.start((AdminService) service);
		}
		service = loginManager.login("company2@gmail.com", "pass2", ClientType.valueOf("Company"));
		if(service instanceof CompanyService) {
			System.out.println("\n--------------------company2 was able to login successfully--------------------");
			companyTest.start((CompanyService) service);
		}
		service = loginManager.login("company0@gmail.com", "pass0", ClientType.valueOf("Company"));
		if(service instanceof CompanyService) {
			System.out.println("\n--------------------company0 was able to login successfully--------------------");
			companyTest.start((CompanyService) service);
		}
	}

}
