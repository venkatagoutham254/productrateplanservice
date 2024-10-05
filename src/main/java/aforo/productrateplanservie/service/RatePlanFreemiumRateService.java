package aforo.productrateplanservie.service;

import aforo.productrateplanservie.model.RatePlanFreemiumRateDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RatePlanFreemiumRateService {

    Page<RatePlanFreemiumRateDTO> findAll(String filter, Pageable pageable);

    RatePlanFreemiumRateDTO get(Integer ratePlanFreemiumRateId);

    Integer create(RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO);

    void update(Integer ratePlanFreemiumRateId, RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO);

    void delete(Integer ratePlanFreemiumRateId);

    ReferencedWarning getReferencedWarning(Integer ratePlanFreemiumRateId);

}
