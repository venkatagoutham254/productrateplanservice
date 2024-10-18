package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatePlanFlatRateService {

    Page<RatePlanFlatRateDTO> findAll(String filter, Pageable pageable);

    RatePlanFlatRateDTO get(Long ratePlanFlatRateId);

    Long create(Long ratePlanId, RatePlanFlatRateDTO ratePlanFlatRateDTO);

    void update(Long ratePlanFlatRateId, RatePlanFlatRateDTO ratePlanFlatRateDTO);

    void delete(Long ratePlanFlatRateId);

    ReferencedWarning getReferencedWarning(Long ratePlanFlatRateId);

    // New method to find all flat rates by ratePlanId
    Page<RatePlanFlatRateDTO> findAllByRatePlanId(Long ratePlanId, Pageable pageable);
}
