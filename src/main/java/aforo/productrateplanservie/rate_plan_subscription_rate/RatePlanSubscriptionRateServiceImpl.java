package aforo.productrateplanservie.rate_plan_subscription_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_flat_rate.RatePlanFlatRate;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetails;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetailsRepository;
import aforo.productrateplanservie.util.NotFoundException;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RatePlanSubscriptionRateServiceImpl implements RatePlanSubscriptionRateService {

    private final RatePlanSubscriptionRateRepository ratePlanSubscriptionRateRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanSubscriptionRateMapper ratePlanSubscriptionRateMapper;
    private final RatePlanSubscriptionRateDetailsRepository ratePlanSubscriptionRateDetailsRepository;

    public RatePlanSubscriptionRateServiceImpl(
            final RatePlanSubscriptionRateRepository ratePlanSubscriptionRateRepository,
            final RatePlanRepository ratePlanRepository,
            final RatePlanSubscriptionRateMapper ratePlanSubscriptionRateMapper,
            final RatePlanSubscriptionRateDetailsRepository ratePlanSubscriptionRateDetailsRepository) {
        this.ratePlanSubscriptionRateRepository = ratePlanSubscriptionRateRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanSubscriptionRateMapper = ratePlanSubscriptionRateMapper;
        this.ratePlanSubscriptionRateDetailsRepository = ratePlanSubscriptionRateDetailsRepository;
    }

    @Override
    public Page<RatePlanSubscriptionRateDTO> findAll(final String filter, final Pageable pageable) {
        Page<RatePlanSubscriptionRate> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = ratePlanSubscriptionRateRepository.findAllByRatePlanSubscriptionRateId(longFilter, pageable);
        } else {
            page = ratePlanSubscriptionRateRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlanSubscriptionRate -> ratePlanSubscriptionRateMapper.updateRatePlanSubscriptionRateDTO(ratePlanSubscriptionRate, new RatePlanSubscriptionRateDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public RatePlanSubscriptionRateDTO get(final Long ratePlanSubscriptionRateId) {
        return ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)
                .map(ratePlanSubscriptionRate -> ratePlanSubscriptionRateMapper.updateRatePlanSubscriptionRateDTO(ratePlanSubscriptionRate, new RatePlanSubscriptionRateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(Long ratePlanId, RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO) {
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
                .orElseThrow(() -> new NotFoundException("RatePlan not found with id: " + ratePlanId));
        RatePlanSubscriptionRate ratePlanSubscriptionRate = new RatePlanSubscriptionRate();
        ratePlanSubscriptionRateMapper.updateRatePlanSubscriptionRate(ratePlanSubscriptionRateDTO, ratePlanSubscriptionRate, ratePlanRepository);
        // Set the associated RatePlan
        ratePlanSubscriptionRate.setRatePlan(ratePlan);

        RatePlanSubscriptionRate savedRatePlanSubscriptionRate = ratePlanSubscriptionRateRepository.save(ratePlanSubscriptionRate);

        // Save RatePlanFlatRateDetails if present
        if (ratePlanSubscriptionRateDTO.getRatePlanSubscriptionRateDetailsDTO() != null) {
            for (RatePlanSubscriptionRateDetailsDTO detailsDTO : ratePlanSubscriptionRateDTO.getRatePlanSubscriptionRateDetailsDTO()) {
                RatePlanSubscriptionRateDetails details = ratePlanSubscriptionRateMapper.mapToRatePlanSubscriptionRateDetails(detailsDTO);
                details.setRatePlanSubscriptionRate(savedRatePlanSubscriptionRate); // Ensure the relationship is set
                ratePlanSubscriptionRateDetailsRepository.save(details); // Save the details object
            }
        }

        return savedRatePlanSubscriptionRate.getRatePlanSubscriptionRateId();
    }
        @Override
        public void update ( final Long ratePlanSubscriptionRateId, RatePlanSubscriptionRateDTO
        ratePlanSubscriptionRateDTO){
            final RatePlanSubscriptionRate ratePlanSubscriptionRate = ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)
                    .orElseThrow(NotFoundException::new);
            ratePlanSubscriptionRateMapper.updateRatePlanSubscriptionRate(ratePlanSubscriptionRateDTO, ratePlanSubscriptionRate, ratePlanRepository);
            ratePlanSubscriptionRateRepository.save(ratePlanSubscriptionRate);
        }

        @Override
        @Transactional
        public void delete ( final Long ratePlanSubscriptionRateId){
            ratePlanSubscriptionRateRepository.deleteById(ratePlanSubscriptionRateId);
        }

        @Override
        public ReferencedWarning getReferencedWarning ( final Long ratePlanSubscriptionRateId){
            final ReferencedWarning referencedWarning = new ReferencedWarning();
            final RatePlanSubscriptionRate ratePlanSubscriptionRate = ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)
                    .orElseThrow(NotFoundException::new);
            final RatePlanSubscriptionRateDetails ratePlanSubscriptionRateRatePlanSubscriptionRateDetails = ratePlanSubscriptionRateDetailsRepository.findFirstByRatePlanSubscriptionRate(ratePlanSubscriptionRate);
            if (ratePlanSubscriptionRateRatePlanSubscriptionRateDetails != null) {
                referencedWarning.setKey("ratePlanSubscriptionRate.ratePlanSubscriptionRateDetails.ratePlanSubscriptionRate.referenced");
                referencedWarning.addParam(ratePlanSubscriptionRateRatePlanSubscriptionRateDetails.getId());
                return referencedWarning;
            }
            return null;
        }

    }
