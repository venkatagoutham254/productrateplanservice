package aforo.productrateplanservie.rate_plan_flat_rate.service;

import aforo.productrateplanservie.rate_plan_flat_rate.model.RatePlanFlatRateDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanFlatRateService {

    Page<RatePlanFlatRateDTO> findAll(String filter, Pageable pageable);

    RatePlanFlatRateDTO get(UUID ratePlanFlatRateId);

    UUID create(RatePlanFlatRateDTO ratePlanFlatRateDTO);

    void update(UUID ratePlanFlatRateId, RatePlanFlatRateDTO ratePlanFlatRateDTO);

    void delete(UUID ratePlanFlatRateId);

    ReferencedWarning getReferencedWarning(UUID ratePlanFlatRateId);

}
