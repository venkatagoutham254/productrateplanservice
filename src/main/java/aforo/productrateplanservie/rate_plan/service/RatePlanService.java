package aforo.productrateplanservie.rate_plan.service;

import aforo.productrateplanservie.rate_plan.model.RatePlanDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanService {

    Page<RatePlanDTO> findAll(String filter, Pageable pageable);

    RatePlanDTO get(UUID ratePlanId);

    UUID create(RatePlanDTO ratePlanDTO);

    void update(UUID ratePlanId, RatePlanDTO ratePlanDTO);

    void delete(UUID ratePlanId);

    ReferencedWarning getReferencedWarning(UUID ratePlanId);

}
