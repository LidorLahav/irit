package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.services.AdminService;

@RestController
@CrossOrigin
@RequestMapping("/api/Admin")
public class AdminController {
	
	@Autowired
	private AdminService service;
	@Autowired
	private ConfigurableApplicationContext ctx;
	
	@PutMapping("/shutdown")
	public String shutdown(@RequestHeader String token, @RequestParam long millis) {
		closeSpringContext(millis);
		return "closing spring app in " + millis + " millis";
	}

	private void closeSpringContext(long millis) {
		System.out.println("application will go down in " + millis + " ms");
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(millis);
					ctx.close();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
	
	@GetMapping("/companies/one")
	public Company getOneCompany(@RequestHeader String token, @RequestHeader int id) {
		try {
			return service.getOneCompany(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/customers/one")
	public Customer getOneCustomer(@RequestHeader String token, @RequestHeader int id) {
		try {
			return service.getOneCustomer(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/companies")
	public List<Company> getAllCompanies(@RequestHeader String token) {
		return service.getAllCompanies();
	}
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers(@RequestHeader String token) {
		return service.getAllCustomers();
	}
	
	@PostMapping("/companies")
	public int addCompany(@RequestHeader String token, @RequestBody Company company) {
		try {
			return service.addCompany(company);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	
	@PostMapping("/customers")
	public int addCustomer(@RequestHeader String token, @RequestBody Customer customer) {
		try {
			return service.addCustomer(customer);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	
	@PutMapping("/companies")
	public void updateCompany(@RequestHeader String token, @RequestBody Company company) {
		try {
			service.updateCompany(company);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@PutMapping("/customers")
	public void updateCustomer(@RequestHeader String token, @RequestBody Customer customer) {
		try {
			service.updateCustomer(customer);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@DeleteMapping("/companies")
	public void deleteCompany(@RequestHeader String token, @RequestHeader int id) {
		try {
			service.deleteCompany(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@DeleteMapping("/customers")
	public void deleteCustomer(@RequestHeader String token, @RequestHeader int id) {
		try {
			service.deleteCustomer(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/companies/one/coupons")
	public List<Coupon> getCompanyCoupons(@RequestHeader String token, @RequestHeader int id) {
		return service.getCompanyCoupons(id);
	}
	
	@GetMapping("/customers/one/coupons")
	public List<Coupon> getCustomerCoupons(@RequestHeader String token, @RequestHeader int id) {
		return service.getCustomerCoupons(id);
	}

}
