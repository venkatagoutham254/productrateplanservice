package aforo.rateplanservie.service;

import aforo.rateplanservie.model.RatePlanDTO;
import aforo.rateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanService {

    Page<RatePlanDTO> findAll(String filter, Pageable pageable);

    RatePlanDTO get(Integer ratePlanId);

    Integer create(RatePlanDTO ratePlanDTO);

    void update(Integer ratePlanId, RatePlanDTO ratePlanDTO);

    void delete(Integer ratePlanId);

    ReferencedWarning getReferencedWarning(Integer ratePlanId);

}
