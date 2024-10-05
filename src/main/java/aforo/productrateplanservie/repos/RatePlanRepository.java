package aforo.productrateplanservie.repos;

import aforo.productrateplanservie.domain.Currencies;
import aforo.productrateplanservie.domain.Product;
import aforo.productrateplanservie.domain.RatePlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanRepository extends JpaRepository<RatePlan, Integer> {

    Page<RatePlan> findAllByRatePlanId(Integer ratePlanId, Pageable pageable);

    RatePlan findFirstByProduct(Product product);

    RatePlan findFirstByCurrency(Currencies currencies);

}
