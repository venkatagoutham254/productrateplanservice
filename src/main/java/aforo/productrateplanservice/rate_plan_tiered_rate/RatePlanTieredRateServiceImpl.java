package aforo.productrateplanservice.rate_plan_tiered_rate;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aforo.productrateplanservice.exception.NotFoundException;
import aforo.productrateplanservice.exception.ReferencedWarning;
import aforo.productrateplanservice.rate_plan.RatePlan;
import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import aforo.productrateplanservice.rate_plan_tiered_rate_details.RatePlanTieredRateDetails;
import aforo.productrateplanservice.rate_plan_tiered_rate_details.RatePlanTieredRateDetailsDTO;
import aforo.productrateplanservice.rate_plan_tiered_rate_details.RatePlanTieredRateDetailsRepository;
import aforo.productrateplanservice.rate_plan_tiered_rate_details.UpdateRatePlanTieredRateDetailsRequest;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RatePlanTieredRateServiceImpl implements RatePlanTieredRateService {

    private final RatePlanTieredRateRepository ratePlanTieredRateRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanTieredRateMapper ratePlanTieredRateMapper;
    private final RatePlanTieredRateDetailsRepository ratePlanTieredRateDetailsRepository;

    public RatePlanTieredRateServiceImpl(final RatePlanTieredRateRepository ratePlanTieredRateRepository, final RatePlanRepository ratePlanRepository, final RatePlanTieredRateMapper ratePlanTieredRateMapper, final RatePlanTieredRateDetailsRepository ratePlanTieredRateDetailsRepository) {
        this.ratePlanTieredRateRepository = ratePlanTieredRateRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanTieredRateMapper = ratePlanTieredRateMapper;
        this.ratePlanTieredRateDetailsRepository = ratePlanTieredRateDetailsRepository;
    }

    @Override
    public Page<RatePlanTieredRateDTO> findAll(final String filter, final Pageable pageable) {
        Page<RatePlanTieredRate> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = ratePlanTieredRateRepository.findAllByRatePlanTieredRateId(longFilter, pageable);
        } else {
            page = ratePlanTieredRateRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent().stream().map(ratePlanTieredRate -> ratePlanTieredRateMapper.updateRatePlanTieredRateDTO(ratePlanTieredRate, new RatePlanTieredRateDTO())).toList(), pageable, page.getTotalElements());
    }

    @Override
    public RatePlanTieredRateDTO get(final Long ratePlanTieredRateId) {
        return ratePlanTieredRateRepository.findById(ratePlanTieredRateId).map(ratePlanTieredRate -> ratePlanTieredRateMapper.updateRatePlanTieredRateDTO(ratePlanTieredRate, new RatePlanTieredRateDTO())).orElseThrow(NotFoundException::new);
    }
    @Override
    @Transactional
    public Long create(Long ratePlanId, CreateRatePlanTieredRateRequest createRequest) {
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
                .orElseThrow(() -> new NotFoundException("RatePlan not found with id: " + ratePlanId));

        // Map the request to RatePlanTieredRate entity
        RatePlanTieredRate ratePlanTieredRate = ratePlanTieredRateMapper.mapToRatePlanTieredRate(createRequest);
        ratePlanTieredRate.setRatePlan(ratePlan);

        RatePlanTieredRate savedRatePlanTieredRate = ratePlanTieredRateRepository.save(ratePlanTieredRate);

        // Map and save each RatePlanTieredRateDetails entity
        createRequest.getRatePlanTieredRateDetails().forEach(detailsRequest -> {
            RatePlanTieredRateDetails details = ratePlanTieredRateMapper.mapToRatePlanTieredRateDetails(detailsRequest);
            details.setRatePlanTieredRate(savedRatePlanTieredRate);
            ratePlanTieredRateDetailsRepository.save(details);
        });

        return savedRatePlanTieredRate.getRatePlanTieredRateId();
    }
    @Override
    @Transactional
    public void update(Long ratePlanId, Long ratePlanTieredRateId, UpdateRatePlanTieredRateRequest updateRequest) {
        // Check if RatePlan exists
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new NotFoundException("RatePlan with id " + ratePlanId + " not found");
        }

        // Retrieve the existing RatePlanTieredRate entity
        RatePlanTieredRate existingRatePlanTieredRate = ratePlanTieredRateRepository.findById(ratePlanTieredRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanTieredRate with id " + ratePlanTieredRateId + " not found"));

        boolean isModified = updateMainTieredRateFields(existingRatePlanTieredRate, updateRequest);

        // Handle nested details
        if (updateRequest.getRatePlanTieredRateDetails() != null) {
            isModified |= updateTieredRateDetails(existingRatePlanTieredRate, updateRequest.getRatePlanTieredRateDetails());
        }

        // Save changes only if modifications were made
        if (isModified) {
            ratePlanTieredRateRepository.save(existingRatePlanTieredRate);
        }
    }

    private boolean updateMainTieredRateFields(RatePlanTieredRate existingRatePlanTieredRate, UpdateRatePlanTieredRateRequest updateRequest) {
        boolean isModified = false;

        if (updateRequest.getRatePlanTieredDescription() != null &&
                !Objects.equals(existingRatePlanTieredRate.getRatePlanTieredDescription(), updateRequest.getRatePlanTieredDescription())) {
            existingRatePlanTieredRate.setRatePlanTieredDescription(updateRequest.getRatePlanTieredDescription());
            isModified = true;
        }

        if (updateRequest.getDescription() != null &&
                !Objects.equals(existingRatePlanTieredRate.getDescription(), updateRequest.getDescription())) {
            existingRatePlanTieredRate.setDescription(updateRequest.getDescription());
            isModified = true;
        }

        if (updateRequest.getUnitType() != null &&
                !Objects.equals(existingRatePlanTieredRate.getUnitType(), updateRequest.getUnitType())) {
            existingRatePlanTieredRate.setUnitType(updateRequest.getUnitType());
            isModified = true;
        }

        if (updateRequest.getUnitMeasurement() != null &&
                !Objects.equals(existingRatePlanTieredRate.getUnitMeasurement(), updateRequest.getUnitMeasurement())) {
            existingRatePlanTieredRate.setUnitMeasurement(updateRequest.getUnitMeasurement());
            isModified = true;
        }

        if (updateRequest.getUnitCalculation() != null &&
                !Objects.equals(existingRatePlanTieredRate.getUnitCalculation(), updateRequest.getUnitCalculation())) {
            existingRatePlanTieredRate.setUnitCalculation(updateRequest.getUnitCalculation());
            isModified = true;
        }

        return isModified;
    }

    private boolean updateTieredRateDetails(RatePlanTieredRate existingRatePlanTieredRate,
                                            Set<UpdateRatePlanTieredRateDetailsRequest> detailsRequests) {
        boolean isModified = false;
        Set<RatePlanTieredRateDetails> existingDetails = existingRatePlanTieredRate.getRatePlanTieredRateDetails();

        if (detailsRequests.size() == 1 && existingDetails.size() == 1) {
            // Handle single detail case
            isModified = updateSingleTieredDetail(existingDetails.iterator().next(), detailsRequests.iterator().next());
        } else {
            // Handle multiple detail cases
            isModified = updateMultipleTieredDetails(existingDetails, detailsRequests);
        }

        return isModified;
    }

    private boolean updateSingleTieredDetail(RatePlanTieredRateDetails existingDetail, UpdateRatePlanTieredRateDetailsRequest detailRequest) {
        boolean isModified = false;

        if (detailRequest.getTierStart() != null &&
                !Objects.equals(existingDetail.getTierStart(), detailRequest.getTierStart())) {
            existingDetail.setTierStart(detailRequest.getTierStart());
            isModified = true;
        }

        if (detailRequest.getTierRate() != null &&
                !Objects.equals(existingDetail.getTierRate(), detailRequest.getTierRate())) {
            existingDetail.setTierRate(detailRequest.getTierRate());
            isModified = true;
        }

        if (detailRequest.getTierEnd() != null &&
                !Objects.equals(existingDetail.getTierEnd(), detailRequest.getTierEnd())) {
            existingDetail.setTierEnd(detailRequest.getTierEnd());
            isModified = true;
        }

        return isModified;
    }

    private boolean updateMultipleTieredDetails(Set<RatePlanTieredRateDetails> existingDetails,
                                                Set<UpdateRatePlanTieredRateDetailsRequest> detailsRequests) {
        boolean isModified = false;

        Map<Long, UpdateRatePlanTieredRateDetailsRequest> requestMap = detailsRequests.stream()
                .filter(request -> request.getTierNumber() != null) // Ensure ID is present
                .collect(Collectors.toMap(UpdateRatePlanTieredRateDetailsRequest::getTierNumber, request -> request));

        for (RatePlanTieredRateDetails existingDetail : existingDetails) {
            if (requestMap.containsKey(existingDetail.getTierNumber())) {
                UpdateRatePlanTieredRateDetailsRequest detailRequest = requestMap.get(existingDetail.getTierNumber());
                isModified |= updateSingleTieredDetail(existingDetail, detailRequest);
            }
        }

        return isModified;
    }

    @Transactional
    @Override
    public void delete(final Long ratePlanTieredRateId) {
        // Fetch the RatePlanTieredRate
        RatePlanTieredRate ratePlanTieredRate = ratePlanTieredRateRepository.findById(ratePlanTieredRateId).orElseThrow(() -> new NotFoundException("RatePlanTieredRate not found with id: " + ratePlanTieredRateId));
        ratePlanTieredRateRepository.delete(ratePlanTieredRate);
    }


//    @Override
//    public ReferencedWarning getReferencedWarning(final Long ratePlanTieredRateId) {
//        final ReferencedWarning referencedWarning = new ReferencedWarning();
//        final RatePlanTieredRate ratePlanTieredRate = ratePlanTieredRateRepository.findById(ratePlanTieredRateId).orElseThrow(NotFoundException::new);
//        final RatePlanTieredRateDetails ratePlanTieredRateRatePlanTieredRateDetails = ratePlanTieredRateDetailsRepository.findFirstByRatePlanTieredRate(ratePlanTieredRate);
//        if (ratePlanTieredRateRatePlanTieredRateDetails != null) {
//            referencedWarning.setKey("ratePlanTieredRate.ratePlanTieredRateDetails.ratePlanTieredRate.referenced");
//            referencedWarning.addParam(ratePlanTieredRateRatePlanTieredRateDetails.getTierNumber());
//            return referencedWarning;
//        }
//        return null;
//    }

@Override
public long getRatePlanTieredRateCount() {
    return ratePlanTieredRateRepository.count();
    }
}
