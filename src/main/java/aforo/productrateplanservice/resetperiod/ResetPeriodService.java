package aforo.productrateplanservice.resetperiod;

import java.util.List;

public interface ResetPeriodService {
    void create(ResetPeriodDTO dto);
    List<ResetPeriodDTO> getAll();
    ResetPeriodDTO getById(Long id);
    void update(Long id, ResetPeriodDTO dto);
    void delete(Long id);
}
