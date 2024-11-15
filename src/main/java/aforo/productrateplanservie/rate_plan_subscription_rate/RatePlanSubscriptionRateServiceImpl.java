package aforo.productrateplanservie.rate_plan_subscription_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetails;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetailsRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.UpdateRatePlanSubscriptionRateDetailsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


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
        RatePlanSubscriptionRate ratePlanSubscriptionRate = ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)
                .orElseThrow(() -> new NotFoundException("Rate Plan Subscription Rate not found with id: " + ratePlanSubscriptionRateId));

        // Map details to DTO and return
        return ratePlanSubscriptionRateMapper.updateRatePlanSubscriptionRateDTO(ratePlanSubscriptionRate, new RatePlanSubscriptionRateDTO());
    }


    @Override
    public Long create(Long ratePlanId, CreateRatePlanSubscriptionRateRequest ratePlanSubscriptionRateRequest) {
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
                .orElseThrow(() -> new NotFoundException("RatePlan not found with id: " + ratePlanId));

        // Map the request to entity with associated RatePlan
        RatePlanSubscriptionRate ratePlanSubscriptionRate = ratePlanSubscriptionRateMapper.mapToEntity(ratePlanSubscriptionRateRequest, ratePlan);

        // Save RatePlanSubscriptionRate with details
        RatePlanSubscriptionRate savedRatePlanSubscriptionRate = ratePlanSubscriptionRateRepository.save(ratePlanSubscriptionRate);

        return savedRatePlanSubscriptionRate.getRatePlanSubscriptionRateId();
    }

    @Override
    public void update(Long ratePlanId, Long ratePlanSubscriptionRateId, UpdateRatePlanSubscriptionRateRequest updateRequest) {
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new NotFoundException("RatePlan with id " + ratePlanId + " not found");
        }

        RatePlanSubscriptionRate existingRatePlanSubscriptionRate = ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanSubscriptionRate with id " + ratePlanSubscriptionRateId + " not found"));

        boolean isModified = false;

        // Update main RatePlanSubscriptionRate fields if provided
        if (updateRequest.getRatePlanSubscriptionDescription() != null) {
            existingRatePlanSubscriptionRate.setRatePlanSubscriptionDescription(updateRequest.getRatePlanSubscriptionDescription());
            isModified = true;
        }
        if (updateRequest.getDescription() != null) {
            existingRatePlanSubscriptionRate.setDescription(updateRequest.getDescription());
            isModified = true;
        }
        if (updateRequest.getUnitType() != null) {
            existingRatePlanSubscriptionRate.setUnitType(updateRequest.getUnitType());
            isModified = true;
        }
        if (updateRequest.getUnitMeasurement() != null) {
            existingRatePlanSubscriptionRate.setUnitMeasurement(updateRequest.getUnitMeasurement());
            isModified = true;
        }
        if (updateRequest.getUnitBillingFrequency() != null) {
            existingRatePlanSubscriptionRate.setUnitBillingFrequency(updateRequest.getUnitBillingFrequency());
            isModified = true;
        }
        if (updateRequest.getUnitPriceFixedFrequency() != null) {
            existingRatePlanSubscriptionRate.setUnitPriceFixedFrequency(updateRequest.getUnitPriceFixedFrequency());
            isModified = true;
        }

        // Handle updating of nested RatePlanSubscriptionRateDetails if provided
        if (updateRequest.getRatePlanSubscriptionRateDetails() != null) {
            isModified |= updateRatePlanSubscriptionRateDetails(existingRatePlanSubscriptionRate, updateRequest.getRatePlanSubscriptionRateDetails());
        }

        // Save changes if any modifications were made
        if (isModified) {
            ratePlanSubscriptionRateRepository.save(existingRatePlanSubscriptionRate);
        }
    }

    private boolean updateRatePlanSubscriptionRateDetails(RatePlanSubscriptionRate existingRatePlanSubscriptionRate,
                                                          Set<UpdateRatePlanSubscriptionRateDetailsRequest> detailsRequests) {
        boolean isModified = false;
        Set<RatePlanSubscriptionRateDetails> existingDetails = existingRatePlanSubscriptionRate.getRatePlanSubscriptionRateDetails();

        for (UpdateRatePlanSubscriptionRateDetailsRequest detailRequest : detailsRequests) {
            RatePlanSubscriptionRateDetails detail = existingDetails.stream()
                    .filter(d -> d.getId().equals(detailRequest.getId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("RatePlanSubscriptionRateDetails not found for ID: " + detailRequest.getId()));

            if (detailRequest.getUnitPriceFixed() != null && !detailRequest.getUnitPriceFixed().equals(detail.getUnitPriceFixed())) {
                detail.setUnitPriceFixed(detailRequest.getUnitPriceFixed());
                isModified = true;
            }
            if (detailRequest.getSubscriptionMaxUnitQuantity() != null && !detailRequest.getSubscriptionMaxUnitQuantity().equals(detail.getSubscriptionMaxUnitQuantity())) {
                detail.setSubscriptionMaxUnitQuantity(detailRequest.getSubscriptionMaxUnitQuantity());
                isModified = true;
            }
        }
        return isModified;
    }




    @Override
    @Transactional
    public void delete(final Long ratePlanSubscriptionRateId) {
        // Check if the record exists
        RatePlanSubscriptionRate ratePlanSubscriptionRate = ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)
                .orElseThrow(() -> new NotFoundException("Rate Plan Subscription Rate not found with id: " + ratePlanSubscriptionRateId));
        // Delete the record
        ratePlanSubscriptionRateRepository.delete(ratePlanSubscriptionRate);
    }


//        @Override
//        public ReferencedWarning getReferencedWarning ( final Long ratePlanSubscriptionRateId){
//            final ReferencedWarning referencedWarning = new ReferencedWarning();
//            final RatePlanSubscriptionRate ratePlanSubscriptionRate = ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)
//                    .orElseThrow(NotFoundException::new);
//            final RatePlanSubscriptionRateDetails ratePlanSubscriptionRateRatePlanSubscriptionRateDetails = ratePlanSubscriptionRateDetailsRepository.findFirstByRatePlanSubscriptionRate(ratePlanSubscriptionRate);
//            if (ratePlanSubscriptionRateRatePlanSubscriptionRateDetails != null) {
//                referencedWarning.setKey("ratePlanSubscriptionRate.ratePlanSubscriptionRateDetails.ratePlanSubscriptionRate.referenced");
//                referencedWarning.addParam(ratePlanSubscriptionRateRatePlanSubscriptionRateDetails.getId());
//                return referencedWarning;
//            }
//            return null;
//        }

    }
