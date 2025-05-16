package aforo.productrateplanservice.rate_plan_freemium_rate;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatePlanFreemiumRateService {

    Page<RatePlanFreemiumRateDTO> findAll(String filter, Pageable pageable);

    RatePlanFreemiumRateDTO get(Long ratePlanFreemiumRateId);

    Long create(Long ratePlanId, @Valid CreateRatePlanFreemiumRateRequest ratePlanFreemiumRateCreateRequestDTO);

    void update(Long ratePlanId, Long ratePlanFreemiumRateId, @Valid UpdateRatePlanFreemiumRateRequest updateDTO);

    void delete(Long ratePlanFreemiumRateId);

    long getRatePlanFreemiumRateCount();
}
