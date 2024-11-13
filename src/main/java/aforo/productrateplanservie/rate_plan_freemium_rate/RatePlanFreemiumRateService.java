package aforo.productrateplanservie.rate_plan_freemium_rate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanFreemiumRateService {

    Page<RatePlanFreemiumRateDTO> findAll(String filter, Pageable pageable);

    RatePlanFreemiumRateDTO get(Long ratePlanFreemiumRateId);

    Long create(Long ratePlanId, RatePlanFreemiumRateCreateRequestDTO ratePlanFreemiumRateCreateRequestDTO);

    //
    void update( Long ratePlanId,Long ratePlanFreemiumRateId, RatePlanFreemiumRateUpdateRequestDTO updateDTO);

    //
    void delete(Long ratePlanFreemiumRateId);

//    ReferencedWarning getReferencedWarning(Long ratePlanFreemiumRateId);

}
