package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.ClientService;
import app.core.services.ClientType;
import app.core.services.CompanyService;
import app.core.services.CustomerService;
import app.core.services.LoginManager;
import app.core.sessions.Session;
import app.core.sessions.SessionContext;
import app.core.webModels.AdminModel;
import app.core.webModels.CompanyModel;
import app.core.webModels.CustomerModel;

@RestController
@CrossOrigin
@RequestMapping("/api/Auth")
public class AuthController {
	
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private AdminService service;

	@Autowired
	private SessionContext sessionContext;

	@PostMapping("/login")
	public Object login(@RequestHeader String email, @RequestHeader String password, @RequestHeader String clientType) {
		// create a new session
		Session session = sessionContext.createSession();
		// load session with attributes if needed (you can load any type of object)
		ClientService service;
		ClientType userType = ClientType.valueOf(clientType);
		try {
			service = loginManager.login(email, password, userType);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		// this is how we save a client state
		session.setAttribute("service", service);
		
		Object userModel = null;
		switch(userType){
		case Customer:
				userModel = new CustomerModel(((CustomerService) service).getCustomerDetails(), session.token);
				System.out.println(userModel);
				break;
		case Company:
				userModel = new CompanyModel(((CompanyService) service).getCompanyDetails(), session.token);
				break;
		case Administrator:
				userModel = new AdminModel(email, password, session.token);
				break;
		}
		return userModel;
	}
	
	@PostMapping("/register/company")
	public Object registerCompany(@RequestHeader String email, @RequestHeader String password, @RequestHeader String name) {
		Company company = new Company(name, email, password);
		try {
			service.addCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
		return login(email, password, "Company");
	}
	
	@PostMapping("/register/customer")
	public Object registerCustomer(@RequestHeader String email, @RequestHeader String password, @RequestHeader String firstName, @RequestHeader String lastName) {
		Customer customer = new Customer(firstName, lastName, email, password);
		try {
			service.addCustomer(customer);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
		return login(email, password, "Customer");
	}
	
}
