package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.services.SharedService;

@RestController
@CrossOrigin
@RequestMapping("/App")
public class AppController {
	
	@Autowired
	private SharedService service;
	
	@GetMapping("/coupons")
	public Page<Coupon> getCoupons(@RequestHeader int pageNumber, @RequestHeader int itemsPerPage, @RequestHeader String sortBy ) {
		return service.getCoupons(pageNumber, itemsPerPage, sortBy);
	}
	
	@GetMapping("/coupons/category")
	public Page<Coupon> getCouponsByCategory(@RequestHeader String category, @RequestHeader int pageNumber, @RequestHeader int itemsPerPage, @RequestHeader String sortBy ) {
		return service.getCouponsByCategory(Category.valueOf(category), pageNumber, itemsPerPage, sortBy);
	}
	
}
