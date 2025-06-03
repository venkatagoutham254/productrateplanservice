package aforo.productrateplanservice.overagecharges;

import java.util.List;

public interface OverageChargeService {
    void create(OverageChargeDTO dto);
    List<OverageChargeDTO> getAll();
    OverageChargeDTO getById(Long id);
    void update(Long id, OverageChargeDTO dto);
    void delete(Long id);
}
