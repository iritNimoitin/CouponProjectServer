package app.core.repositories;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;

@Transactional
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	void deleteByCompanyId(int id);

	List<Coupon> findByCompanyId(int companyId);

	List<Coupon> findByCustomersId(int customerId);

	Coupon findFirstByTitle(String title);

	Coupon findFirstById(int id);

	List<Coupon> findByCategory(Category category);

	List<Coupon> findByCompanyIdAndPriceLessThanEqual(int companyId, double maxPrice);

	//boolean existsByIdAndCustomerId(int id, int customerId);
	boolean existsByIdAndCustomersId(int id, int customerId);
	
	List<Coupon> findByCustomersIdAndPriceLessThanEqual(int customerId, double maxPrice);

//	List<Coupon> findByEndDateBefore(LocalDate now);

	void deleteByEndDateBefore(LocalDate now);

	List<Coupon> findByCustomersIdAndCategory(int customerId, Category category);
}
