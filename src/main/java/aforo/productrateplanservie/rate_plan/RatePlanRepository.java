package aforo.productrateplanservie.rate_plan;

import aforo.productrateplanservie.currencies.Currencies;
import aforo.productrateplanservie.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatePlanRepository extends JpaRepository<RatePlan, Long> {
    Page<RatePlan> findAllByRatePlanId(Long ratePlanId, Pageable pageable);

    RatePlan findFirstByProduct(Product product);

    RatePlan findFirstByCurrency(Currencies currencies);
}
