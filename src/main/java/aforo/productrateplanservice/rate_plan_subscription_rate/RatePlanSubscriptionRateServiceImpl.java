package aforo.productrateplanservice.rate_plan_subscription_rate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aforo.productrateplanservice.exception.NotFoundException;
import aforo.productrateplanservice.rate_plan.RatePlan;
import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import aforo.productrateplanservice.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetails;
import aforo.productrateplanservice.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetailsDTO;
import aforo.productrateplanservice.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetailsRepository;
import aforo.productrateplanservice.rate_plan_subscription_rate_details.UpdateRatePlanSubscriptionRateDetailsRequest;
import aforo.productrateplanservice.rate_plan_tiered_rate.RatePlanTieredRateDTO;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


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
    @Transactional
    public void update(Long ratePlanId, Long ratePlanSubscriptionRateId, UpdateRatePlanSubscriptionRateRequest updateRequest) {
        // Check if RatePlan exists
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new NotFoundException("RatePlan with id " + ratePlanId + " not found");
        }

        // Retrieve the existing RatePlanSubscriptionRate entity
        RatePlanSubscriptionRate existingRatePlanSubscriptionRate = ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanSubscriptionRate with id " + ratePlanSubscriptionRateId + " not found"));

        boolean isModified = updateMainSubscriptionRateFields(existingRatePlanSubscriptionRate, updateRequest);

        // Handle nested details
        if (updateRequest.getRatePlanSubscriptionRateDetails() != null) {
            isModified |= updateSubscriptionRateDetails(existingRatePlanSubscriptionRate, updateRequest.getRatePlanSubscriptionRateDetails());
        }

        // Save changes only if modifications were made
        if (isModified) {
            ratePlanSubscriptionRateRepository.save(existingRatePlanSubscriptionRate);
        }
    }
    private boolean updateMainSubscriptionRateFields(RatePlanSubscriptionRate existingRatePlanSubscriptionRate, UpdateRatePlanSubscriptionRateRequest updateRequest) {
        boolean isModified = false;

        if (updateRequest.getRatePlanSubscriptionDescription() != null &&
                !Objects.equals(existingRatePlanSubscriptionRate.getRatePlanSubscriptionDescription(), updateRequest.getRatePlanSubscriptionDescription())) {
            existingRatePlanSubscriptionRate.setRatePlanSubscriptionDescription(updateRequest.getRatePlanSubscriptionDescription());
            isModified = true;
        }

        if (updateRequest.getDescription() != null &&
                !Objects.equals(existingRatePlanSubscriptionRate.getDescription(), updateRequest.getDescription())) {
            existingRatePlanSubscriptionRate.setDescription(updateRequest.getDescription());
            isModified = true;
        }

        if (updateRequest.getUnitType() != null &&
                !Objects.equals(existingRatePlanSubscriptionRate.getUnitType(), updateRequest.getUnitType())) {
            existingRatePlanSubscriptionRate.setUnitType(updateRequest.getUnitType());
            isModified = true;
        }

        if (updateRequest.getUnitMeasurement() != null &&
                !Objects.equals(existingRatePlanSubscriptionRate.getUnitMeasurement(), updateRequest.getUnitMeasurement())) {
            existingRatePlanSubscriptionRate.setUnitMeasurement(updateRequest.getUnitMeasurement());
            isModified = true;
        }

        if (updateRequest.getUnitBillingFrequency() != null &&
                !Objects.equals(existingRatePlanSubscriptionRate.getUnitBillingFrequency(), updateRequest.getUnitBillingFrequency())) {
            existingRatePlanSubscriptionRate.setUnitBillingFrequency(updateRequest.getUnitBillingFrequency());
            isModified = true;
        }

        if (updateRequest.getUnitPriceFixedFrequency() != null &&
                !Objects.equals(existingRatePlanSubscriptionRate.getUnitPriceFixedFrequency(), updateRequest.getUnitPriceFixedFrequency())) {
            existingRatePlanSubscriptionRate.setUnitPriceFixedFrequency(updateRequest.getUnitPriceFixedFrequency());
            isModified = true;
        }

        return isModified;
    }
    private boolean updateSubscriptionRateDetails(RatePlanSubscriptionRate existingRatePlanSubscriptionRate,
                                                  Set<UpdateRatePlanSubscriptionRateDetailsRequest> detailsRequests) {
        boolean isModified = false;
        Set<RatePlanSubscriptionRateDetails> existingDetails = existingRatePlanSubscriptionRate.getRatePlanSubscriptionRateDetails();

        if (detailsRequests.size() == 1 && existingDetails.size() == 1) {
            // Handle single detail case
            isModified = updateSingleSubscriptionDetail(existingDetails.iterator().next(), detailsRequests.iterator().next());
        } else {
            // Handle multiple detail cases
            isModified = updateMultipleSubscriptionDetails(existingDetails, detailsRequests);
        }

        return isModified;
    }

    private boolean updateSingleSubscriptionDetail(RatePlanSubscriptionRateDetails existingDetail, UpdateRatePlanSubscriptionRateDetailsRequest detailRequest) {
        boolean isModified = false;

        if (detailRequest.getUnitPriceFixed() != null &&
                !Objects.equals(existingDetail.getUnitPriceFixed(), detailRequest.getUnitPriceFixed())) {
            existingDetail.setUnitPriceFixed(detailRequest.getUnitPriceFixed());
            isModified = true;
        }

        if (detailRequest.getSubscriptionMaxUnitQuantity() != null &&
                !Objects.equals(existingDetail.getSubscriptionMaxUnitQuantity(), detailRequest.getSubscriptionMaxUnitQuantity())) {
            existingDetail.setSubscriptionMaxUnitQuantity(detailRequest.getSubscriptionMaxUnitQuantity());
            isModified = true;
        }

        return isModified;
    }

    private boolean updateMultipleSubscriptionDetails(Set<RatePlanSubscriptionRateDetails> existingDetails,
                                                      Set<UpdateRatePlanSubscriptionRateDetailsRequest> detailsRequests) {
        boolean isModified = false;

        Map<Long, UpdateRatePlanSubscriptionRateDetailsRequest> requestMap = detailsRequests.stream()
                .filter(request -> request.getId() != null) // Ensure ID is present
                .collect(Collectors.toMap(UpdateRatePlanSubscriptionRateDetailsRequest::getId, request -> request));

        for (RatePlanSubscriptionRateDetails existingDetail : existingDetails) {
            if (requestMap.containsKey(existingDetail.getId())) {
                UpdateRatePlanSubscriptionRateDetailsRequest detailRequest = requestMap.get(existingDetail.getId());
                isModified |= updateSingleSubscriptionDetail(existingDetail, detailRequest);
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

@Override
public long getRatePlanSubscriptionRateCount() {
    return ratePlanSubscriptionRateRepository.count();
    }
}
