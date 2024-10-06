package aforo.productrateplanservie.rate_plan_tiered_rate.service;

import aforo.productrateplanservie.rate_plan_tiered_rate.model.RatePlanTieredRateDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanTieredRateService {

    Page<RatePlanTieredRateDTO> findAll(String filter, Pageable pageable);

    RatePlanTieredRateDTO get(UUID ratePlanTieredRateId);

    UUID create(RatePlanTieredRateDTO ratePlanTieredRateDTO);

    void update(UUID ratePlanTieredRateId, RatePlanTieredRateDTO ratePlanTieredRateDTO);

    void delete(UUID ratePlanTieredRateId);

    ReferencedWarning getReferencedWarning(UUID ratePlanTieredRateId);

}
