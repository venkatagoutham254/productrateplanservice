package aforo.productrateplanservice.freemium;

import aforo.productrateplanservice.exception.ResourceNotFoundException;
import aforo.productrateplanservice.rate_plan.RatePlan;
import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FreemiumServiceImpl implements FreemiumService {

    private final FreemiumRepository repository;
    private final RatePlanRepository ratePlanRepository;
    private final FreemiumMapper mapper;

    @Override
    public void create(FreemiumDTO dto) {
        RatePlan ratePlan = ratePlanRepository.findById(dto.getRatePlanId())
                .orElseThrow(() -> new ResourceNotFoundException("RatePlan not found"));
        Freemium entity = mapper.toEntity(dto, ratePlan);
        repository.save(entity);
    }

    @Override
    public List<FreemiumDTO> getAll() {
        return repository.findByIsDeletedFalse()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public FreemiumDTO getById(Long id) {
        Freemium entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Freemium not found"));
        return toDTO(entity);
    }

    @Override
    public void update(Long id, FreemiumDTO dto) {
        Freemium entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Freemium not found"));

        entity.setFreemiumType(dto.getFreemiumType());
        entity.setEligibility(dto.getEligibility());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setUpdatedBy("system");
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Freemium entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Freemium not found"));

        entity.setIsDeleted(true);
        entity.setIsActive(false);
        entity.setUpdatedBy("system");
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);
        repository.save(entity);
    }

    private FreemiumDTO toDTO(Freemium entity) {
        return FreemiumDTO.builder()
                .ratePlanId(entity.getRatePlan().getRatePlanId())
                .freemiumType(entity.getFreemiumType())
                .eligibility(entity.getEligibility())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }
}
