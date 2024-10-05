package aforo.productrateplanservie.service;

import aforo.productrateplanservie.model.RatePlanDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
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
