package aforo.productrateplanservie.rate_plan_subscription_rate;

import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanSubscriptionRateService {

    Page<RatePlanSubscriptionRateDTO> findAll(String filter, Pageable pageable);

    RatePlanSubscriptionRateDTO get(Long ratePlanSubscriptionRateId);

    Long create(Long ratePlanId, RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO);

    void update(Long ratePlanSubscriptionRateId,
            RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO);

    void delete(Long ratePlanSubscriptionRateId);

    ReferencedWarning getReferencedWarning(Long ratePlanSubscriptionRateId);

}
