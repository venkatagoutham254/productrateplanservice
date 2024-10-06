package aforo.productrateplanservie.rate_plan_subscription_rate.service;

import aforo.productrateplanservie.rate_plan_subscription_rate.model.RatePlanSubscriptionRateDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanSubscriptionRateService {

    Page<RatePlanSubscriptionRateDTO> findAll(String filter, Pageable pageable);

    RatePlanSubscriptionRateDTO get(UUID ratePlanSubscriptionRateId);

    UUID create(RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO);

    void update(UUID ratePlanSubscriptionRateId,
            RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO);

    void delete(UUID ratePlanSubscriptionRateId);

    ReferencedWarning getReferencedWarning(UUID ratePlanSubscriptionRateId);

}
