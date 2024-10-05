package aforo.rateplanservie.service;

import aforo.rateplanservie.model.RatePlanUsageBasedDTO;
import aforo.rateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanUsageBasedService {

    Page<RatePlanUsageBasedDTO> findAll(String filter, Pageable pageable);

    RatePlanUsageBasedDTO get(Integer ratePlanUsageRateId);

    Integer create(RatePlanUsageBasedDTO ratePlanUsageBasedDTO);

    void update(Integer ratePlanUsageRateId, RatePlanUsageBasedDTO ratePlanUsageBasedDTO);

    void delete(Integer ratePlanUsageRateId);

    ReferencedWarning getReferencedWarning(Integer ratePlanUsageRateId);

}
