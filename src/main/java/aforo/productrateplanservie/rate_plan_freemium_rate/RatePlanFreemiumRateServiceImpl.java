package aforo.productrateplanservie.rate_plan_freemium_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetails;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetailsRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.UpdateRatePlanFreemiumRateDetailsRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RatePlanFreemiumRateServiceImpl implements RatePlanFreemiumRateService {

    private final RatePlanFreemiumRateRepository ratePlanFreemiumRateRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanFreemiumRateMapper ratePlanFreemiumRateMapper;
    private final RatePlanFreemiumRateDetailsRepository ratePlanFreemiumRateDetailsRepository;

    public RatePlanFreemiumRateServiceImpl(final RatePlanFreemiumRateRepository ratePlanFreemiumRateRepository, final RatePlanRepository ratePlanRepository, final RatePlanFreemiumRateMapper ratePlanFreemiumRateMapper, final RatePlanFreemiumRateDetailsRepository ratePlanFreemiumRateDetailsRepository) {
        this.ratePlanFreemiumRateRepository = ratePlanFreemiumRateRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanFreemiumRateMapper = ratePlanFreemiumRateMapper;
        this.ratePlanFreemiumRateDetailsRepository = ratePlanFreemiumRateDetailsRepository;
    }

    @Override
    public Page<RatePlanFreemiumRateDTO> findAll(final String filter, final Pageable pageable) {
        Page<RatePlanFreemiumRate> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = ratePlanFreemiumRateRepository.findAllByRatePlanFreemiumRateId(longFilter, pageable);
        } else {
            page = ratePlanFreemiumRateRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent().stream().map(ratePlanFreemiumRate -> ratePlanFreemiumRateMapper.updateRatePlanFreemiumRateDTO(ratePlanFreemiumRate, new RatePlanFreemiumRateDTO())).toList(), pageable, page.getTotalElements());
    }

    @Override
    public RatePlanFreemiumRateDTO get(final Long ratePlanFreemiumRateId) {
        return ratePlanFreemiumRateRepository.findById(ratePlanFreemiumRateId).map(ratePlanFreemiumRate -> ratePlanFreemiumRateMapper.updateRatePlanFreemiumRateDTO(ratePlanFreemiumRate, new RatePlanFreemiumRateDTO())).orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(Long ratePlanId, @Valid CreateRatePlanFreemiumRateRequest ratePlanFreemiumRateCreateRequestDTO) {
        RatePlan ratePlan = new RatePlan(ratePlanId);
        RatePlanFreemiumRate ratePlanFreemiumRate = ratePlanFreemiumRateMapper.mapToEntity(ratePlanFreemiumRateCreateRequestDTO, ratePlan);
        ratePlanFreemiumRateRepository.save(ratePlanFreemiumRate);
        return ratePlanFreemiumRate.getRatePlanFreemiumRateId();
    }

    @Override
    @Transactional
    public void update(Long ratePlanId, Long ratePlanFreemiumRateId, @Valid UpdateRatePlanFreemiumRateRequest updateDTO) {
        // Check if RatePlan exists
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new EntityNotFoundException("RatePlan with id " + ratePlanId + " not found");
        }

        // Retrieve the existing RatePlanFreemiumRate entity
        RatePlanFreemiumRate existingRatePlanFreemiumRate = ratePlanFreemiumRateRepository.findById(ratePlanFreemiumRateId)
                .orElseThrow(() -> new EntityNotFoundException("RatePlanFreemiumRate with id " + ratePlanFreemiumRateId + " not found"));

        boolean isModified = updateMainFreemiumRateFields(existingRatePlanFreemiumRate, updateDTO);

        // Handle nested details
        if (updateDTO.getRatePlanFreemiumRateDetailsDTO() != null) {
            isModified |= updateFreemiumRateDetails(existingRatePlanFreemiumRate, updateDTO.getRatePlanFreemiumRateDetailsDTO());
        }

        // Save changes only if modifications were made
        if (isModified) {
            ratePlanFreemiumRateRepository.save(existingRatePlanFreemiumRate);
        }
    }
    private boolean updateMainFreemiumRateFields(RatePlanFreemiumRate existingRatePlanFreemiumRate, UpdateRatePlanFreemiumRateRequest updateDTO) {
        boolean isModified = false;

        if (updateDTO.getFreemiumRateDescription() != null &&
                !updateDTO.getFreemiumRateDescription().trim().isEmpty() &&
                !Objects.equals(existingRatePlanFreemiumRate.getFreemiumRateDescription(), updateDTO.getFreemiumRateDescription())) {
            existingRatePlanFreemiumRate.setFreemiumRateDescription(updateDTO.getFreemiumRateDescription());
            isModified = true;
        }

        if (updateDTO.getDescription() != null &&
                !Objects.equals(existingRatePlanFreemiumRate.getDescription(), updateDTO.getDescription())) {
            existingRatePlanFreemiumRate.setDescription(updateDTO.getDescription());
            isModified = true;
        }

        if (updateDTO.getUnitType() != null &&
                !Objects.equals(existingRatePlanFreemiumRate.getUnitType(), updateDTO.getUnitType())) {
            existingRatePlanFreemiumRate.setUnitType(updateDTO.getUnitType());
            isModified = true;
        }

        if (updateDTO.getUnitMeasurement() != null &&
                !Objects.equals(existingRatePlanFreemiumRate.getUnitMeasurement(), updateDTO.getUnitMeasurement())) {
            existingRatePlanFreemiumRate.setUnitMeasurement(updateDTO.getUnitMeasurement());
            isModified = true;
        }

        if (updateDTO.getUnitBillingFrequency() != null &&
                !Objects.equals(existingRatePlanFreemiumRate.getUnitBillingFrequency(), updateDTO.getUnitBillingFrequency())) {
            existingRatePlanFreemiumRate.setUnitBillingFrequency(updateDTO.getUnitBillingFrequency());
            isModified = true;
        }

        if (updateDTO.getUnitFreePriceFixedFrequency() != null &&
                !Objects.equals(existingRatePlanFreemiumRate.getUnitFreePriceFixedFrequency(), updateDTO.getUnitFreePriceFixedFrequency())) {
            existingRatePlanFreemiumRate.setUnitFreePriceFixedFrequency(updateDTO.getUnitFreePriceFixedFrequency());
            isModified = true;
        }

        return isModified;
    }
    private boolean updateFreemiumRateDetails(RatePlanFreemiumRate existingRatePlanFreemiumRate,
                                              Set<UpdateRatePlanFreemiumRateDetailsRequest> detailsDTO) {
        boolean isModified = false;
        Set<RatePlanFreemiumRateDetails> existingDetails = existingRatePlanFreemiumRate.getRatePlanFreemiumRateDetails();

        if (detailsDTO.size() == 1 && existingDetails.size() == 1) {
            // Handle single detail case
            isModified = updateSingleFreemiumRateDetail(existingDetails.iterator().next(), detailsDTO.iterator().next());
        } else {
            // Handle multiple detail cases
            isModified = updateMultipleFreemiumRateDetails(existingDetails, detailsDTO);
        }

        return isModified;
    }

    private boolean updateSingleFreemiumRateDetail(RatePlanFreemiumRateDetails existingDetail, UpdateRatePlanFreemiumRateDetailsRequest detailDTO) {
        boolean isModified = false;

        if (detailDTO.getFreemiumMaxUnitQuantity() != null &&
                !Objects.equals(existingDetail.getFreemiumMaxUnitQuantity(), detailDTO.getFreemiumMaxUnitQuantity())) {
            existingDetail.setFreemiumMaxUnitQuantity(detailDTO.getFreemiumMaxUnitQuantity());
            isModified = true;
        }

        return isModified;
    }

    private boolean updateMultipleFreemiumRateDetails(Set<RatePlanFreemiumRateDetails> existingDetails,
                                                      Set<UpdateRatePlanFreemiumRateDetailsRequest> detailsDTO) {
        boolean isModified = false;

        Map<Long, UpdateRatePlanFreemiumRateDetailsRequest> requestMap = detailsDTO.stream()
                .filter(request -> request.getId() != null) // Ensure ID is present
                .collect(Collectors.toMap(UpdateRatePlanFreemiumRateDetailsRequest::getId, request -> request));

        for (RatePlanFreemiumRateDetails existingDetail : existingDetails) {
            if (requestMap.containsKey(existingDetail.getId())) {
                UpdateRatePlanFreemiumRateDetailsRequest detailDTO = requestMap.get(existingDetail.getId());
                isModified |= updateSingleFreemiumRateDetail(existingDetail, detailDTO);
            }
        }

        return isModified;
    }


    @Override
    public void delete(Long ratePlanFreemiumRateId) {
        // Check if the entity exists before attempting to delete
        if (!ratePlanFreemiumRateRepository.existsById(ratePlanFreemiumRateId)) {
            throw new NotFoundException("RatePlanFreemiumRate not found with ID: " + ratePlanFreemiumRateId);
        }

        ratePlanFreemiumRateRepository.deleteById(ratePlanFreemiumRateId);
    }
}
