package app.core.repositories;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;

@Transactional
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	void deleteByCompanyId(int companyId);

	List<Coupon> findByCompanyId(int companyId);

	List<Coupon> findByCustomersId(int customerId);

	Coupon findFirstByTitle(String title);

	Coupon findFirstById(int id);

	List<Coupon> findByCategory(Category category, Sort sort);

	List<Coupon> findByCompanyIdAndPriceLessThanEqual(int companyId, double maxPrice);

	boolean existsByIdAndCustomersId(int id, int customerId);
	
	List<Coupon> findByCustomersIdAndPriceLessThanEqual(int customerId, double maxPrice);

	void deleteByEndDateBefore(LocalDate now);

	List<Coupon> findByCustomersIdAndCategory(int customerId, Category category);

	List<Coupon> findByCompanyIdAndCategory(int companyId, Category category);
}
