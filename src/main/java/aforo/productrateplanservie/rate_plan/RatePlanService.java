package aforo.productrateplanservie.rate_plan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RatePlanService {
    Page<RatePlanDTO> findAll(String filter, Pageable pageable);

    RatePlanDTO get(Long ratePlanId);

    Long create(Long productId, CreateRatePlanRequest createRatePlanRequest);

    void update(Long ratePlanId, CreateRatePlanRequest createRatePlanRequest);

    void delete(Long ratePlanId);

    Page<RatePlanDTO> getRatePlansByProductId(Long productId, String filter, Pageable pageable);

    // New method to fetch SelectedRatePlanTypeId based on ratePlanId and ratePlanType
    Optional<Long> getSelectedRatePlanTypeId(Long ratePlanId, String ratePlanType);

    long getRatePlanCount();
}
