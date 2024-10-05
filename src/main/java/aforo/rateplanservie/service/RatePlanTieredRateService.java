package aforo.rateplanservie.service;

import aforo.rateplanservie.model.RatePlanTieredRateDTO;
import aforo.rateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanTieredRateService {

    Page<RatePlanTieredRateDTO> findAll(String filter, Pageable pageable);

    RatePlanTieredRateDTO get(Integer ratePlanTieredRateId);

    Integer create(RatePlanTieredRateDTO ratePlanTieredRateDTO);

    void update(Integer ratePlanTieredRateId, RatePlanTieredRateDTO ratePlanTieredRateDTO);

    void delete(Integer ratePlanTieredRateId);

    ReferencedWarning getReferencedWarning(Integer ratePlanTieredRateId);

}
