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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void update(Long ratePlanId, Long ratePlanTieredRateId, UpdateRatePlanTieredRateRequest updateRequest) {
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new EntityNotFoundException("RatePlan with id " + ratePlanId + " not found");
        }

        RatePlanTieredRate existingRatePlanTieredRate = ratePlanTieredRateRepository.findById(ratePlanTieredRateId)
                .orElseThrow(() -> new EntityNotFoundException("RatePlanTieredRate with id " + ratePlanTieredRateId + " not found"));

        boolean isModified = false;

        if (updateRequest.getRatePlanTieredDescription() != null) {
            existingRatePlanTieredRate.setRatePlanTieredDescription(updateRequest.getRatePlanTieredDescription());
            isModified = true;
        }
        if (updateRequest.getDescription() != null) {
            existingRatePlanTieredRate.setDescription(updateRequest.getDescription());
            isModified = true;
        }
        if (updateRequest.getUnitType() != null) {
            existingRatePlanTieredRate.setUnitType(updateRequest.getUnitType());
            isModified = true;
        }
        if (updateRequest.getUnitMeasurement() != null) {
            existingRatePlanTieredRate.setUnitMeasurement(updateRequest.getUnitMeasurement());
            isModified = true;
        }
        if (updateRequest.getUnitCalculation() != null) {
            existingRatePlanTieredRate.setUnitCalculation(updateRequest.getUnitCalculation());
            isModified = true;
        }

        if (updateRequest.getRatePlanTieredRateDetails() != null) {
            isModified |= updateRatePlanTieredRateDetails(existingRatePlanTieredRate, updateRequest.getRatePlanTieredRateDetails());
        }

        if (isModified) {
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
                    .orElseThrow(() -> new EntityNotFoundException("RatePlanTieredRateDetails not found for ID: " + detailRequest.getTierNumber()));

            if (detailRequest.getTierStart() != null && !detailRequest.getTierStart().equals(detail.getTierStart())) {
                detail.setTierStart(detailRequest.getTierStart());
                isModified = true;
            }
            if (detailRequest.getTierRate() != null && !detailRequest.getTierRate().equals(detail.getTierRate())) {
                detail.setTierRate(detailRequest.getTierRate());
                isModified = true;
            }
            if (detailRequest.getTierEnd() != null && !detailRequest.getTierEnd().equals(detail.getTierEnd())) {
                detail.setTierEnd(detailRequest.getTierEnd());
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
