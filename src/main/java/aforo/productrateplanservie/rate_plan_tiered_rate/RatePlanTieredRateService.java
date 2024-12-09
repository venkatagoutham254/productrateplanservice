package aforo.productrateplanservie.rate_plan_tiered_rate;

import aforo.productrateplanservie.exception.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanTieredRateService {

    Page<RatePlanTieredRateDTO> findAll(String filter, Pageable pageable);

    RatePlanTieredRateDTO get(Long ratePlanTieredRateId);

    Long create(Long ratePlanId, CreateRatePlanTieredRateRequest createRequest);


    void update(Long ratePlanId, Long ratePlanTieredRateId, UpdateRatePlanTieredRateRequest updateRequest);

    void delete(Long ratePlanTieredRateId);

//    ReferencedWarning getReferencedWarning(Long ratePlanTieredRateId);
    long getRatePlanTieredRateCount();
}
