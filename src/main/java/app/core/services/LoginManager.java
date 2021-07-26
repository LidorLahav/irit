package app.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.exceptions.CouponSystemException;

@Component
public class LoginManager {

	private ApplicationContext ctx;
	
	@Autowired
	public LoginManager(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	/**
	 * According to @param(clientType) trying to login to the system
	 * with @param(email) and @param(password)
	 * @return client facade in accordance to clientType
	 * @throws CouponSystemException
	 */
	public ClientService login(String email, String password, ClientType clientType) throws CouponSystemException {
		ClientService service = null;
		switch (clientType) {
		case Administrator:
			service = (AdminService) ctx.getBean(AdminService.class);		
			break;
		case Company:
			service = (CompanyService) ctx.getBean(CompanyService.class);
			break;
		case Customer:
			service = (CustomerService) ctx.getBean(CustomerService.class);
			break;
		}
		service.login(email, password);
		return service;
	}
}
