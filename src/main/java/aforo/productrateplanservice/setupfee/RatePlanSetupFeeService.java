package aforo.productrateplanservice.setupfee;

import java.util.List;

public interface RatePlanSetupFeeService {
    void createSetupFee(RatePlanSetupFeeDTO dto);
    List<RatePlanSetupFeeDTO> getAll();
    RatePlanSetupFeeDTO getById(Long id);
    void updateSetupFee(Long id, RatePlanSetupFeeDTO dto);
    void deleteSetupFee(Long id);
}
