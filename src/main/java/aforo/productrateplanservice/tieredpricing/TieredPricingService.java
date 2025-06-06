package aforo.productrateplanservice.tieredpricing;

import java.util.List;

public interface TieredPricingService {
    TieredPricingDTO create(TieredPricingDTO dto);
    TieredPricingDTO update(Long id, TieredPricingDTO dto);
    void delete(Long id);
    TieredPricingDTO getById(Long id);
    List<TieredPricingDTO> getAll();
}
