package aforo.productrateplanservice.rate_plan;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aforo.productrateplanservice.currencies.Currencies;
import aforo.productrateplanservice.currencies.CurrenciesRepository;
import aforo.productrateplanservice.exception.NotFoundException;
import aforo.productrateplanservice.exception.ValidationException;
import aforo.productrateplanservice.product.repository.ProductRepository;
import aforo.productrateplanservice.rate_plan_flat_rate.RatePlanFlatRate;
import aforo.productrateplanservice.rate_plan_flat_rate.RatePlanFlatRateRepository;
import aforo.productrateplanservice.rate_plan_freemium_rate.RatePlanFreemiumRate;
import aforo.productrateplanservice.rate_plan_freemium_rate.RatePlanFreemiumRateRepository;
import aforo.productrateplanservice.rate_plan_subscription_rate.RatePlanSubscriptionRate;
import aforo.productrateplanservice.rate_plan_subscription_rate.RatePlanSubscriptionRateRepository;
import aforo.productrateplanservice.rate_plan_tiered_rate.RatePlanTieredRate;
import aforo.productrateplanservice.rate_plan_tiered_rate.RatePlanTieredRateRepository;
import aforo.productrateplanservice.rate_plan_usage_based.RatePlanUsageBased;
import aforo.productrateplanservice.rate_plan_usage_based.RatePlanUsageBasedRepository;
import aforo.productrateplanservice.validation.RatePlanValidator;
import aforo.productrateplanservice.validation.ValidationResult;

import java.util.Objects;
import java.util.Optional;

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
    public Long create(Long productId,  final CreateRatePlanRequest createRatePlanRequest) {


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

        RatePlanDTO ratePlanDTO = ratePlanMapper.updateRatePlanDTO(ratePlan, new RatePlanDTO());
        boolean isModified = false;

        if (createRatePlanRequest.getRatePlanName() != null && !createRatePlanRequest.getRatePlanName().trim().isEmpty() &&
                !Objects.equals(ratePlan.getRatePlanName(), createRatePlanRequest.getRatePlanName())) {
            ratePlanDTO.setRatePlanName(createRatePlanRequest.getRatePlanName());
            isModified = true;
        }

        if (createRatePlanRequest.getDescription() != null && !createRatePlanRequest.getDescription().trim().isEmpty() &&
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
        ratePlanRepository.deleteById(ratePlanId);
    }

    @Override
    public Optional<Long> getSelectedRatePlanTypeId(Long ratePlanId, String ratePlanType) {
        return ratePlanRepository.findById(ratePlanId)
                .flatMap(ratePlan -> switch (ratePlanType) {
                    case "FLAT_RATE" -> ratePlanFlatRateRepository.findFirstByRatePlan(ratePlan).map(RatePlanFlatRate::getRatePlanFlatRateId);
                    case "FREEMIUM" -> ratePlanFreemiumRateRepository.findFirstByRatePlan(ratePlan).map(RatePlanFreemiumRate::getRatePlanFreemiumRateId);
                    case "SUBSCRIPTION" -> ratePlanSubscriptionRateRepository.findFirstByRatePlan(ratePlan).map(RatePlanSubscriptionRate::getRatePlanSubscriptionRateId);
                    case "TIERED" -> ratePlanTieredRateRepository.findFirstByRatePlan(ratePlan).map(RatePlanTieredRate::getRatePlanTieredRateId);
                    case "USAGE_BASED" -> ratePlanUsageBasedRepository.findFirstByRatePlan(ratePlan).map(RatePlanUsageBased::getRatePlanUsageRateId);
                    default -> Optional.empty();
                });
    }

    @Override
    public long getRatePlanCount() {
        return ratePlanRepository.count();
    }
}
