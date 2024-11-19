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

import java.util.Map;
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


    @Override
    @Transactional
    public void update(Long ratePlanId, Long ratePlanUsageRateId, @Valid UpdateRatePlanUsageBasedRequest updateDTO) {
        // Check if RatePlan exists
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new NotFoundException("RatePlan with id " + ratePlanId + " not found");
        }

        // Retrieve the existing RatePlanUsageBased entity
        RatePlanUsageBased existingRatePlanUsageBased = ratePlanUsageBasedRepository.findById(ratePlanUsageRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanUsageBased with id " + ratePlanUsageRateId + " not found"));

        boolean isModified = updateMainUsageRateFields(existingRatePlanUsageBased, updateDTO);

        // Handle nested rates
        if (updateDTO.getRatePlanUsageBasedRatesDTO() != null) {
            isModified |= updateUsageBasedRates(existingRatePlanUsageBased, updateDTO.getRatePlanUsageBasedRatesDTO());
        }

        // Save changes only if modifications were made
        if (isModified) {
            ratePlanUsageBasedRepository.save(existingRatePlanUsageBased);
        }
    }

    private boolean updateMainUsageRateFields(RatePlanUsageBased existingRatePlanUsageBased, UpdateRatePlanUsageBasedRequest updateDTO) {
        boolean isModified = false;

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

        return isModified;
    }

    private boolean updateUsageBasedRates(RatePlanUsageBased ratePlanUsageBased,
                                          Set<UpdateRatePlanUsageBasedRatesRequest> ratesDTO) {
        boolean isModified = false;
        Set<RatePlanUsageBasedRates> existingRates = ratePlanUsageBased.getRatePlanUsageBasedRates();

        if (ratesDTO.size() == 1 && existingRates.size() == 1) {
            // Handle single rate case
            isModified = updateSingleUsageRate(existingRates.iterator().next(), ratesDTO.iterator().next());
        } else {
            // Handle multiple rate cases
            isModified = updateMultipleUsageRates(existingRates, ratesDTO);
        }

        return isModified;
    }

    private boolean updateSingleUsageRate(RatePlanUsageBasedRates existingRate, UpdateRatePlanUsageBasedRatesRequest rateDTO) {
        boolean isModified = false;

        if (rateDTO.getUnitRate() != null &&
                !Objects.equals(existingRate.getUnitRate(), rateDTO.getUnitRate())) {
            existingRate.setUnitRate(rateDTO.getUnitRate());
            isModified = true;
        }

        return isModified;
    }

    private boolean updateMultipleUsageRates(Set<RatePlanUsageBasedRates> existingRates,
                                             Set<UpdateRatePlanUsageBasedRatesRequest> ratesDTO) {
        boolean isModified = false;

        Map<Long, UpdateRatePlanUsageBasedRatesRequest> requestMap = ratesDTO.stream()
                .filter(request -> request.getId() != null) // Ensure ID is present
                .collect(Collectors.toMap(UpdateRatePlanUsageBasedRatesRequest::getId, request -> request));

        for (RatePlanUsageBasedRates existingRate : existingRates) {
            if (requestMap.containsKey(existingRate.getId())) {
                UpdateRatePlanUsageBasedRatesRequest rateDTO = requestMap.get(existingRate.getId());
                isModified |= updateSingleUsageRate(existingRate, rateDTO);
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
