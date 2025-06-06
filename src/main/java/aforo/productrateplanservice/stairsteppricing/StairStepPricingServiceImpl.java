package aforo.productrateplanservice.stairsteppricing;

import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StairStepPricingServiceImpl implements StairStepPricingService {

    private final StairStepPricingRepository repository;
    private final RatePlanRepository ratePlanRepository;
    private final StairStepPricingMapper mapper;

    @Override
    public StairStepPricingDTO create(StairStepPricingDTO dto) {
        validateRatePlanExists(dto.getRatePlanId());
        StairStepPricing entity = mapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy("system");
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public StairStepPricingDTO update(Long id, StairStepPricingDTO dto) {
        validateRatePlanExists(dto.getRatePlanId());
        StairStepPricing entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("StairStepPricing not found"));
        mapper.updateEntity(entity, dto);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy("system");
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        StairStepPricing entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("StairStepPricing not found"));
        entity.setIsDeleted(true);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy("system");
        repository.save(entity);
    }

    @Override
    public StairStepPricingDTO getById(Long id) {
        StairStepPricing entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("StairStepPricing not found"));
        return mapper.toDTO(entity);
    }

    @Override
    public List<StairStepPricingDTO> getAll() {
        return repository.findAll().stream()
                .filter(e -> !Boolean.TRUE.equals(e.getIsDeleted()))
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    private void validateRatePlanExists(Long ratePlanId) {
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new IllegalArgumentException("Invalid ratePlanId: " + ratePlanId);
        }
    }
}
