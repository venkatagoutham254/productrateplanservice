package aforo.productrateplanservie.rate_plan_freemium_rate.service;

import aforo.productrateplanservie.rate_plan_freemium_rate.model.RatePlanFreemiumRateDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanFreemiumRateService {

    Page<RatePlanFreemiumRateDTO> findAll(String filter, Pageable pageable);

    RatePlanFreemiumRateDTO get(UUID ratePlanFreemiumRateId);

    UUID create(RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO);

    void update(UUID ratePlanFreemiumRateId, RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO);

    void delete(UUID ratePlanFreemiumRateId);

    ReferencedWarning getReferencedWarning(UUID ratePlanFreemiumRateId);

}
