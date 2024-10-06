package aforo.productrateplanservie.rate_plan.repos;

import aforo.productrateplanservie.currencies.domain.Currencies;
import aforo.productrateplanservie.product.domain.Product;
import aforo.productrateplanservie.rate_plan.domain.RatePlan;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanRepository extends JpaRepository<RatePlan, UUID> {

    Page<RatePlan> findAllByRatePlanId(UUID ratePlanId, Pageable pageable);

    RatePlan findFirstByProduct(Product product);

    RatePlan findFirstByCurrency(Currencies currencies);

}
