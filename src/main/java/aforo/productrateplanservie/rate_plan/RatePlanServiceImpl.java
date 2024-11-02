package aforo.productrateplanservie.rate_plan;

import aforo.productrateplanservie.currencies.Currencies;
import aforo.productrateplanservie.currencies.CurrenciesRepository;
import aforo.productrateplanservie.exception.ValidationException;
import aforo.productrateplanservie.product.ProductRepository;
import aforo.productrateplanservie.rate_plan_flat_rate.RatePlanFlatRateRepository;
import aforo.productrateplanservie.rate_plan_freemium_rate.RatePlanFreemiumRateRepository;
import aforo.productrateplanservie.rate_plan_subscription_rate.RatePlanSubscriptionRateRepository;
import aforo.productrateplanservie.rate_plan_tiered_rate.RatePlanTieredRateRepository;
import aforo.productrateplanservie.rate_plan_usage_based.RatePlanUsageBasedRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.validation.RatePlanValidator;
import aforo.productrateplanservie.validation.ValidationResult;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class RatePlanServiceImpl implements RatePlanService {

    private final RatePlanRepository ratePlanRepository;
    private final ProductRepository productRepository;
    private final CurrenciesRepository currenciesRepository;
    private final RatePlanMapper ratePlanMapper;
    private final RatePlanUsageBasedRepository ratePlanUsageBasedRepository;
    private final RatePlanTieredRateRepository ratePlanTieredRateRepository;
    private final RatePlanFlatRateRepository ratePlanFlatRateRepository;
    private final RatePlanSubscriptionRateRepository ratePlanSubscriptionRateRepository;
    private final RatePlanFreemiumRateRepository ratePlanFreemiumRateRepository;
    private final RatePlanValidator ratePlanValidator;

    public RatePlanServiceImpl(final RatePlanRepository ratePlanRepository, final ProductRepository productRepository,
                               final CurrenciesRepository currenciesRepository, final RatePlanMapper ratePlanMapper,
                               final RatePlanUsageBasedRepository ratePlanUsageBasedRepository,
                               final RatePlanTieredRateRepository ratePlanTieredRateRepository,
                               final RatePlanFlatRateRepository ratePlanFlatRateRepository,
                               final RatePlanSubscriptionRateRepository ratePlanSubscriptionRateRepository,
                               final RatePlanFreemiumRateRepository ratePlanFreemiumRateRepository,
                               final RatePlanValidator ratePlanValidator) {
        this.ratePlanRepository = ratePlanRepository;
        this.productRepository = productRepository;
        this.currenciesRepository = currenciesRepository;
        this.ratePlanMapper = ratePlanMapper;
        this.ratePlanUsageBasedRepository = ratePlanUsageBasedRepository;
        this.ratePlanTieredRateRepository = ratePlanTieredRateRepository;
        this.ratePlanFlatRateRepository = ratePlanFlatRateRepository;
        this.ratePlanSubscriptionRateRepository = ratePlanSubscriptionRateRepository;
        this.ratePlanFreemiumRateRepository = ratePlanFreemiumRateRepository;
        this.ratePlanValidator = ratePlanValidator;
    }

    @Override
    public Page<RatePlanDTO> findAll(final String filter, final Pageable pageable) {
        Page<RatePlan> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = ratePlanRepository.findAllByRatePlanId(longFilter, pageable);
        } else {
            page = ratePlanRepository.findAll(pageable);
        }
        return new PageImpl<>(
                page.getContent().stream()
                        .map(ratePlan -> ratePlanMapper.updateRatePlanDTO(ratePlan, new RatePlanDTO())).toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public Page<RatePlanDTO> getRatePlansByProductId(Long productId, String filter, Pageable pageable) {
        Page<RatePlan> page;

        if (filter != null && !filter.isEmpty()) {
            // Parse filter and apply it
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (NumberFormatException e) {
                // Keep null if parsing fails
            }
            page = ratePlanRepository.findByProductIdAndRatePlanId(productId, longFilter, pageable);
        } else {
            page = ratePlanRepository.findByProductId(productId, pageable);
        }

        return new PageImpl<>(
                page.getContent().stream()
                        .map(ratePlan -> ratePlanMapper.updateRatePlanDTO(ratePlan, new RatePlanDTO()))
                        .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public RatePlanDTO get(final Long ratePlanId) {
        return ratePlanRepository.findById(ratePlanId)
                .map(ratePlan -> ratePlanMapper.updateRatePlanDTO(ratePlan, new RatePlanDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(Long productId, @Valid final CreateRatePlanRequest createRatePlanRequest) {
        productRepository.findById(productId).orElseThrow(() -> new NotFoundException("productId not found with ID: " + productId));
        final ValidationResult validationResult = ratePlanValidator.validate(createRatePlanRequest);
        if (!validationResult.isValid()) {
            throw new ValidationException("Invalid rate plan request: " +
                    String.join(", ", validationResult.getErrors()));
        }

        final RatePlan ratePlan = new RatePlan();
        RatePlanDTO ratePlanDTO = ratePlanMapper.createRatePlanRequestToRatePlanDTO(createRatePlanRequest);
        ratePlanDTO.setProductId(productId);

        ratePlanMapper.updateRatePlan(ratePlanDTO, ratePlan, productRepository, currenciesRepository);
        return (Long) ratePlanRepository.save(ratePlan).getRatePlanId();
    }

    @Transactional
    @Override
    public void update(final Long ratePlanId, final CreateRatePlanRequest createRatePlanRequest) {
        final RatePlan ratePlan = ratePlanRepository.findById(ratePlanId).orElseThrow(() -> new NotFoundException("ratePlanId not found with ID: " + ratePlanId));

//        if (createRatePlanRequest.getStartDate() != null || createRatePlanRequest.getEndDate()!= null) {
//            throw new ValidationException("Can not update StartDate or EndDate: ");
//        }
        RatePlanDTO ratePlanDTO = ratePlanMapper.updateRatePlanDTO(ratePlan, new RatePlanDTO());
        boolean isModified = false;

        if (!Objects.equals(ratePlan.getRatePlanName(), createRatePlanRequest.getRatePlanName())) {
            ratePlanDTO.setRatePlanName(createRatePlanRequest.getRatePlanName());
            isModified = true;
        }

        if (createRatePlanRequest.getDescription() != null &&
                !Objects.equals(ratePlan.getDescription(), createRatePlanRequest.getDescription())) {
            ratePlanDTO.setDescription(createRatePlanRequest.getDescription());
            isModified = true;
        }

        if (createRatePlanRequest.getRatePlanType() != null &&
                !Objects.equals(ratePlan.getRatePlanType(), createRatePlanRequest.getRatePlanType())) {
            ratePlanDTO.setRatePlanType(createRatePlanRequest.getRatePlanType());
            isModified = true;
        }

        if (createRatePlanRequest.getStatus() != null &&
                !Objects.equals(ratePlan.getStatus(), createRatePlanRequest.getStatus())) {
            ratePlanDTO.setStatus(createRatePlanRequest.getStatus());
            isModified = true;
        }

        if (createRatePlanRequest.getCurrencyId() != null) {
            currenciesRepository.findById(createRatePlanRequest.getCurrencyId())
                    .orElseThrow(() -> new ValidationException("Invalid currency ID: " + createRatePlanRequest.getCurrencyId()));

            if (!Objects.equals(ratePlan.getCurrency().getCurrencyId(), createRatePlanRequest.getCurrencyId())) {
                ratePlanDTO.setCurrencyId(createRatePlanRequest.getCurrencyId());
                isModified = true;
            }
        }

        if (isModified) {
            ratePlanMapper.updateRatePlan(ratePlanDTO, ratePlan, productRepository, currenciesRepository);
            ratePlanRepository.save(ratePlan);
        }
    }

    @Override
    @Transactional
    public void delete(final Long ratePlanId) {
        final RatePlan ratePlan = ratePlanRepository.findById(ratePlanId).orElseThrow(() -> new NotFoundException("ratePlanId not found with ID: " + ratePlanId));
        // List all the rate plan types for this rate plan
        // List child entities for each rate plan type
        // Delete child entities for each rate plan type
        // Delete all the rate plan types for this rate plan

        // Delete rate plan
        ratePlanRepository.deleteById(ratePlanId);
    }

//	@Override
//	public ReferencedWarning getReferencedWarning(final Long ratePlanId) {
//		final ReferencedWarning referencedWarning = new ReferencedWarning();
//		final RatePlan ratePlan = ratePlanRepository.findById(ratePlanId).orElseThrow(NotFoundException::new);
//		final RatePlanUsageBased ratePlanRatePlanUsageBased = ratePlanUsageBasedRepository
//				.findFirstByRatePlan(ratePlan);
//		if (ratePlanRatePlanUsageBased != null) {
//			referencedWarning.setKey("ratePlan.ratePlanUsageBased.ratePlan.referenced");
//			referencedWarning.addParam(ratePlanRatePlanUsageBased.getRatePlanUsageRateId());
//			return referencedWarning;
//		}
//		final RatePlanTieredRate ratePlanRatePlanTieredRate = ratePlanTieredRateRepository
//				.findFirstByRatePlan(ratePlan);
//		if (ratePlanRatePlanTieredRate != null) {
//			referencedWarning.setKey("ratePlan.ratePlanTieredRate.ratePlan.referenced");
//			referencedWarning.addParam(ratePlanRatePlanTieredRate.getRatePlanTieredRateId());
//			return referencedWarning;
//		}
//		final RatePlanFlatRate ratePlanRatePlanFlatRate = ratePlanFlatRateRepository.findFirstByRatePlan(ratePlan);
//		if (ratePlanRatePlanFlatRate != null) {
//			referencedWarning.setKey("ratePlan.ratePlanFlatRate.ratePlan.referenced");
//			referencedWarning.addParam(ratePlanRatePlanFlatRate.getRatePlanFlatRateId());
//			return referencedWarning;
//		}
//		final RatePlanSubscriptionRate ratePlanRatePlanSubscriptionRate = ratePlanSubscriptionRateRepository
//				.findFirstByRatePlan(ratePlan);
//		if (ratePlanRatePlanSubscriptionRate != null) {
//			referencedWarning.setKey("ratePlan.ratePlanSubscriptionRate.ratePlan.referenced");
//			referencedWarning.addParam(ratePlanRatePlanSubscriptionRate.getRatePlanSubscriptionRateId());
//			return referencedWarning;
//		}
//		final RatePlanFreemiumRate ratePlanRatePlanFreemiumRate = ratePlanFreemiumRateRepository
//				.findFirstByRatePlan(ratePlan);
//		if (ratePlanRatePlanFreemiumRate != null) {
//			referencedWarning.setKey("ratePlan.ratePlanFreemiumRate.ratePlan.referenced");
//			referencedWarning.addParam(ratePlanRatePlanFreemiumRate.getRatePlanFreemiumRateId());
//			return referencedWarning;
//		}
//		return null;
//	}


}
