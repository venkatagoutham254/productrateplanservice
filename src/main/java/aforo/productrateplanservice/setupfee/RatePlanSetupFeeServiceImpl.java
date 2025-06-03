package aforo.productrateplanservice.setupfee;

import aforo.productrateplanservice.setupfee.RatePlanSetupFeeDTO;
import aforo.productrateplanservice.exception.ResourceNotFoundException;
import aforo.productrateplanservice.setupfee.RatePlanSetupFeeMapper;
import aforo.productrateplanservice.rate_plan.RatePlan;
import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import aforo.productrateplanservice.setupfee.RatePlanSetupFeeRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatePlanSetupFeeServiceImpl implements RatePlanSetupFeeService {

    private final RatePlanRepository ratePlanRepository;
    private final RatePlanSetupFeeRepository setupFeeRepository;
    private final RatePlanSetupFeeMapper setupFeeMapper;

    @Override
    public void createSetupFee(RatePlanSetupFeeDTO dto) {
        RatePlan ratePlan = ratePlanRepository.findById(dto.getRatePlanId())
                .orElseThrow(() -> new ResourceNotFoundException("RatePlan not found"));

        RatePlanSetupFee setupFee = setupFeeMapper.toEntity(dto, ratePlan);
        setupFeeRepository.save(setupFee);
    }
    @Override
public List<RatePlanSetupFeeDTO> getAll() {
    return setupFeeRepository.findByIsDeletedFalse()
            .stream()
            .map(this::toDTO)
            .toList();
}

@Override
public RatePlanSetupFeeDTO getById(Long id) {
    RatePlanSetupFee setupFee = setupFeeRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("Setup Fee not found"));
    return toDTO(setupFee);
}

@Override
public void updateSetupFee(Long id, RatePlanSetupFeeDTO dto) {
    RatePlanSetupFee setupFee = setupFeeRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("Setup Fee not found"));

    setupFee.setOneTimeFee(dto.getOneTimeFee());
    setupFee.setApplicationTiming(dto.getApplicationTiming());
    setupFee.setInvoiceDescription(dto.getInvoiceDescription());
    setupFee.setUpdatedBy("system");
    setupFee.setUpdatedAt(LocalDateTime.now());
    setupFee.setVersion(setupFee.getVersion() + 1);

    setupFeeRepository.save(setupFee);
}

@Override
public void deleteSetupFee(Long id) {
    RatePlanSetupFee setupFee = setupFeeRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("Setup Fee not found"));

    setupFee.setIsDeleted(true);
    setupFee.setIsActive(false);
    setupFee.setUpdatedBy("system");
    setupFee.setUpdatedAt(LocalDateTime.now());
    setupFee.setVersion(setupFee.getVersion() + 1);

    setupFeeRepository.save(setupFee);
}

// Helper
private RatePlanSetupFeeDTO toDTO(RatePlanSetupFee fee) {
    return RatePlanSetupFeeDTO.builder()
            .ratePlanId(fee.getRatePlan().getRatePlanId())
            .oneTimeFee(fee.getOneTimeFee())
            .applicationTiming(fee.getApplicationTiming())
            .invoiceDescription(fee.getInvoiceDescription())
            .build();
}

}
