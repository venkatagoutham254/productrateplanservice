package aforo.productrateplanservice.overagecharges;

import aforo.productrateplanservice.exception.ResourceNotFoundException;
import aforo.productrateplanservice.rate_plan.RatePlan;
import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OverageChargeServiceImpl implements OverageChargeService {

    private final OverageChargeRepository repository;
    private final RatePlanRepository ratePlanRepository;
    private final OverageChargeMapper mapper;

    @Override
    public void create(OverageChargeDTO dto) {
        RatePlan ratePlan = ratePlanRepository.findById(dto.getRatePlanId())
                .orElseThrow(() -> new ResourceNotFoundException("RatePlan not found"));
        OverageCharge entity = mapper.toEntity(dto, ratePlan);
        repository.save(entity);
    }

    @Override
    public List<OverageChargeDTO> getAll() {
        return repository.findByIsDeletedFalse()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public OverageChargeDTO getById(Long id) {
        OverageCharge entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("OverageCharge not found"));
        return toDTO(entity);
    }

    @Override
    public void update(Long id, OverageChargeDTO dto) {
        OverageCharge entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("OverageCharge not found"));

        entity.setUsageAccount(dto.getUsageAccount());
        entity.setOverageUnitRate(dto.getOverageUnitRate());
        entity.setGraceBuffer(dto.getGraceBuffer());
        entity.setUpdatedBy("system");
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        OverageCharge entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("OverageCharge not found"));

        entity.setIsDeleted(true);
        entity.setIsActive(false);
        entity.setUpdatedBy("system");
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);
        repository.save(entity);
    }

    private OverageChargeDTO toDTO(OverageCharge entity) {
        return OverageChargeDTO.builder()
                .ratePlanId(entity.getRatePlan().getRatePlanId())
                .usageAccount(entity.getUsageAccount())
                .overageUnitRate(entity.getOverageUnitRate())
                .graceBuffer(entity.getGraceBuffer())
                .build();
    }
}
