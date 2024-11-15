package aforo.productrateplanservie.rate_plan_subscription_rate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanSubscriptionRateService {

    Page<RatePlanSubscriptionRateDTO> findAll(String filter, Pageable pageable);

    RatePlanSubscriptionRateDTO get(Long ratePlanSubscriptionRateId);

    Long create(Long ratePlanId, CreateRatePlanSubscriptionRateRequest ratePlanSubscriptionRateRequest);

    void update(Long ratePlanId, Long ratePlanSubscriptionRateId, UpdateRatePlanSubscriptionRateRequest request);

    void delete(Long ratePlanSubscriptionRateId);

//    ReferencedWarning getReferencedWarning(Long ratePlanSubscriptionRateId);

}
