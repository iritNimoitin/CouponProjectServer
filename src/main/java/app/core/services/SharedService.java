package app.core.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.repositories.CouponRepository;

@Service
@Transactional
public class SharedService {
	
	private CouponRepository couponRepository;
	
	@Autowired
	public SharedService(CouponRepository couponRepository) {
		this.couponRepository = couponRepository;
	}
	
	public Page<Coupon> getCoupons(int pageNumber, int itemsPerPage, String sortBy) {
		Pageable page = PageRequest.of(pageNumber, itemsPerPage, Sort.by(sortBy));
		return couponRepository.findAll(page);
	}
	
	public Page<Coupon> getCouponsByCategory(Category category, int pageNumber, int itemsPerPage, String sortBy) {
		Pageable page = PageRequest.of((pageNumber)*itemsPerPage, itemsPerPage, Sort.by(sortBy));
		return couponRepository.findByCategory(category, page);
	}
}
