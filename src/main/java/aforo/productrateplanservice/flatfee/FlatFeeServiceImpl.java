package aforo.productrateplanservice.flatfee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlatFeeServiceImpl implements FlatFeeService {

    private final FlatFeeRepository repository;
    private final RatePlanRepository ratePlanRepository;
    private final FlatFeeMapper mapper;

    @Override
    public FlatFeeDTO create(FlatFeeCreateUpdateDTO dto) {
        validateRatePlanExists(dto.getRatePlanId());
        FlatFee entity = mapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy("system");
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public FlatFeeDTO update(Long id, FlatFeeCreateUpdateDTO dto) {
        validateRatePlanExists(dto.getRatePlanId());
        FlatFee entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("FlatFee not found"));
        mapper.updateEntity(entity, dto);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy("system");
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        FlatFee entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("FlatFee not found"));
        entity.setIsDeleted(true);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy("system");
        repository.save(entity);
    }

    @Override
    public FlatFeeDTO getById(Long id) {
        FlatFee entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("FlatFee not found"));
        return mapper.toDTO(entity);
    }

    @Override
    public List<FlatFeeDTO> getAll() {
        return repository.findAll().stream()
                .filter(f -> !Boolean.TRUE.equals(f.getIsDeleted()))
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    private void validateRatePlanExists(Long ratePlanId) {
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new IllegalArgumentException("Invalid ratePlanId: " + ratePlanId);
        }
    }
}
