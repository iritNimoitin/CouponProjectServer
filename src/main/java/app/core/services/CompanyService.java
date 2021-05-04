package app.core.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

@Service
@Transactional
@Scope("prototype")
public class CompanyService extends ClientService {
	
	private Company company;
	
	@Autowired
	public CompanyService(CompanyRepository companyRepository, CouponRepository couponRepository,
			CustomerRepository customerRepository) {
		super(companyRepository, couponRepository, customerRepository);
	}
	
	
	/**
	 * First checks if company exists in database by his email,
	 * then validate the password.
	 * Finally initialize the filed companyId to be the company id.
	 * @param email
	 * @param password
	 * @return true if company exists in database
	 * @throws CouponSystemException
	 */
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		Company company = companyRepository.findFirstByEmail(email);
		if(company == null) {
			throw new CouponSystemException("Company email: " + email + ", was not found.");
		}
		if(!company.getPassword().equals(password)) {
			throw new CouponSystemException("Wrong password");
		}
		this.company = company;
		return true;
	}
	
	/**
	 * First checks if there is not another coupon with the same id,
	 * after that checks if the coupon title of the @param(coupon) is exists in database,
	 * than if not, adds the @param(coupon) of the company to database.
	 * @throws CouponSystemException in case the coupon title of the company already exist in the database
	 */
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		if(couponRepository.existsById(coupon.getId())) {
			throw new CouponSystemException("The id of the coupon already exist in the system. Can't add coupon with the same id.");
		}
		Coupon dbCoupon = couponRepository.findFirstByTitle(coupon.getTitle());
		if(dbCoupon != null) {
			throw new CouponSystemException("The coupon title : " + coupon.getTitle() +  " of company with id: " + this.company.getId()  + " already exist in the database. Can't add coupon with the same title.");
		}
		coupon.setCompany(this.company);
		couponRepository.save(coupon);
	}
	
	/**
	 * First checks if the @param(coupon) exists in database,
	 * then checks if the @param(coupon) belong to another company.
	 * Finally Updates the @param(coupon) properties in the database
	 * @throws CouponSystemException in case the coupon not exists in the database
	 * or belongs to other company
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Coupon dbCoupon = couponRepository.findFirstById(coupon.getId());
		if(dbCoupon == null) {
			throw new CouponSystemException("The coupon with id: " + coupon.getId() + " not exists in the database.");
		}
		if(coupon.getCompany().getId() != this.company.getId()) {
			throw new CouponSystemException("The coupon with id: " +coupon.getId()+ " belongs to other company");
		}
		dbCoupon.setTitle(coupon.getTitle());
		dbCoupon.setCategory(coupon.getCategory());
		dbCoupon.setDescription(coupon.getDescription());
		dbCoupon.setAmount(coupon.getAmount());
		dbCoupon.setPrice(coupon.getPrice());
		dbCoupon.setStartDate(coupon.getStartDate());
		dbCoupon.setEndDate(coupon.getEndDate());
		dbCoupon.setImage(coupon.getImage());
	}
	
	public void deleteCoupon(int couponId) throws CouponSystemException {
		if(!couponRepository.existsById(couponId)) {
			throw new CouponSystemException("The coupon not exist in the system.");
		}
		couponRepository.deleteById(couponId);
	}
	
	public List<Coupon> getCompanyCoupons() {
		return couponRepository.findByCompanyId(this.company.getId());
	}
	
	public List<Coupon> getCompanyCoupons(Category category) {
		return couponRepository.findByCompanyIdAndCategory(this.company.getId(), category);
	}
	
	public List<Coupon> getCompanyCoupons(double maxPrice) {
		return couponRepository.findByCompanyIdAndPriceLessThanEqual(this.company.getId(), maxPrice);
	}
	
	public Company getCompanyDetails() {
		return companyRepository.findFirstById(this.company.getId());
	}
}
