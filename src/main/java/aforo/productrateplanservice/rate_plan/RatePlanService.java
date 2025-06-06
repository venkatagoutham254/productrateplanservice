package aforo.productrateplanservice.rate_plan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface RatePlanService {
    RatePlanDTO createRatePlan(CreateRatePlanRequest request);
    RatePlanDTO getRatePlanById(Long id);
    List<RatePlanDTO> getAllRatePlans();
    void deleteRatePlan(Long id);
    RatePlanDTO updateRatePlan(Long id, UpdateRatePlanRequest request);

}

