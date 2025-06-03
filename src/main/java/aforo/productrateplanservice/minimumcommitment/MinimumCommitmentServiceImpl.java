package aforo.productrateplanservice.minimumcommitment;

import aforo.productrateplanservice.exception.ResourceNotFoundException;
import aforo.productrateplanservice.rate_plan.RatePlan;
import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MinimumCommitmentServiceImpl implements MinimumCommitmentService {

    private final MinimumCommitmentRepository repository;
    private final RatePlanRepository ratePlanRepository;
    private final MinimumCommitmentMapper mapper;

    @Override
    public void create(MinimumCommitmentDTO dto) {
        RatePlan ratePlan = ratePlanRepository.findById(dto.getRatePlanId())
                .orElseThrow(() -> new ResourceNotFoundException("RatePlan not found"));
        MinimumCommitment entity = mapper.toEntity(dto, ratePlan);
        repository.save(entity);
    }

    @Override
    public List<MinimumCommitmentDTO> getAll() {
        return repository.findByIsDeletedFalse()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public MinimumCommitmentDTO getById(Long id) {
        MinimumCommitment entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Minimum Commitment not found"));
        return toDTO(entity);
    }

    @Override
    public void update(Long id, MinimumCommitmentDTO dto) {
        MinimumCommitment entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Minimum Commitment not found"));

        entity.setMinimumCommitment(dto.getMinimumCommitment());
        entity.setCommitmentDuration(dto.getCommitmentDuration());
        entity.setCommitmentUnit(dto.getCommitmentUnit());
        entity.setUpdatedBy("system");
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        MinimumCommitment entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Minimum Commitment not found"));

        entity.setIsDeleted(true);
        entity.setIsActive(false);
        entity.setUpdatedBy("system");
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);
        repository.save(entity);
    }

    private MinimumCommitmentDTO toDTO(MinimumCommitment entity) {
        return MinimumCommitmentDTO.builder()
                .ratePlanId(entity.getRatePlan().getRatePlanId())
                .minimumCommitment(entity.getMinimumCommitment())
                .commitmentDuration(entity.getCommitmentDuration())
                .commitmentUnit(entity.getCommitmentUnit())
                .build();
    }
}
