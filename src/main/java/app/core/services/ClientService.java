package app.core.services;

import app.core.exceptions.CouponSystemException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

public abstract class ClientService {
	
	protected CompanyRepository companyRepository;
	protected CouponRepository couponRepository;
	protected CustomerRepository customerRepository;
	
	public ClientService(CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository) {
		this.companyRepository = companyRepository;
		this.couponRepository = couponRepository;
		this.customerRepository = customerRepository;
	}
	
	public abstract boolean login(String email, String password) throws CouponSystemException;
	
}
