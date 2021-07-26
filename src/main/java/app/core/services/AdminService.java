package app.core.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

@Service
@Transactional
public class AdminService extends ClientService {
	
	@Autowired
	public AdminService(CompanyRepository companyRepository, CouponRepository couponRepository,
			CustomerRepository customerRepository) {
		super(companyRepository, couponRepository, customerRepository);
	}
	
	@Override
	public boolean login(String email, String password) {
		if(email.equals("admin@admin.com") && password.equals("admin")){
			return true;
		}
		return false;
	}
	
	public int addCompany(Company company) throws CouponSystemException {
		if(companyRepository.existsById(company.getId())) {
			throw new CouponSystemException("The id of the company already exist in the system. Can't add company with the same id.");
		}
		if(companyRepository.existsByName(company.getName())) {
			throw new CouponSystemException("The name of the company already exist in the system. Can't add company with the same name.");
		}
		if(companyRepository.existsByEmail(company.getEmail())) {
			throw new CouponSystemException("the email of the company already exist in the system. Can't add company with the same email.");
		}
		return companyRepository.save(company).getId();
	}
	
	public void updateCompany(Company company) throws CouponSystemException {
		Company dbCompany = companyRepository.findFirstById(company.getId());
		if(dbCompany == null) {
			throw new CouponSystemException("The company with id: " + company.getId() + " not exist in the system.");
		}
		if(!dbCompany.getName().equals(company.getName())) {
			throw new CouponSystemException("Can't update the name of the company.");
		}
		dbCompany.setEmail(company.getEmail());
		dbCompany.setPassword(company.getPassword());
	}
	
	/**
	 * first deletes all company coupons and the coupons purchases from database, 
	 * then deletes company from database
	 * @param email
	 * @param password
	 * @return true if company exists in database
	 * @throws CouponSystemException
	 */
	public void deleteCompany(int companyId) throws CouponSystemException {
		if(!companyRepository.existsById(companyId)) {
			throw new CouponSystemException("The company not exist in the system.");
		}
		companyRepository.deleteById(companyId);
	}
	
	public List<Company> getAllCompanies(){
		return companyRepository.findAll();
	}
	
	public Company getOneCompany(int companyId) throws CouponSystemException {
		Company company = companyRepository.findFirstById(companyId);
		if(company == null) {
			throw new CouponSystemException("Company with id: " + companyId + ", is not exists in database");
		}
		return company;
	}
	
	public int addCustomer(Customer customer) throws CouponSystemException {
		if(customerRepository.existsById(customer.getId())) {
			throw new CouponSystemException("The id of the customer already exist in the system. Can't add customer with the same id.");
		}
		if(customerRepository.existsByEmail(customer.getEmail())) {
			throw new CouponSystemException("The email of the customer already exist in the database. Can't add customer with the same email.");
		}
		return customerRepository.save(customer).getId();
	}
	
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Customer dbCustomer = customerRepository.findFirstById(customer.getId());
		if(dbCustomer == null) {
			throw new CouponSystemException("The customer with id: " + customer.getId() + " not exist in the system.");
		}
		dbCustomer.setEmail(customer.getEmail());
		dbCustomer.setFirstName(customer.getFirstName());
		dbCustomer.setLastName(customer.getLastName());
		dbCustomer.setPassword(customer.getPassword());
	}
	
	public void deleteCustomer(int customerId) throws CouponSystemException {
		if(!customerRepository.existsById(customerId)) {
			throw new CouponSystemException("The customer not exist in the system.");
		}
		customerRepository.deleteById(customerId);
	}
	
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
	public Customer getOneCustomer(int customerId) throws CouponSystemException {
		Customer customer = customerRepository.findFirstById(customerId);
		if(customer == null) {
			throw new CouponSystemException("Customer with id: " + customerId + ", is not exists in database");
		}
		return customer;
	}
	
	public List<Coupon> getCompanyCoupons(int companyId) {
		return couponRepository.findByCompanyId(companyId);
	}
	
	public List<Coupon> getCustomerCoupons(int customerId){
		return couponRepository.findByCustomersId(customerId);
	}
	
	
}
