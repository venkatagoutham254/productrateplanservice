// RatePlanUsageBasedService.java
package aforo.productrateplanservie.rate_plan_usage_based;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface RatePlanUsageBasedService {

    Page<RatePlanUsageBasedDTO> findAll(String filter, Pageable pageable);

    RatePlanUsageBasedDTO get(Long ratePlanUsageRateId);

    Long create(Long ratePlanId, CreateRatePlanUsageBasedRequest request);


    @Transactional
    void update(Long ratePlanId, Long ratePlanUsageRateId, @Valid UpdateRatePlanUsageBasedRequest updateDTO);

    void delete(Long ratePlanUsageRateId);
}
