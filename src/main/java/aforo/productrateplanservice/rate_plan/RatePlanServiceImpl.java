package aforo.productrateplanservice.rate_plan;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aforo.productrateplanservice.exception.NotFoundException;
import aforo.productrateplanservice.exception.ValidationException;
import aforo.productrateplanservice.product.entity.Product;
import aforo.productrateplanservice.product.repository.ProductRepository;
import aforo.productrateplanservice.product.enums.RatePlanType;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
@Service
@RequiredArgsConstructor
public class RatePlanServiceImpl implements RatePlanService {

    private final RatePlanRepository ratePlanRepository;
    private final ProductRepository productRepository;
    private final RatePlanAssembler ratePlanAssembler;

    @Override
    public RatePlanDTO createRatePlan(CreateRatePlanRequest request) {
        if (ratePlanRepository.existsByRatePlanNameIgnoreCaseTrimmed(request.getRatePlanName())) {
            throw new ValidationException("Rate plan name must be unique.");
        }

        if (!EnumSet.of(
                RatePlanType.FLAT_FEE,
                RatePlanType.VOLUME_BASED,
                RatePlanType.TIERED_PRICING,
                RatePlanType.STAIR_STEP_PRICING
        ).contains(request.getRatePlanType())) {
            throw new ValidationException("Invalid rate plan type.");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Product ID not found"));

        if (!product.getProductName().trim().equalsIgnoreCase(request.getProductName().trim())) {
            throw new ValidationException("Product ID and product name do not match.");
        }

        RatePlan ratePlan = RatePlan.builder()
                .product(product)
                .ratePlanName(request.getRatePlanName().trim())
                .description(request.getDescription())
                .ratePlanType(request.getRatePlanType())
                .status(request.getStatus())
                .build();

        RatePlan saved = ratePlanRepository.save(ratePlan);
        return ratePlanAssembler.toDTO(saved);
    }
@Override
@Transactional
public RatePlanDTO updateRatePlan(Long id, UpdateRatePlanRequest request) {
    RatePlan ratePlan = ratePlanRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Rate plan not found"));

    if (request.getRatePlanName() != null &&
        !request.getRatePlanName().trim().equalsIgnoreCase(ratePlan.getRatePlanName().trim())) {
        if (ratePlanRepository.existsByRatePlanNameIgnoreCaseTrimmed(request.getRatePlanName())) {
            throw new ValidationException("Rate plan name must be unique.");
        }
        ratePlan.setRatePlanName(request.getRatePlanName().trim());
    }

    if (request.getProductId() != null && request.getProductName() != null) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Product ID not found"));

        if (!product.getProductName().trim().equalsIgnoreCase(request.getProductName().trim())) {
            throw new ValidationException("Product ID and Product Name do not match.");
        }
        ratePlan.setProduct(product);
    }

    if (request.getDescription() != null) ratePlan.setDescription(request.getDescription());
    if (request.getRatePlanType() != null) ratePlan.setRatePlanType(request.getRatePlanType());
    if (request.getStatus() != null) ratePlan.setStatus(request.getStatus());

    RatePlan updated = ratePlanRepository.save(ratePlan);
    return ratePlanAssembler.toDTO(updated);
}

    @Override
    public RatePlanDTO getRatePlanById(Long id) {
        RatePlan ratePlan = ratePlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rate plan not found"));
        return ratePlanAssembler.toDTO(ratePlan);
    }

    @Override
    public List<RatePlanDTO> getAllRatePlans() {
        return ratePlanRepository.findAll().stream()
                .map(ratePlanAssembler::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRatePlan(Long id) {
        ratePlanRepository.deleteById(id);
    }
}
