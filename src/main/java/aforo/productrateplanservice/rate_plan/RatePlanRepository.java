package aforo.productrateplanservice.rate_plan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aforo.productrateplanservice.currencies.Currencies;
import aforo.productrateplanservice.product.entity.Product;

public interface RatePlanRepository extends JpaRepository<RatePlan, Long> {
    Page<RatePlan> findAllByRatePlanId(Long ratePlanId, Pageable pageable);

    RatePlan findFirstByProduct(Product product);

    RatePlan findFirstByCurrency(Currencies currencies);

    @Query("SELECT rp FROM RatePlan rp WHERE rp.product.productId = :productId")
    Page<RatePlan> findByProductId(@Param("productId") Long productId, Pageable pageable);

    @Query("SELECT rp FROM RatePlan rp WHERE rp.product.productId = :productId AND (:ratePlanId IS NULL OR rp.ratePlanId = :ratePlanId)")
    Page<RatePlan> findByProductIdAndRatePlanId(@Param("productId") Long productId, @Param("ratePlanId") Long ratePlanId, Pageable pageable);
}
