package aforo.productrateplanservie.rate_plan_usage_based.service;

import aforo.productrateplanservie.rate_plan_usage_based.model.RatePlanUsageBasedDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanUsageBasedService {

    Page<RatePlanUsageBasedDTO> findAll(String filter, Pageable pageable);

    RatePlanUsageBasedDTO get(UUID ratePlanUsageRateId);

    UUID create(RatePlanUsageBasedDTO ratePlanUsageBasedDTO);

    void update(UUID ratePlanUsageRateId, RatePlanUsageBasedDTO ratePlanUsageBasedDTO);

    void delete(UUID ratePlanUsageRateId);

    ReferencedWarning getReferencedWarning(UUID ratePlanUsageRateId);

}
