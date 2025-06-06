package aforo.productrateplanservice.volumepricing;

import java.util.List;

public interface VolumePricingService {
    VolumePricingDTO create(VolumePricingDTO dto);
    VolumePricingDTO update(Long id, VolumePricingDTO dto);
    void delete(Long id);
    VolumePricingDTO getById(Long id);
    List<VolumePricingDTO> getAll();
}
