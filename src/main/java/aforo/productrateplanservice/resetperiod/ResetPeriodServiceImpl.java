package aforo.productrateplanservice.resetperiod;

import aforo.productrateplanservice.exception.ResourceNotFoundException;
import aforo.productrateplanservice.rate_plan.RatePlan;
import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResetPeriodServiceImpl implements ResetPeriodService {

    private final ResetPeriodRepository repository;
    private final RatePlanRepository ratePlanRepository;
    private final ResetPeriodMapper mapper;

    @Override
    public void create(ResetPeriodDTO dto) {
        RatePlan ratePlan = ratePlanRepository.findById(dto.getRatePlanId())
                .orElseThrow(() -> new ResourceNotFoundException("RatePlan not found"));
        ResetPeriod entity = mapper.toEntity(dto, ratePlan);
        repository.save(entity);
    }

    @Override
    public List<ResetPeriodDTO> getAll() {
        return repository.findByIsDeletedFalse()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public ResetPeriodDTO getById(Long id) {
        ResetPeriod entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reset Period not found"));
        return toDTO(entity);
    }

    @Override
    public void update(Long id, ResetPeriodDTO dto) {
        ResetPeriod entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reset Period not found"));

        entity.setResetFrequency(dto.getResetFrequency());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setUpdatedBy("system");
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        ResetPeriod entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reset Period not found"));

        entity.setIsDeleted(true);
        entity.setIsActive(false);
        entity.setUpdatedBy("system");
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);
        repository.save(entity);
    }

    private ResetPeriodDTO toDTO(ResetPeriod entity) {
        return ResetPeriodDTO.builder()
                .ratePlanId(entity.getRatePlan().getRatePlanId())
                .resetFrequency(entity.getResetFrequency())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }
}
