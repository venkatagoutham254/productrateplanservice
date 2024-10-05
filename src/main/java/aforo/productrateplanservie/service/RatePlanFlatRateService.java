package aforo.productrateplanservie.service;

import aforo.productrateplanservie.model.RatePlanFlatRateDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanFlatRateService {

    Page<RatePlanFlatRateDTO> findAll(String filter, Pageable pageable);

    RatePlanFlatRateDTO get(Integer ratePlanFlatRateId);

    Integer create(RatePlanFlatRateDTO ratePlanFlatRateDTO);

    void update(Integer ratePlanFlatRateId, RatePlanFlatRateDTO ratePlanFlatRateDTO);

    void delete(Integer ratePlanFlatRateId);

    ReferencedWarning getReferencedWarning(Integer ratePlanFlatRateId);

}
