package app.core.tests;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.services.CompanyService;

@Component
public class TestCompanyService {
	
	private ApplicationContext ctx;
	
	@Autowired
	public TestCompanyService(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	/**
	 * Adds company coupons to database, using company1
	 * testing the method addCoupon(coupon) of CompanyFacade
	 * @param companyFacade
	 */
	public void initializeCoupons1(CompanyService companyService) {
		try {	
			Coupon coupon1 = createCoupon(Category.Electricity, "coupon1", "electronics", LocalDate.parse("2022-02-03"), LocalDate.parse("2024-02-03"), 30, 70.3, "image1");
			companyService.addCoupon(coupon1);
			Coupon coupon2 = createCoupon(Category.Food, "coupon2", "foodies", LocalDate.parse("2022-04-05"), LocalDate.parse("2024-04-05"), 35, 77.3, "image2");
			companyService.addCoupon(coupon2);
			Coupon coupon3 = createCoupon(Category.Restaurant, "coupon3", "kepasa", LocalDate.parse("2021-01-27"), LocalDate.parse("2021-02-10"), 40, 37, "image3");
			companyService.addCoupon(coupon3);
			Coupon coupon4 = createCoupon(Category.Vacation, "coupon4", "hotels", LocalDate.parse("2021-01-27"), LocalDate.parse("2021-06-09"), 5, 230, "image4");
			companyService.addCoupon(coupon4);
			System.out.println("addCoupon succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Adds company coupons to database, using company1
	 * testing the method addCoupon(coupon) of CompanyFacade
	 * @param companyFacade
	 */
	public void initializeCoupons2(CompanyService companyService) {
		try {
			Coupon coupon1 = createCoupon(Category.Attractions, "coupon5", "escape room", LocalDate.parse("2021-05-03"), LocalDate.parse("2023-02-04"), 10, 50.3, "image5");
			companyService.addCoupon(coupon1);
			Coupon coupon2 = createCoupon(Category.Attractions, "coupon6", "cinema", LocalDate.parse("2021-02-05"), LocalDate.parse("2021-11-15"), 123, 13.3, "image6");
			companyService.addCoupon(coupon2);
			Coupon coupon3 = createCoupon(Category.Furnitures, "coupon7", "chair", LocalDate.parse("2021-02-27"), LocalDate.parse("2021-07-12"), 2, 37, "image7");
			companyService.addCoupon(coupon3);
			Coupon coupon4 = createCoupon(Category.Vacation, "coupon8", "tzimer", LocalDate.parse("2021-01-27"), LocalDate.parse("2021-08-23"), 1, 230, "image8");
			companyService.addCoupon(coupon4);
			System.out.println("addCoupon succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Testing methods of CompanyFacade:
	 * getCompanyCoupons(), updateCoupon(coupon), deleteCoupon(couponID),
	 * getCompanyCoupons(category), getCompanyCoupons(maxPrice), 
	 * getCompanyDetails()
	 * @param companyFacade
	 */
	public void start(CompanyService companyService) {
		List<Coupon> coupons = null;
		coupons = companyService.getCompanyCoupons();
		System.out.println("\ncompany coupons: ");
		for(Coupon c: coupons) {
			System.out.println("	" + c.toString());
		}
		if(coupons.size() == 0) {
			System.out.println("	this company don't have coupons");
		}
		System.out.println("getCompanyCoupons succeeded");
		if(coupons != null && coupons.size() > 0) {
			coupons.get(0).setImage("updateImage");
			try {
				companyService.updateCoupon(coupons.get(0));
				System.out.println("updateCoupon succeeded");
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("ERROR: coupons array is empty");
		}
		try {
			companyService.deleteCoupon(coupons.get(0).getId());
			System.out.println("deleteCoupon succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		List<Coupon> couponsByCategory = null;
		Category category = Category.Restaurant;
		couponsByCategory = companyService.getCompanyCoupons(category);
		System.out.println("\ncompany coupons by category: " + category);
		for(Coupon c: couponsByCategory) {
			System.out.println("	" + c.toString());
		}
		if(couponsByCategory.size() == 0) {
			System.out.println("	this company don't have coupons with category " + category);
		}
		System.out.println("getCompanyCoupons by category succeeded");
		List<Coupon> couponsByMaxPrice = null;
		double maxPrice = 160.4;
		couponsByMaxPrice = companyService.getCompanyCoupons(maxPrice);
		System.out.println("\ncompany coupons by max price: " + maxPrice);
		for(Coupon c: couponsByMaxPrice) {
			System.out.println("	" + c.toString());
		}
		if(couponsByCategory.size() == 0) {
			System.out.println("	this company don't have coupons with price less then " + maxPrice);
		}
		System.out.println("getCompanyCoupons by max price succeeded");
		System.out.println(companyService.getCompanyDetails().toString());
		System.out.println("getCompanyDetails succeeded");
	}
	
	private Coupon createCoupon(Category category, String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price, String image) {
		Coupon coupon = new Coupon();
		coupon.setAmount(amount);
		coupon.setCategory(category);
		coupon.setDescription(description);
		coupon.setEndDate(endDate);
		coupon.setImage(image);
		coupon.setPrice(price);
		coupon.setStartDate(startDate);
		coupon.setTitle(title);
		return coupon;
	}

}
