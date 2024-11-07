package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.exception.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RatePlanFlatRateService {

    Page<RatePlanFlatRateDTO> findAll(String filter, Pageable pageable);

    RatePlanFlatRateDTO get(Long ratePlanFlatRateId);

    Long create(Long ratePlanId, RatePlanFlatRateDTO ratePlanFlatRateDTO);

    void update(Long ratePlanFlatRateId, RatePlanFlatRateDTO ratePlanFlatRateDTO);

    void delete(Long ratePlanFlatRateId);

    ReferencedWarning getReferencedWarning(Long ratePlanFlatRateId);

    // Method to find all flat rates by RatePlan ID
    Page<RatePlanFlatRateDTO> findAllByRatePlanId(Long ratePlanId, Pageable pageable);

    // New method to find the first RatePlanFlatRate by RatePlan ID
    Optional<RatePlanFlatRateDTO> findFirstByRatePlanId(Long ratePlanId);
}
