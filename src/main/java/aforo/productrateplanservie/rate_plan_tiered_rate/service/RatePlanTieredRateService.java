package aforo.productrateplanservie.rate_plan_tiered_rate.service;

import aforo.productrateplanservie.rate_plan_tiered_rate.model.RatePlanTieredRateDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanTieredRateService {

    Page<RatePlanTieredRateDTO> findAll(String filter, Pageable pageable);

    RatePlanTieredRateDTO get(Long ratePlanTieredRateId);

    Long create(RatePlanTieredRateDTO ratePlanTieredRateDTO);

    void update(Long ratePlanTieredRateId, RatePlanTieredRateDTO ratePlanTieredRateDTO);

    void delete(Long ratePlanTieredRateId);

    ReferencedWarning getReferencedWarning(Long ratePlanTieredRateId);

}
