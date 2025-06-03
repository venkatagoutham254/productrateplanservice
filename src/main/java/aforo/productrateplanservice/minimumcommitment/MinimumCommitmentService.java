package aforo.productrateplanservice.minimumcommitment;

import java.util.List;

public interface MinimumCommitmentService {
    void create(MinimumCommitmentDTO dto);
    List<MinimumCommitmentDTO> getAll();
    MinimumCommitmentDTO getById(Long id);
    void update(Long id, MinimumCommitmentDTO dto);
    void delete(Long id);
}
