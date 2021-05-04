package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.exceptions.CouponSystemException;
import app.core.services.ClientService;
import app.core.services.ClientType;
import app.core.services.LoginManager;
import app.core.sessions.Session;
import app.core.sessions.SessionContext;

@RestController
@CrossOrigin
@RequestMapping("/api/Auth")
public class LoginController {
	
	@Autowired
	private LoginManager loginManager;

	@Autowired
	private SessionContext sessionContext;

	@PostMapping("/login")
	public String login(@RequestHeader String email, @RequestHeader String password, @RequestHeader ClientType clientType) {
		// create a new session
		Session session = sessionContext.createSession();
		// load session with attributes if needed (you can load any type of object)
		ClientService service;
		try {
			service = loginManager.login(email, password, clientType);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
		// this is how we save a client state
		session.setAttribute("service", service);
		// return the token to the caller (client)
		return session.token;
	}
	
}
