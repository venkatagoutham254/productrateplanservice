package aforo.rateplanservie.service;

import aforo.rateplanservie.model.RatePlanSubscriptionRateDTO;
import aforo.rateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanSubscriptionRateService {

    Page<RatePlanSubscriptionRateDTO> findAll(String filter, Pageable pageable);

    RatePlanSubscriptionRateDTO get(Integer ratePlanSubscriptionRateId);

    Integer create(RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO);

    void update(Integer ratePlanSubscriptionRateId,
            RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO);

    void delete(Integer ratePlanSubscriptionRateId);

    ReferencedWarning getReferencedWarning(Integer ratePlanSubscriptionRateId);

}
