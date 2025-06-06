package aforo.productrateplanservice.stairsteppricing;

import java.util.List;

public interface StairStepPricingService {
    StairStepPricingDTO create(StairStepPricingDTO dto);
    StairStepPricingDTO update(Long id, StairStepPricingDTO dto);
    void delete(Long id);
    StairStepPricingDTO getById(Long id);
    List<StairStepPricingDTO> getAll();
}
