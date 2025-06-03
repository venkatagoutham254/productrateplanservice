package aforo.productrateplanservice.discount;

import aforo.productrateplanservice.exception.ResourceNotFoundException;
import aforo.productrateplanservice.rate_plan.RatePlan;
import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository repository;
    private final RatePlanRepository ratePlanRepository;
    private final DiscountMapper mapper;

    @Override
    public void create(DiscountDTO dto) {
        RatePlan ratePlan = ratePlanRepository.findById(dto.getRatePlanId())
                .orElseThrow(() -> new ResourceNotFoundException("RatePlan not found"));
        Discount entity = mapper.toEntity(dto, ratePlan);
        repository.save(entity);
    }

    @Override
    public List<DiscountDTO> getAll() {
        return repository.findByIsDeletedFalse()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public DiscountDTO getById(Long id) {
        Discount entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found"));
        return toDTO(entity);
    }

    @Override
    public void update(Long id, DiscountDTO dto) {
        Discount entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found"));

        entity.setDiscountType(dto.getDiscountType());
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
        Discount entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found"));

        entity.setIsDeleted(true);
        entity.setIsActive(false);
        entity.setUpdatedBy("system");
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);
        repository.save(entity);
    }

    private DiscountDTO toDTO(Discount entity) {
        return DiscountDTO.builder()
                .ratePlanId(entity.getRatePlan().getRatePlanId())
                .discountType(entity.getDiscountType())
                .eligibility(entity.getEligibility())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }
}
