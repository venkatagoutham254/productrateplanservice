package aforo.productrateplanservie.rate_plan_tiered_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetails;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetailsRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.exception.ReferencedWarning;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.UpdateRatePlanTieredRateDetailsRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;


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
    public void update(Long ratePlanId, Long ratePlanTieredRateId, @Valid UpdateRatePlanTieredRateRequest updateRequest) {
        // Check if RatePlan exists
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new EntityNotFoundException("RatePlan with id " + ratePlanId + " not found");
        }

        // Retrieve the existing RatePlanTieredRate entity
        RatePlanTieredRate existingRatePlanTieredRate = ratePlanTieredRateRepository.findById(ratePlanTieredRateId)
                .orElseThrow(() -> new EntityNotFoundException("RatePlanTieredRate with id " + ratePlanTieredRateId + " not found"));

        // Map existing entity to a DTO
        RatePlanTieredRateDTO ratePlanTieredRateDTO = new RatePlanTieredRateDTO();
        ratePlanTieredRateMapper.updateRatePlanTieredRateDTO(existingRatePlanTieredRate, ratePlanTieredRateDTO);

        boolean isModified = false;

        // Update main RatePlanTieredRate fields
        if (updateRequest.getRatePlanTieredDescription() != null &&
                !updateRequest.getRatePlanTieredDescription().trim().isEmpty() &&
                !Objects.equals(ratePlanTieredRateDTO.getRatePlanTieredDescription(), updateRequest.getRatePlanTieredDescription())) {
            ratePlanTieredRateDTO.setRatePlanTieredDescription(updateRequest.getRatePlanTieredDescription());
            isModified = true;
        }

        if (updateRequest.getDescription() != null &&
                !Objects.equals(ratePlanTieredRateDTO.getDescription(), updateRequest.getDescription())) {
            ratePlanTieredRateDTO.setDescription(updateRequest.getDescription());
            isModified = true;
        }

        if (updateRequest.getUnitType() != null &&
                !Objects.equals(ratePlanTieredRateDTO.getUnitType(), updateRequest.getUnitType())) {
            ratePlanTieredRateDTO.setUnitType(updateRequest.getUnitType());
            isModified = true;
        }

        if (updateRequest.getUnitMeasurement() != null &&
                !Objects.equals(ratePlanTieredRateDTO.getUnitMeasurement(), updateRequest.getUnitMeasurement())) {
            ratePlanTieredRateDTO.setUnitMeasurement(updateRequest.getUnitMeasurement());
            isModified = true;
        }

        if (updateRequest.getUnitCalculation() != null &&
                !Objects.equals(ratePlanTieredRateDTO.getUnitCalculation(), updateRequest.getUnitCalculation())) {
            ratePlanTieredRateDTO.setUnitCalculation(updateRequest.getUnitCalculation());
            isModified = true;
        }

        // Update nested RatePlanTieredRateDetails
        if (updateRequest.getRatePlanTieredRateDetails() != null) {
            isModified |= updateRatePlanTieredRateDetails(existingRatePlanTieredRate, updateRequest.getRatePlanTieredRateDetails());
        }

        // Map updated DTO back to the entity and save only if modifications were made
        if (isModified) {
            ratePlanTieredRateMapper.updateRatePlanTieredRate(ratePlanTieredRateDTO, existingRatePlanTieredRate, ratePlanRepository);
            ratePlanTieredRateRepository.save(existingRatePlanTieredRate);
        }
    }

    private boolean updateRatePlanTieredRateDetails(RatePlanTieredRate existingRatePlanTieredRate,
                                                    Set<UpdateRatePlanTieredRateDetailsRequest> detailsRequests) {
        boolean isModified = false;

        Set<RatePlanTieredRateDetails> existingDetails = existingRatePlanTieredRate.getRatePlanTieredRateDetails();

        for (UpdateRatePlanTieredRateDetailsRequest detailRequest : detailsRequests) {
            RatePlanTieredRateDetails detail = existingDetails.stream()
                    .filter(d -> d.getTierNumber().equals(detailRequest.getTierNumber()))
                    .findFirst()
                    .orElse(null);

            if (detail != null) {
                // Update existing details
                if (detailRequest.getTierStart() != null && !Objects.equals(detail.getTierStart(), detailRequest.getTierStart())) {
                    detail.setTierStart(detailRequest.getTierStart());
                    isModified = true;
                }
                if (detailRequest.getTierRate() != null && !Objects.equals(detail.getTierRate(), detailRequest.getTierRate())) {
                    detail.setTierRate(detailRequest.getTierRate());
                    isModified = true;
                }
                if (detailRequest.getTierEnd() != null && !Objects.equals(detail.getTierEnd(), detailRequest.getTierEnd())) {
                    detail.setTierEnd(detailRequest.getTierEnd());
                    isModified = true;
                }
            } else {
                // Add new details if not found
                RatePlanTieredRateDetails newDetail = new RatePlanTieredRateDetails();
                newDetail.setTierNumber(detailRequest.getTierNumber());
                newDetail.setTierStart(detailRequest.getTierStart());
                newDetail.setTierRate(detailRequest.getTierRate());
                newDetail.setTierEnd(detailRequest.getTierEnd());
                newDetail.setRatePlanTieredRate(existingRatePlanTieredRate);
                existingDetails.add(newDetail);
                isModified = true;
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

}
