package aforo.productrateplanservice.freemium;

import java.util.List;

public interface FreemiumService {
    void create(FreemiumDTO dto);
    List<FreemiumDTO> getAll();
    FreemiumDTO getById(Long id);
    void update(Long id, FreemiumDTO dto);
    void delete(Long id);
}
