package aforo.productrateplanservice.tieredpricing;

import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TieredPricingServiceImpl implements TieredPricingService {

    private final TieredPricingRepository repository;
    private final RatePlanRepository ratePlanRepository;
    private final TieredPricingMapper mapper;

    @Override
    public TieredPricingDTO create(TieredPricingDTO dto) {
        validateRatePlanExists(dto.getRatePlanId());
        TieredPricing entity = mapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy("system");
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public TieredPricingDTO update(Long id, TieredPricingDTO dto) {
        validateRatePlanExists(dto.getRatePlanId());
        TieredPricing entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TieredPricing not found"));
        mapper.updateEntity(entity, dto);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy("system");
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        TieredPricing entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TieredPricing not found"));
        entity.setIsDeleted(true);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy("system");
        repository.save(entity);
    }

    @Override
    public TieredPricingDTO getById(Long id) {
        TieredPricing entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TieredPricing not found"));
        return mapper.toDTO(entity);
    }

    @Override
    public List<TieredPricingDTO> getAll() {
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
