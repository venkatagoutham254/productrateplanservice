package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRates;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRatesRepository;
import aforo.productrateplanservie.util.NotFoundException;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class RatePlanUsageBasedServiceImpl implements RatePlanUsageBasedService {

    private final RatePlanUsageBasedRepository ratePlanUsageBasedRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanUsageBasedMapper ratePlanUsageBasedMapper;
    private final RatePlanUsageBasedRatesRepository ratePlanUsageBasedRatesRepository;

    public RatePlanUsageBasedServiceImpl(
            final RatePlanUsageBasedRepository ratePlanUsageBasedRepository,
            final RatePlanRepository ratePlanRepository,
            final RatePlanUsageBasedMapper ratePlanUsageBasedMapper,
            final RatePlanUsageBasedRatesRepository ratePlanUsageBasedRatesRepository) {
        this.ratePlanUsageBasedRepository = ratePlanUsageBasedRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanUsageBasedMapper = ratePlanUsageBasedMapper;
        this.ratePlanUsageBasedRatesRepository = ratePlanUsageBasedRatesRepository;
    }

    @Override
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
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlanUsageBased -> ratePlanUsageBasedMapper.updateRatePlanUsageBasedDTO(ratePlanUsageBased, new RatePlanUsageBasedDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public RatePlanUsageBasedDTO get(final Long ratePlanUsageRateId) {
        return ratePlanUsageBasedRepository.findById(ratePlanUsageRateId)
                .map(ratePlanUsageBased -> ratePlanUsageBasedMapper.updateRatePlanUsageBasedDTO(ratePlanUsageBased, new RatePlanUsageBasedDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final RatePlanUsageBasedDTO ratePlanUsageBasedDTO) {
        final RatePlanUsageBased ratePlanUsageBased = new RatePlanUsageBased();
        ratePlanUsageBasedMapper.updateRatePlanUsageBased(ratePlanUsageBasedDTO, ratePlanUsageBased, ratePlanRepository);
        return ratePlanUsageBasedRepository.save(ratePlanUsageBased).getRatePlanUsageRateId();
    }

    @Override
    public void update(final Long ratePlanUsageRateId,
            final RatePlanUsageBasedDTO ratePlanUsageBasedDTO) {
        final RatePlanUsageBased ratePlanUsageBased = ratePlanUsageBasedRepository.findById(ratePlanUsageRateId)
                .orElseThrow(NotFoundException::new);
        ratePlanUsageBasedMapper.updateRatePlanUsageBased(ratePlanUsageBasedDTO, ratePlanUsageBased, ratePlanRepository);
        ratePlanUsageBasedRepository.save(ratePlanUsageBased);
    }

    @Override
    public void delete(final Long ratePlanUsageRateId) {
        ratePlanUsageBasedRepository.deleteById(ratePlanUsageRateId);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final Long ratePlanUsageRateId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final RatePlanUsageBased ratePlanUsageBased = ratePlanUsageBasedRepository.findById(ratePlanUsageRateId)
                .orElseThrow(NotFoundException::new);
        final RatePlanUsageBasedRates ratePlanUsageRateRatePlanUsageBasedRates = ratePlanUsageBasedRatesRepository.findFirstByRatePlanUsageRate(ratePlanUsageBased);
        if (ratePlanUsageRateRatePlanUsageBasedRates != null) {
            referencedWarning.setKey("ratePlanUsageBased.ratePlanUsageBasedRates.ratePlanUsageRate.referenced");
            referencedWarning.addParam(ratePlanUsageRateRatePlanUsageBasedRates.getId());
            return referencedWarning;
        }
        return null;
    }

}
