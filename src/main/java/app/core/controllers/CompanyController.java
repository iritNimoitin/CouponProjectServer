package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.services.CompanyService;
import app.core.sessions.SessionContext;

@RestController
@CrossOrigin
@RequestMapping("/api/Company")
public class CompanyController {

	@Autowired
	private SessionContext sessionContext;
	
	private CompanyService getService(String token) {
		return (CompanyService) sessionContext.getSession(token).getAttribute("service");
	}
	
	@GetMapping("/details")
	public Company getCompanyDetails(@RequestHeader String token) {
		return getService(token).getCompanyDetails();
	}
	
	@GetMapping("/coupons")
	public List<Coupon> getCompanyCoupons(@RequestHeader String token){
		return getService(token).getCompanyCoupons();
	}
	
	@GetMapping("/coupons/max-price")
	public List<Coupon> getCompanyCoupons(@RequestHeader String token, @RequestHeader double maxPrice){
		return getService(token).getCompanyCoupons(maxPrice);
	}
	
	@GetMapping("/coupons/category")
	public List<Coupon> getCompanyCoupons(@RequestHeader String token, @RequestHeader Category category) {
		return getService(token).getCompanyCoupons(category);
	}
	
	@PostMapping("/coupons")
	public void addCoupon(@RequestHeader String token, @RequestBody Coupon coupon) {
		try {
			getService(token).addCoupon(coupon);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	
	@PutMapping("/coupons")
	public void updateCoupon(@RequestHeader String token, @RequestBody Coupon coupon) {
		try {
			getService(token).updateCoupon(coupon);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@DeleteMapping("/coupons")
	public void deleteCoupon(@RequestHeader String token, @RequestHeader int id) {
		try {
			getService(token).deleteCoupon(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
}
