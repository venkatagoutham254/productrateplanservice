package aforo.productrateplanservie.rate_plan_freemium_rate.service;

import aforo.productrateplanservie.rate_plan_freemium_rate.model.RatePlanFreemiumRateDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanFreemiumRateService {

    Page<RatePlanFreemiumRateDTO> findAll(String filter, Pageable pageable);

    RatePlanFreemiumRateDTO get(Long ratePlanFreemiumRateId);

    Long create(RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO);

    void update(Long ratePlanFreemiumRateId, RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO);

    void delete(Long ratePlanFreemiumRateId);

    ReferencedWarning getReferencedWarning(Long ratePlanFreemiumRateId);

}
