package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRates;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRatesDTO;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRatesRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.rate_plan_usage_based_rates.UpdateRatePlanUsageBasedRatesRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RatePlanUsageBasedServiceImpl implements RatePlanUsageBasedService {

    private final RatePlanUsageBasedRepository ratePlanUsageBasedRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanUsageBasedMapper ratePlanUsageBasedMapper;
    private final RatePlanUsageBasedRatesRepository ratePlanUsageBasedRatesRepository;

    public RatePlanUsageBasedServiceImpl(final RatePlanUsageBasedRepository ratePlanUsageBasedRepository, final RatePlanRepository ratePlanRepository, final RatePlanUsageBasedMapper ratePlanUsageBasedMapper, final RatePlanUsageBasedRatesRepository ratePlanUsageBasedRatesRepository) {
        this.ratePlanUsageBasedRepository = ratePlanUsageBasedRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanUsageBasedMapper = ratePlanUsageBasedMapper;
        this.ratePlanUsageBasedRatesRepository = ratePlanUsageBasedRatesRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RatePlanUsageBasedDTO> findAll(final String filter, final Pageable pageable) {
        Page<RatePlanUsageBased> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = ratePlanUsageBasedRepository.findAllByRatePlanUsageRateId(longFilter, pageable);
        } else {
            page = ratePlanUsageBasedRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent().stream().map(ratePlanUsageBased -> ratePlanUsageBasedMapper.updateRatePlanUsageBasedDTO(ratePlanUsageBased, new RatePlanUsageBasedDTO())).collect(Collectors.toList()), pageable, page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public RatePlanUsageBasedDTO get(final Long ratePlanUsageRateId) {
        return ratePlanUsageBasedRepository.findById(ratePlanUsageRateId).map(ratePlanUsageBased -> ratePlanUsageBasedMapper.updateRatePlanUsageBasedDTO(ratePlanUsageBased, new RatePlanUsageBasedDTO())).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public Long create(Long ratePlanId, CreateRatePlanUsageBasedRequest request) {
        // Retrieve the RatePlan entity by ID
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
                .orElseThrow(() -> new NotFoundException("RatePlan with ID " + ratePlanId + " not found"));

        // Map the create request DTO to entity
        RatePlanUsageBased ratePlanUsageBased = ratePlanUsageBasedMapper.toEntity(request);
        ratePlanUsageBased.setRatePlan(ratePlan);

        // Save RatePlanUsageBased entity
        RatePlanUsageBased savedRatePlanUsageBased = ratePlanUsageBasedRepository.save(ratePlanUsageBased);

        // Save each nested RatePlanUsageBasedRates entity if provided
        if (request.getRatePlanUsageBasedRatesDTO() != null) {
            request.getRatePlanUsageBasedRatesDTO().forEach(rateRequest -> {
                RatePlanUsageBasedRates rate = ratePlanUsageBasedMapper.toEntity(rateRequest);
                rate.setRatePlanUsageBased(savedRatePlanUsageBased);
                ratePlanUsageBasedRatesRepository.save(rate);
            });
        }

        return savedRatePlanUsageBased.getRatePlanUsageRateId();
    }


    @Transactional
    @Override
    public void update(Long ratePlanId, Long ratePlanUsageRateId, @Valid UpdateRatePlanUsageBasedRequest updateDTO) {
        // Check if RatePlan exists
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new EntityNotFoundException("RatePlan with id " + ratePlanId + " not found");
        }

        // Retrieve the existing RatePlanUsageBased entity
        RatePlanUsageBased existingRatePlanUsageBased = ratePlanUsageBasedRepository.findById(ratePlanUsageRateId)
                .orElseThrow(() -> new EntityNotFoundException("RatePlanUsageBased with id " + ratePlanUsageRateId + " not found"));

        boolean isModified = false;

        // Update main RatePlanUsageBased fields directly
        if (updateDTO.getRatePlanUsageDescription() != null &&
                !Objects.equals(existingRatePlanUsageBased.getRatePlanUsageDescription(), updateDTO.getRatePlanUsageDescription())) {
            existingRatePlanUsageBased.setRatePlanUsageDescription(updateDTO.getRatePlanUsageDescription());
            isModified = true;
        }

        if (updateDTO.getDescription() != null &&
                !Objects.equals(existingRatePlanUsageBased.getDescription(), updateDTO.getDescription())) {
            existingRatePlanUsageBased.setDescription(updateDTO.getDescription());
            isModified = true;
        }

        if (updateDTO.getUnitType() != null &&
                !Objects.equals(existingRatePlanUsageBased.getUnitType(), updateDTO.getUnitType())) {
            existingRatePlanUsageBased.setUnitType(updateDTO.getUnitType());
            isModified = true;
        }

        if (updateDTO.getUnitMeasurement() != null &&
                !Objects.equals(existingRatePlanUsageBased.getUnitMeasurement(), updateDTO.getUnitMeasurement())) {
            existingRatePlanUsageBased.setUnitMeasurement(updateDTO.getUnitMeasurement());
            isModified = true;
        }

        if (updateDTO.getUnitCalculation() != null &&
                !Objects.equals(existingRatePlanUsageBased.getUnitCalculation(), updateDTO.getUnitCalculation())) {
            existingRatePlanUsageBased.setUnitCalculation(updateDTO.getUnitCalculation());
            isModified = true;
        }

        // Update nested RatePlanUsageBasedRates
        if (updateDTO.getRatePlanUsageBasedRatesDTO() != null) {
            isModified |= updateRatePlanUsageBasedRates(existingRatePlanUsageBased, updateDTO.getRatePlanUsageBasedRatesDTO());
        }

        // Save the updated entity only if modifications were made
        if (isModified) {
            ratePlanUsageBasedRepository.save(existingRatePlanUsageBased);
        }
    }

    private boolean updateRatePlanUsageBasedRates(RatePlanUsageBased ratePlanUsageBased,
                                                  Set<UpdateRatePlanUsageBasedRatesRequest> ratesDTO) {
        boolean isModified = false;

        // Retrieve existing rates
        Set<RatePlanUsageBasedRates> existingRates = ratePlanUsageBased.getRatePlanUsageBasedRates();

        for (UpdateRatePlanUsageBasedRatesRequest rateDTO : ratesDTO) {
            RatePlanUsageBasedRates rate = existingRates.stream()
                    .filter(r -> r.getId().equals(rateDTO.getId()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("RatePlanUsageBasedRates not found for ID: " + rateDTO.getId()));

            // Update fields if they have changed
            if (rateDTO.getUnitRate() != null && !Objects.equals(rate.getUnitRate(), rateDTO.getUnitRate())) {
                rate.setUnitRate(rateDTO.getUnitRate());
                isModified = true;
            }
        }

        // Add new rates if not found in the existing set
        for (UpdateRatePlanUsageBasedRatesRequest newRateDTO : ratesDTO) {
            if (existingRates.stream().noneMatch(r -> r.getId().equals(newRateDTO.getId()))) {
                RatePlanUsageBasedRates newRate = new RatePlanUsageBasedRates();
                newRate.setUnitRate(newRateDTO.getUnitRate());
                newRate.setRatePlanUsageBased(ratePlanUsageBased);
                ratePlanUsageBased.getRatePlanUsageBasedRates().add(newRate);
                isModified = true;
            }
        }

        return isModified;
    }



    @Override
    @Transactional
    public void delete(final Long ratePlanUsageRateId) {
        // Check if the RatePlanUsageBased exists
        if (!ratePlanUsageBasedRepository.existsById(ratePlanUsageRateId)) {
            throw new NotFoundException("RatePlanUsageBased with ID " + ratePlanUsageRateId + " does not exist");
        }
        // If it exists, proceed to delete
        ratePlanUsageBasedRepository.deleteById(ratePlanUsageRateId);
    }


//    @Override
//    @Transactional(readOnly = true)
//    public ReferencedWarning getReferencedWarning(final Long ratePlanUsageRateId) {
//        ReferencedWarning referencedWarning = new ReferencedWarning();
//        RatePlanUsageBased ratePlanUsageBased = ratePlanUsageBasedRepository.findById(ratePlanUsageRateId)
//                .orElseThrow(NotFoundException::new);
//        RatePlanUsageBasedRates ratePlanUsageRateRatePlanUsageBasedRates = ratePlanUsageBasedRatesRepository.findFirstByRatePlanUsageBased(ratePlanUsageBased);
//        if (ratePlanUsageRateRatePlanUsageBasedRates != null) {
//            referencedWarning.setKey("ratePlanUsageBased.ratePlanUsageBasedRates.ratePlanUsageRate.referenced");
//            referencedWarning.addParam(ratePlanUsageRateRatePlanUsageBasedRates.getId());
//            return referencedWarning;
//        }
//        return null;
//    }
}
