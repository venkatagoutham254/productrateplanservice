package aforo.productrateplanservice.tieredpricing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TieredPricingRepository extends JpaRepository<TieredPricing, Long> {
}
