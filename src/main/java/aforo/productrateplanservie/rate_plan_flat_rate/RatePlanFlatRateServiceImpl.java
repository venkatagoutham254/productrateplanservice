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
import java.util.Optional;
import java.util.Set;

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
    @Transactional
    public Long create(Long ratePlanId, CreateRatePlanFlatRateRequest createRatePlanFlatRateRequest) {
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
                .orElseThrow(() -> new NotFoundException("RatePlan not found with id: " + ratePlanId));

        // Map the request to RatePlanFlatRate entity
        RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateMapper.mapToRatePlanFlatRate(createRatePlanFlatRateRequest);
        ratePlanFlatRate.setRatePlan(ratePlan);

        RatePlanFlatRate savedRatePlanFlatRate = ratePlanFlatRateRepository.save(ratePlanFlatRate);

        // Map and save each RatePlanFlatRateDetails entity
        createRatePlanFlatRateRequest.getRatePlanFlatRateDetails().forEach(detailsRequest -> {
            RatePlanFlatRateDetails details = ratePlanFlatRateMapper.mapToRatePlanFlatRateDetails(detailsRequest);
            details.setRatePlanFlatRate(savedRatePlanFlatRate);
            ratePlanFlatRateDetailsRepository.save(details);
        });

        return savedRatePlanFlatRate.getRatePlanFlatRateId();
    }



    @Override
    @Transactional
    public void update(Long ratePlanId, Long ratePlanFlatRateId, @Valid UpdateRatePlanFlatRateRequest updateRequest) {
        // Check if RatePlan exists
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new EntityNotFoundException("RatePlan with id " + ratePlanId + " not found");
        }

        // Retrieve existing RatePlanFlatRate entity
        RatePlanFlatRate existingRatePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(() -> new EntityNotFoundException("RatePlanFlatRate with id " + ratePlanFlatRateId + " not found"));

        boolean isModified = false;

        // Update main RatePlanFlatRate fields if provided
        if (updateRequest.getRatePlanFlatDescription() != null) {
            existingRatePlanFlatRate.setRatePlanFlatDescription(updateRequest.getRatePlanFlatDescription());
            isModified = true;
        }
        if (updateRequest.getDescription() != null) {
            existingRatePlanFlatRate.setDescription(updateRequest.getDescription());
            isModified = true;
        }
        if (updateRequest.getUnitType() != null) {
            existingRatePlanFlatRate.setUnitType(updateRequest.getUnitType());
            isModified = true;
        }
        if (updateRequest.getUnitMeasurement() != null) {
            existingRatePlanFlatRate.setUnitMeasurement(updateRequest.getUnitMeasurement());
            isModified = true;
        }
        if (updateRequest.getFlatRateUnitCalculation() != null) {
            existingRatePlanFlatRate.setFlatRateUnitCalculation(updateRequest.getFlatRateUnitCalculation());
            isModified = true;
        }
        if (updateRequest.getMaxLimitFrequency() != null) {
            existingRatePlanFlatRate.setMaxLimitFrequency(updateRequest.getMaxLimitFrequency());
            isModified = true;
        }

        // Handle updating of nested RatePlanFlatRateDetails if provided
        if (updateRequest.getRatePlanFlatRateDetails() != null) {
            isModified |= updateRatePlanFlatRateDetails(existingRatePlanFlatRate, updateRequest.getRatePlanFlatRateDetails());
        }

        // Save changes if any modifications were made
        if (isModified) {
            ratePlanFlatRateRepository.save(existingRatePlanFlatRate);
        }
    }

    private boolean updateRatePlanFlatRateDetails(RatePlanFlatRate existingRatePlanFlatRate,
                                                  List<UpdateRatePlanFlatRateDetailsRequest> detailsRequests) {
        boolean isModified = false;
        Set<RatePlanFlatRateDetails> existingDetails = existingRatePlanFlatRate.getRatePlanFlatRateDetails();

        for (UpdateRatePlanFlatRateDetailsRequest detailRequest : detailsRequests) {
            RatePlanFlatRateDetails detail = existingDetails.stream()
                    .filter(d -> d.getId().equals(detailRequest.getId()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("RatePlanFlatRateDetails not found for ID: " + detailRequest.getId()));

            if (detailRequest.getUnitRate() != null && !detailRequest.getUnitRate().equals(detail.getUnitRate())) {
                detail.setUnitRate(detailRequest.getUnitRate());
                isModified = true;
            }
            if (detailRequest.getMaxLimit() != null && !detailRequest.getMaxLimit().equals(detail.getMaxLimit())) {
                detail.setMaxLimit(detailRequest.getMaxLimit());
                isModified = true;
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
    public ReferencedWarning getReferencedWarning(Long ratePlanFlatRateId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanFlatRate not found with id: " + ratePlanFlatRateId));
        return referencedWarning;
    }

    @Override
    public Page<RatePlanFlatRateDTO> findAllByRatePlanId(Long ratePlanId, Pageable pageable) {
        Page<RatePlanFlatRate> page = ratePlanFlatRateRepository.findAllByRatePlan_RatePlanId(ratePlanId, pageable);
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

}
