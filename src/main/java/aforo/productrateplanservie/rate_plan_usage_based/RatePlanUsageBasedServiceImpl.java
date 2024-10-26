package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRates;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRatesDTO;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRatesRepository;
import aforo.productrateplanservie.util.NotFoundException;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Long create(Long ratePlanId, RatePlanUsageBasedDTO ratePlanUsageBasedDTO) {
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId).orElseThrow(() -> new IllegalArgumentException("RatePlan with ID " + ratePlanId + " does not exist"));
        RatePlanUsageBased ratePlanUsageBased = new RatePlanUsageBased();
        ratePlanUsageBasedMapper.updateRatePlanUsageBased(ratePlanUsageBasedDTO, ratePlanUsageBased, ratePlanRepository);
        ratePlanUsageBased.setRatePlan(ratePlan);
        RatePlanUsageBased savedRatePlanUsageBased = ratePlanUsageBasedRepository.save(ratePlanUsageBased);

        if (ratePlanUsageBasedDTO.getRatePlanUsageBasedRatesDTO() != null) {
            for (RatePlanUsageBasedRatesDTO detailsDTO : ratePlanUsageBasedDTO.getRatePlanUsageBasedRatesDTO()) {
                RatePlanUsageBasedRates details = ratePlanUsageBasedMapper.mapToRatePlanUsageBasedRates(detailsDTO);
                details.setRatePlanUsageBased(savedRatePlanUsageBased);
                ratePlanUsageBasedRatesRepository.save(details);
            }
        }

        return savedRatePlanUsageBased.getRatePlanUsageRateId();
    }

    @Override
    @Transactional
    public void update(final Long ratePlanUsageRateId, final RatePlanUsageBasedDTO ratePlanUsageBasedDTO) {
        RatePlanUsageBased ratePlanUsageBased = ratePlanUsageBasedRepository.findById(ratePlanUsageRateId).orElseThrow(NotFoundException::new);
        ratePlanUsageBasedMapper.updateRatePlanUsageBased(ratePlanUsageBasedDTO, ratePlanUsageBased, ratePlanRepository);

        // Retrieve the IDs of existing rates
        Set<Long> existingRatesIds = ratePlanUsageBased.getRatePlanUsageBasedRates().stream().map(RatePlanUsageBasedRates::getId).collect(Collectors.toSet());

        Set<Long> updatedRatesIds = ratePlanUsageBasedDTO.getRatePlanUsageBasedRatesDTO().stream().map(RatePlanUsageBasedRatesDTO::getId).filter(id -> id != null) // Only keep IDs that are not null
                .collect(Collectors.toSet());

        // Identify rates to be removed
        Set<Long> ratesToRemove = existingRatesIds.stream().filter(id -> !updatedRatesIds.contains(id)).collect(Collectors.toSet());

        // First, delete the rates that are no longer needed
        for (Long id : ratesToRemove) {
            ratePlanUsageBasedRatesRepository.deleteById(id);
        }

        // Now update existing rates or add new ones
        for (RatePlanUsageBasedRatesDTO detailsDTO : ratePlanUsageBasedDTO.getRatePlanUsageBasedRatesDTO()) {
            RatePlanUsageBasedRates details;
            if (detailsDTO.getId() != null && ratePlanUsageBasedRatesRepository.existsById(detailsDTO.getId())) {
                details = ratePlanUsageBasedRatesRepository.findById(detailsDTO.getId()).orElseThrow(NotFoundException::new);
                ratePlanUsageBasedMapper.updateRatePlanUsageBasedRatesFromDTO(detailsDTO, details);
            } else {
                details = ratePlanUsageBasedMapper.mapToRatePlanUsageBasedRates(detailsDTO);
                details.setRatePlanUsageBased(ratePlanUsageBased);
            }
            ratePlanUsageBasedRatesRepository.save(details);
        }

        // Finally, save the main entity
        ratePlanUsageBasedRepository.save(ratePlanUsageBased);
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
