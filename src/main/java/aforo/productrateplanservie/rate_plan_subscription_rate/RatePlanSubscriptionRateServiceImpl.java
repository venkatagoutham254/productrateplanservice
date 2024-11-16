package aforo.productrateplanservie.rate_plan_subscription_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetails;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetailsRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.UpdateRatePlanSubscriptionRateDetailsRequest;
import aforo.productrateplanservie.rate_plan_tiered_rate.RatePlanTieredRateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
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
    @Transactional
    public void update(Long ratePlanId, Long ratePlanSubscriptionRateId, UpdateRatePlanSubscriptionRateRequest updateRequest) {
        // Check if RatePlan exists
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new NotFoundException("RatePlan with id " + ratePlanId + " not found");
        }

        // Retrieve the existing RatePlanSubscriptionRate entity
        RatePlanSubscriptionRate existingRatePlanSubscriptionRate = ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanSubscriptionRate with id " + ratePlanSubscriptionRateId + " not found"));

        // Map existing entity to a DTO
        RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO = new RatePlanSubscriptionRateDTO();
        ratePlanSubscriptionRateMapper.updateRatePlanSubscriptionRateDTO(existingRatePlanSubscriptionRate, ratePlanSubscriptionRateDTO);

        boolean isModified = false;

        // Update main RatePlanSubscriptionRate fields using the DTO
        if (updateRequest.getRatePlanSubscriptionDescription() != null &&
                !Objects.equals(ratePlanSubscriptionRateDTO.getRatePlanSubscriptionDescription(), updateRequest.getRatePlanSubscriptionDescription())) {
            ratePlanSubscriptionRateDTO.setRatePlanSubscriptionDescription(updateRequest.getRatePlanSubscriptionDescription());
            isModified = true;
        }

        if (updateRequest.getDescription() != null &&
                !Objects.equals(ratePlanSubscriptionRateDTO.getDescription(), updateRequest.getDescription())) {
            ratePlanSubscriptionRateDTO.setDescription(updateRequest.getDescription());
            isModified = true;
        }

        if (updateRequest.getUnitType() != null &&
                !Objects.equals(ratePlanSubscriptionRateDTO.getUnitType(), updateRequest.getUnitType())) {
            ratePlanSubscriptionRateDTO.setUnitType(updateRequest.getUnitType());
            isModified = true;
        }

        if (updateRequest.getUnitMeasurement() != null &&
                !Objects.equals(ratePlanSubscriptionRateDTO.getUnitMeasurement(), updateRequest.getUnitMeasurement())) {
            ratePlanSubscriptionRateDTO.setUnitMeasurement(updateRequest.getUnitMeasurement());
            isModified = true;
        }

        if (updateRequest.getUnitBillingFrequency() != null &&
                !Objects.equals(ratePlanSubscriptionRateDTO.getUnitBillingFrequency(), updateRequest.getUnitBillingFrequency())) {
            ratePlanSubscriptionRateDTO.setUnitBillingFrequency(updateRequest.getUnitBillingFrequency());
            isModified = true;
        }

        if (updateRequest.getUnitPriceFixedFrequency() != null &&
                !Objects.equals(ratePlanSubscriptionRateDTO.getUnitPriceFixedFrequency(), updateRequest.getUnitPriceFixedFrequency())) {
            ratePlanSubscriptionRateDTO.setUnitPriceFixedFrequency(updateRequest.getUnitPriceFixedFrequency());
            isModified = true;
        }

        // Update nested RatePlanSubscriptionRateDetails
        if (updateRequest.getRatePlanSubscriptionRateDetails() != null) {
            isModified |= updateRatePlanSubscriptionRateDetails(existingRatePlanSubscriptionRate, updateRequest.getRatePlanSubscriptionRateDetails());
        }

        // Map updated DTO back to the entity and save only if modifications were made
        if (isModified) {
            ratePlanSubscriptionRateMapper.updateRatePlanSubscriptionRate(ratePlanSubscriptionRateDTO, existingRatePlanSubscriptionRate, ratePlanRepository);
            ratePlanSubscriptionRateRepository.save(existingRatePlanSubscriptionRate);
        }
    }

    private boolean updateRatePlanSubscriptionRateDetails(RatePlanSubscriptionRate existingRatePlanSubscriptionRate,
                                                          Set<UpdateRatePlanSubscriptionRateDetailsRequest> detailsRequests) {
        boolean isModified = false;

        // Retrieve existing details from the entity
        Set<RatePlanSubscriptionRateDetails> existingDetails = existingRatePlanSubscriptionRate.getRatePlanSubscriptionRateDetails();

        // Update or delete existing details
        for (UpdateRatePlanSubscriptionRateDetailsRequest detailRequest : detailsRequests) {
            RatePlanSubscriptionRateDetails detail = existingDetails.stream()
                    .filter(d -> d.getId().equals(detailRequest.getId()))
                    .findFirst()
                    .orElse(null);

            if (detail != null) {
                if (detailRequest.getUnitPriceFixed() != null && !Objects.equals(detail.getUnitPriceFixed(), detailRequest.getUnitPriceFixed())) {
                    detail.setUnitPriceFixed(detailRequest.getUnitPriceFixed());
                    isModified = true;
                }
                if (detailRequest.getSubscriptionMaxUnitQuantity() != null &&
                        !Objects.equals(detail.getSubscriptionMaxUnitQuantity(), detailRequest.getSubscriptionMaxUnitQuantity())) {
                    detail.setSubscriptionMaxUnitQuantity(detailRequest.getSubscriptionMaxUnitQuantity());
                    isModified = true;
                }
            } else {
                // Add new details
                RatePlanSubscriptionRateDetails newDetail = new RatePlanSubscriptionRateDetails();
                newDetail.setUnitPriceFixed(detailRequest.getUnitPriceFixed());
                newDetail.setSubscriptionMaxUnitQuantity(detailRequest.getSubscriptionMaxUnitQuantity());
                newDetail.setRatePlanSubscriptionRate(existingRatePlanSubscriptionRate);
                existingRatePlanSubscriptionRate.getRatePlanSubscriptionRateDetails().add(newDetail);
                isModified = true;
            }
        }

        // Remove details not present in the update request
        existingDetails.removeIf(existingDetail -> detailsRequests.stream()
                .noneMatch(request -> request.getId() != null && request.getId().equals(existingDetail.getId())));

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
