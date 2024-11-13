package aforo.productrateplanservie.rate_plan_freemium_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetails;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetailsRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetailsUpdateRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Long create(Long ratePlanId, RatePlanFreemiumRateCreateRequestDTO ratePlanFreemiumRateCreateRequestDTO) {
        RatePlan ratePlan = new RatePlan(ratePlanId);
        RatePlanFreemiumRate ratePlanFreemiumRate = ratePlanFreemiumRateMapper.mapToEntity(ratePlanFreemiumRateCreateRequestDTO, ratePlan);
        ratePlanFreemiumRateRepository.save(ratePlanFreemiumRate);
        return ratePlanFreemiumRate.getRatePlanFreemiumRateId();
    }
    @Override
    @Transactional
    public void update(Long ratePlanId, Long ratePlanFreemiumRateId, RatePlanFreemiumRateUpdateRequestDTO updateDTO) {
        // Validate the existence of the RatePlan before proceeding
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new EntityNotFoundException("RatePlan with id " + ratePlanId + " not found");
        }

        // Fetch the existing RatePlanFreemiumRate entity
        RatePlanFreemiumRate existingRatePlanFreemiumRate = ratePlanFreemiumRateRepository.findById(ratePlanFreemiumRateId)
                .orElseThrow(() -> new EntityNotFoundException("RatePlanFreemiumRate with id " + ratePlanFreemiumRateId + " not found"));

        // Update fields only if they are not null
        if (updateDTO.getFreemiumRateDescription() != null) {
            existingRatePlanFreemiumRate.setFreemiumRateDescription(updateDTO.getFreemiumRateDescription());
        }
        if (updateDTO.getDescription() != null) {
            existingRatePlanFreemiumRate.setDescription(updateDTO.getDescription());
        }
        if (updateDTO.getUnitType() != null) {
            existingRatePlanFreemiumRate.setUnitType(updateDTO.getUnitType());
        }
        if (updateDTO.getUnitMeasurement() != null) {
            existingRatePlanFreemiumRate.setUnitMeasurement(updateDTO.getUnitMeasurement());
        }
        if (updateDTO.getUnitBillingFrequency() != null) {
            existingRatePlanFreemiumRate.setUnitBillingFrequency(updateDTO.getUnitBillingFrequency());
        }
        if (updateDTO.getUnitFreePriceFixedFrequency() != null) {
            existingRatePlanFreemiumRate.setUnitFreePriceFixedFrequency(updateDTO.getUnitFreePriceFixedFrequency());
        }

        // Update nested RatePlanFreemiumRateDetails if provided
        if (updateDTO.getRatePlanFreemiumRateDetailsDTO() != null) {
            updateRatePlanFreemiumRateDetails(existingRatePlanFreemiumRate, updateDTO.getRatePlanFreemiumRateDetailsDTO());
        }

        // Save the updated entity
        ratePlanFreemiumRateRepository.save(existingRatePlanFreemiumRate);
    }

    // Helper method to handle partial update of RatePlanFreemiumRateDetails
    private void updateRatePlanFreemiumRateDetails(RatePlanFreemiumRate ratePlanFreemiumRate, Set<RatePlanFreemiumRateDetailsUpdateRequestDTO> detailsDTO) {
        Set<RatePlanFreemiumRateDetails> existingDetails = ratePlanFreemiumRate.getRatePlanFreemiumRateDetails();

        for (RatePlanFreemiumRateDetailsUpdateRequestDTO detailDTO : detailsDTO) {
            RatePlanFreemiumRateDetails detail = existingDetails.stream()
                    .filter(d -> d.getId().equals(detailDTO.getId()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("RatePlanFreemiumRateDetails not found for ID: " + detailDTO.getId()));

            if (detailDTO.getFreemiumMaxUnitQuantity() != null) {
                detail.setFreemiumMaxUnitQuantity(detailDTO.getFreemiumMaxUnitQuantity());
            }
        }
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



//    @Override
//    public ReferencedWarning getReferencedWarning(final Long ratePlanFreemiumRateId) {
//        final ReferencedWarning referencedWarning = new ReferencedWarning();
//        final RatePlanFreemiumRate ratePlanFreemiumRate = ratePlanFreemiumRateRepository.findById(ratePlanFreemiumRateId)
//                .orElseThrow(NotFoundException::new);
//        final RatePlanFreemiumRateDetails ratePlanFreemiumRateRatePlanFreemiumRateDetails = ratePlanFreemiumRateDetailsRepository.findFirstByRatePlanFreemiumRate(ratePlanFreemiumRate);
//        if (ratePlanFreemiumRateRatePlanFreemiumRateDetails != null) {
//            referencedWarning.setKey("ratePlanFreemiumRate.ratePlanFreemiumRateDetails.ratePlanFreemiumRate.referenced");
//            referencedWarning.addParam((ratePlanFreemiumRateRatePlanFreemiumRateDetails).getId());
//            return referencedWarning;
//        }
//        return null;
//    }


