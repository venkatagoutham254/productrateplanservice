package aforo.productrateplanservie.rate_plan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatePlanService {
    Page<RatePlanDTO> findAll(String filter, Pageable pageable);

    RatePlanDTO get(Long ratePlanId);

    Long create(Long productId, CreateRatePlanRequest createRatePlanRequest);

    void update(Long ratePlanId, CreateRatePlanRequest createRatePlanRequest);

    void delete(Long ratePlanId);

//    ReferencedWarning getReferencedWarning(Long ratePlanId);

    Page<RatePlanDTO> getRatePlansByProductId(Long productId, String filter, Pageable pageable);

}
