package aforo.productrateplanservie.rate_plan_usage_based.service;

import aforo.productrateplanservie.rate_plan_usage_based.model.RatePlanUsageBasedDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanUsageBasedService {

    Page<RatePlanUsageBasedDTO> findAll(String filter, Pageable pageable);

    RatePlanUsageBasedDTO get(Long ratePlanUsageRateId);

    Long create(RatePlanUsageBasedDTO ratePlanUsageBasedDTO);

    void update(Long ratePlanUsageRateId, RatePlanUsageBasedDTO ratePlanUsageBasedDTO);

    void delete(Long ratePlanUsageRateId);

    ReferencedWarning getReferencedWarning(Long ratePlanUsageRateId);

}
