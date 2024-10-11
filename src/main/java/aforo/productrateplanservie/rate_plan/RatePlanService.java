package aforo.productrateplanservie.rate_plan;

import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatePlanService {
    Page<RatePlanDTO> findAll(String filter, Pageable pageable);

    RatePlanDTO get(Long ratePlanId);

    Long create(RatePlanDTO ratePlanDTO);

    void update(Long ratePlanId, RatePlanDTO ratePlanDTO);

    void delete(Long ratePlanId);

    ReferencedWarning getReferencedWarning(Long ratePlanId);

}
