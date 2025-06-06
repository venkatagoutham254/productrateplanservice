package aforo.productrateplanservice.volumepricing;

import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VolumePricingServiceImpl implements VolumePricingService {

    private final VolumePricingRepository repository;
    private final RatePlanRepository ratePlanRepository;
    private final VolumePricingMapper mapper;

    @Override
    public VolumePricingDTO create(VolumePricingDTO dto) {
        validateRatePlanExists(dto.getRatePlanId());
        VolumePricing entity = mapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy("system");
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public VolumePricingDTO update(Long id, VolumePricingDTO dto) {
        validateRatePlanExists(dto.getRatePlanId());
        VolumePricing entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("VolumePricing not found"));
        mapper.updateEntity(entity, dto);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy("system");
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        VolumePricing entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("VolumePricing not found"));
        entity.setIsDeleted(true);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy("system");
        repository.save(entity);
    }

    @Override
    public VolumePricingDTO getById(Long id) {
        VolumePricing entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("VolumePricing not found"));
        return mapper.toDTO(entity);
    }

    @Override
    public List<VolumePricingDTO> getAll() {
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
