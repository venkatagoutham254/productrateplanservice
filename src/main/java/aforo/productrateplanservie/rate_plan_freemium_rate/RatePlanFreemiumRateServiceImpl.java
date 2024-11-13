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

import java.util.Objects;
import java.util.Set;

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

        // Initialize a DTO to store updates
        RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO = ratePlanFreemiumRateMapper.updateRatePlanFreemiumRateDTO(existingRatePlanFreemiumRate, new RatePlanFreemiumRateDTO());
        boolean isModified = false;

        // Check and update fields only if there is a change
        if (updateDTO.getFreemiumRateDescription() != null &&
                !updateDTO.getFreemiumRateDescription().trim().isEmpty() &&
                !Objects.equals(existingRatePlanFreemiumRate.getFreemiumRateDescription(), updateDTO.getFreemiumRateDescription())) {
            ratePlanFreemiumRateDTO.setFreemiumRateDescription(updateDTO.getFreemiumRateDescription());
            isModified = true;
        }

        if (updateDTO.getDescription() != null &&
                !Objects.equals(existingRatePlanFreemiumRate.getDescription(), updateDTO.getDescription())) {
            ratePlanFreemiumRateDTO.setDescription(updateDTO.getDescription());
            isModified = true;
        }

        if (updateDTO.getUnitType() != null &&
                !Objects.equals(existingRatePlanFreemiumRate.getUnitType(), updateDTO.getUnitType())) {
            ratePlanFreemiumRateDTO.setUnitType(updateDTO.getUnitType());
            isModified = true;
        }

        if (updateDTO.getUnitMeasurement() != null &&
                !Objects.equals(existingRatePlanFreemiumRate.getUnitMeasurement(), updateDTO.getUnitMeasurement())) {
            ratePlanFreemiumRateDTO.setUnitMeasurement(updateDTO.getUnitMeasurement());
            isModified = true;
        }

        if (updateDTO.getUnitBillingFrequency() != null &&
                !Objects.equals(existingRatePlanFreemiumRate.getUnitBillingFrequency(), updateDTO.getUnitBillingFrequency())) {
            ratePlanFreemiumRateDTO.setUnitBillingFrequency(updateDTO.getUnitBillingFrequency());
            isModified = true;
        }

        if (updateDTO.getUnitFreePriceFixedFrequency() != null &&
                !Objects.equals(existingRatePlanFreemiumRate.getUnitFreePriceFixedFrequency(), updateDTO.getUnitFreePriceFixedFrequency())) {
            ratePlanFreemiumRateDTO.setUnitFreePriceFixedFrequency(updateDTO.getUnitFreePriceFixedFrequency());
            isModified = true;
        }

        // Check and update nested RatePlanFreemiumRateDetails if provided
        if (updateDTO.getRatePlanFreemiumRateDetailsDTO() != null) {
            isModified |= updateRatePlanFreemiumRateDetails(existingRatePlanFreemiumRate, updateDTO.getRatePlanFreemiumRateDetailsDTO());
        }

        // Apply updates to the entity only if modifications were made
        if (isModified) {
            ratePlanFreemiumRateMapper.updateRatePlanFreemiumRate(ratePlanFreemiumRateDTO, existingRatePlanFreemiumRate);
            ratePlanFreemiumRateRepository.save(existingRatePlanFreemiumRate);
        }
    }

    // Helper method to handle partial update of RatePlanFreemiumRateDetails
    private boolean updateRatePlanFreemiumRateDetails(RatePlanFreemiumRate ratePlanFreemiumRate, Set<UpdateRatePlanFreemiumRateDetailsRequest> detailsDTO) {
        boolean isModified = false;
        Set<RatePlanFreemiumRateDetails> existingDetails = ratePlanFreemiumRate.getRatePlanFreemiumRateDetails();

        for (UpdateRatePlanFreemiumRateDetailsRequest detailDTO : detailsDTO) {
            RatePlanFreemiumRateDetails detail = existingDetails.stream()
                    .filter(d -> d.getId().equals(detailDTO.getId()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("RatePlanFreemiumRateDetails not found for ID: " + detailDTO.getId()));

            if (detailDTO.getFreemiumMaxUnitQuantity() != null &&
                    !Objects.equals(detail.getFreemiumMaxUnitQuantity(), detailDTO.getFreemiumMaxUnitQuantity())) {
                detail.setFreemiumMaxUnitQuantity(detailDTO.getFreemiumMaxUnitQuantity());
                isModified = true;
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
