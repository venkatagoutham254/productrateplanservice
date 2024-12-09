package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetails;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetailsRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.exception.ReferencedWarning;
import aforo.productrateplanservie.rate_plan_flat_rate_details.UpdateRatePlanFlatRateDetailsRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RatePlanFlatRateServiceImpl implements RatePlanFlatRateService {

    private final RatePlanFlatRateRepository ratePlanFlatRateRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanFlatRateDetailsRepository ratePlanFlatRateDetailsRepository;
    private final RatePlanFlatRateMapper ratePlanFlatRateMapper;

    public RatePlanFlatRateServiceImpl(final RatePlanFlatRateRepository ratePlanFlatRateRepository,
                                       final RatePlanRepository ratePlanRepository,
                                       final RatePlanFlatRateDetailsRepository ratePlanFlatRateDetailsRepository,
                                       final RatePlanFlatRateMapper ratePlanFlatRateMapper) {
        this.ratePlanFlatRateRepository = ratePlanFlatRateRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanFlatRateDetailsRepository = ratePlanFlatRateDetailsRepository;
        this.ratePlanFlatRateMapper = ratePlanFlatRateMapper;
    }

    @Override
    public Page<RatePlanFlatRateDTO> findAll(String filter, Pageable pageable) {
        Page<RatePlanFlatRate> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (NumberFormatException numberFormatException) {
                // Keep null - no parseable input
            }
            page = ratePlanFlatRateRepository.findAllByRatePlanFlatRateId(longFilter, pageable);
        } else {
            page = ratePlanFlatRateRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public RatePlanFlatRateDTO get(Long ratePlanFlatRateId) {
        return ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .orElseThrow(() -> new NotFoundException("RatePlanFlatRate not found with id: " + ratePlanFlatRateId));
    }

    @Override
    public Long create(Long ratePlanId, CreateRatePlanFlatRateRequest request) {
        // Validate if the RatePlan exists
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
                .orElseThrow(() -> new IllegalArgumentException("RatePlan with ID " + ratePlanId + " not found"));

        // Map the request to entity
        RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateMapper.mapToEntity(request, ratePlan);

        // Save the RatePlanFlatRate entity
        RatePlanFlatRate savedEntity = ratePlanFlatRateRepository.save(ratePlanFlatRate);

        // Return the ID of the saved entity
        return savedEntity.getRatePlanFlatRateId();
    }

    @Override
    @Transactional
    public void update(Long ratePlanId, Long ratePlanFlatRateId, @Valid UpdateRatePlanFlatRateRequest updateRequest) {
        // Check if RatePlan exists
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new EntityNotFoundException("RatePlan with id " + ratePlanId + " not found");
        }

        // Retrieve the existing RatePlanFlatRate entity
        RatePlanFlatRate existingRatePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(() -> new EntityNotFoundException("RatePlanFlatRate with id " + ratePlanFlatRateId + " not found"));

        boolean isModified = updateMainRatePlanFlatRateFields(existingRatePlanFlatRate, updateRequest);

        // Handle nested details
        if (updateRequest.getRatePlanFlatRateDetails() != null) {
            isModified |= updateRatePlanFlatRateDetails(existingRatePlanFlatRate, updateRequest.getRatePlanFlatRateDetails());
        }

        // Save changes only if modifications were made
        if (isModified) {
            ratePlanFlatRateRepository.save(existingRatePlanFlatRate);
        }
    }
    private boolean updateMainRatePlanFlatRateFields(RatePlanFlatRate existingRatePlanFlatRate, UpdateRatePlanFlatRateRequest updateRequest) {
        boolean isModified = false;

        if (updateRequest.getRatePlanFlatDescription() != null &&
                !updateRequest.getRatePlanFlatDescription().trim().isEmpty() &&
                !Objects.equals(existingRatePlanFlatRate.getRatePlanFlatDescription(), updateRequest.getRatePlanFlatDescription())) {
            existingRatePlanFlatRate.setRatePlanFlatDescription(updateRequest.getRatePlanFlatDescription());
            isModified = true;
        }

        if (updateRequest.getDescription() != null &&
                !Objects.equals(existingRatePlanFlatRate.getDescription(), updateRequest.getDescription())) {
            existingRatePlanFlatRate.setDescription(updateRequest.getDescription());
            isModified = true;
        }

        if (updateRequest.getUnitType() != null &&
                !Objects.equals(existingRatePlanFlatRate.getUnitType(), updateRequest.getUnitType())) {
            existingRatePlanFlatRate.setUnitType(updateRequest.getUnitType());
            isModified = true;
        }

        if (updateRequest.getUnitMeasurement() != null &&
                !Objects.equals(existingRatePlanFlatRate.getUnitMeasurement(), updateRequest.getUnitMeasurement())) {
            existingRatePlanFlatRate.setUnitMeasurement(updateRequest.getUnitMeasurement());
            isModified = true;
        }

        if (updateRequest.getFlatRateUnitCalculation() != null &&
                !Objects.equals(existingRatePlanFlatRate.getFlatRateUnitCalculation(), updateRequest.getFlatRateUnitCalculation())) {
            existingRatePlanFlatRate.setFlatRateUnitCalculation(updateRequest.getFlatRateUnitCalculation());
            isModified = true;
        }

        if (updateRequest.getMaxLimitFrequency() != null &&
                !Objects.equals(existingRatePlanFlatRate.getMaxLimitFrequency(), updateRequest.getMaxLimitFrequency())) {
            existingRatePlanFlatRate.setMaxLimitFrequency(updateRequest.getMaxLimitFrequency());
            isModified = true;
        }

        return isModified;
    }
    private boolean updateRatePlanFlatRateDetails(RatePlanFlatRate existingRatePlanFlatRate,
                                                  List<UpdateRatePlanFlatRateDetailsRequest> detailsRequests) {
        boolean isModified = false;
        Set<RatePlanFlatRateDetails> existingDetails = existingRatePlanFlatRate.getRatePlanFlatRateDetails();

        if (detailsRequests.size() == 1 && existingDetails.size() == 1) {
            // Single detail case
            isModified = updateSingleDetail(existingDetails.iterator().next(), detailsRequests.get(0));
        } else {
            // Multiple detail case
            isModified = updateMultipleDetails(existingDetails, detailsRequests);
        }

        return isModified;
    }

    private boolean updateSingleDetail(RatePlanFlatRateDetails existingDetail, UpdateRatePlanFlatRateDetailsRequest detailRequest) {
        boolean isModified = false;

        if (detailRequest.getUnitRate() != null && !Objects.equals(detailRequest.getUnitRate(), existingDetail.getUnitRate())) {
            existingDetail.setUnitRate(detailRequest.getUnitRate());
            isModified = true;
        }

        if (detailRequest.getMaxLimit() != null && !Objects.equals(detailRequest.getMaxLimit(), existingDetail.getMaxLimit())) {
            existingDetail.setMaxLimit(detailRequest.getMaxLimit());
            isModified = true;
        }

        return isModified;
    }

    private boolean updateMultipleDetails(Set<RatePlanFlatRateDetails> existingDetails,
                                          List<UpdateRatePlanFlatRateDetailsRequest> detailsRequests) {
        boolean isModified = false;

        Map<Long, UpdateRatePlanFlatRateDetailsRequest> requestMap = detailsRequests.stream()
                .filter(request -> request.getId() != null) // Ensure ID is present
                .collect(Collectors.toMap(UpdateRatePlanFlatRateDetailsRequest::getId, request -> request));

        for (RatePlanFlatRateDetails existingDetail : existingDetails) {
            if (requestMap.containsKey(existingDetail.getId())) {
                UpdateRatePlanFlatRateDetailsRequest detailRequest = requestMap.get(existingDetail.getId());
                isModified |= updateSingleDetail(existingDetail, detailRequest);
            }
        }

        return isModified;
    }



    @Override
    @Transactional
    public void delete(Long ratePlanFlatRateId) {
        RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanFlatRate not found with id: " + ratePlanFlatRateId));

        ratePlanFlatRateRepository.delete(ratePlanFlatRate); // Details are deleted automatically
    }


    @Override
    public Page<RatePlanFlatRateDTO> findAllByRatePlanId(Long ratePlanId, Pageable pageable) {
        Page<RatePlanFlatRate> page = ratePlanFlatRateRepository.findAllByRatePlanRatePlanId(ratePlanId, pageable);
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }


    public Optional<RatePlanFlatRateDTO> findFirstByRatePlanId(Long ratePlanId) {
        Optional<RatePlan> ratePlanOpt = ratePlanRepository.findById(ratePlanId);
        if (ratePlanOpt.isEmpty()) {
            System.out.println("RatePlan not found with id: " + ratePlanId);
            return Optional.empty();
        }
        System.out.println("RatePlan found: " + ratePlanOpt.get());

        Optional<RatePlanFlatRate> ratePlanFlatRateOpt = ratePlanFlatRateRepository.findFirstByRatePlan(ratePlanOpt.get());
        if (ratePlanFlatRateOpt.isEmpty()) {
            System.out.println("RatePlanFlatRate not found for ratePlanId: " + ratePlanId);
            return Optional.empty();
        }
        return ratePlanFlatRateOpt.map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()));
    }

    @Override
    public long getRatePlanFlatRateCount() {
        return ratePlanFlatRateRepository.count();
    }
}