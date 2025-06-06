package aforo.productrateplanservice.rate_plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatePlanRepository extends JpaRepository<RatePlan, Long> {

    @Query("SELECT COUNT(r) > 0 FROM RatePlan r WHERE LOWER(TRIM(r.ratePlanName)) = LOWER(TRIM(:name))")
    boolean existsByRatePlanNameIgnoreCaseTrimmed(@Param("name") String name);
}
