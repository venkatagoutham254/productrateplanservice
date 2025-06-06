package aforo.productrateplanservice.flatfee;

import java.util.List;

public interface FlatFeeService {
    FlatFeeDTO create(FlatFeeCreateUpdateDTO dto);
    FlatFeeDTO update(Long id, FlatFeeCreateUpdateDTO dto);
    void delete(Long id);
    FlatFeeDTO getById(Long id);
    List<FlatFeeDTO> getAll();
}
