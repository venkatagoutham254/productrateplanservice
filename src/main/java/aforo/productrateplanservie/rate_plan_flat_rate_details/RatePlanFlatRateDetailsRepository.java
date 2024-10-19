package aforo.productrateplanservie.rate_plan_flat_rate_details;
import aforo.productrateplanservie.rate_plan_flat_rate.RatePlanFlatRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RatePlanFlatRateDetailsRepository extends JpaRepository<RatePlanFlatRateDetails, Long> {

    RatePlanFlatRateDetails findFirstByRatePlanFlatRate(RatePlanFlatRate ratePlanFlatRate);

    @Modifying
    @Transactional
    @Query("DELETE FROM RatePlanFlatRateDetails d WHERE d.ratePlanFlatRate = ?1")
    void deleteAllByRatePlanFlatRate(RatePlanFlatRate ratePlanFlatRate);

}
