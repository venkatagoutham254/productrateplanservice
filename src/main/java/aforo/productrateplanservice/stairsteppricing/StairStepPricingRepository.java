package aforo.productrateplanservice.stairsteppricing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StairStepPricingRepository extends JpaRepository<StairStepPricing, Long> {
}
