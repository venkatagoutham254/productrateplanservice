package aforo.productrateplanservie.service;

import aforo.productrateplanservie.domain.RatePlanUsageBased;
import aforo.productrateplanservie.domain.RatePlanUsageBasedRates;
import aforo.productrateplanservie.model.RatePlanUsageBasedDTO;
import aforo.productrateplanservie.repos.RatePlanRepository;
import aforo.productrateplanservie.repos.RatePlanUsageBasedRatesRepository;
import aforo.productrateplanservie.repos.RatePlanUsageBasedRepository;
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
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = ratePlanUsageBasedRepository.findAllByRatePlanUsageRateId(integerFilter, pageable);
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
    public RatePlanUsageBasedDTO get(final Integer ratePlanUsageRateId) {
        return ratePlanUsageBasedRepository.findById(ratePlanUsageRateId)
                .map(ratePlanUsageBased -> ratePlanUsageBasedMapper.updateRatePlanUsageBasedDTO(ratePlanUsageBased, new RatePlanUsageBasedDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final RatePlanUsageBasedDTO ratePlanUsageBasedDTO) {
        final RatePlanUsageBased ratePlanUsageBased = new RatePlanUsageBased();
        ratePlanUsageBasedMapper.updateRatePlanUsageBased(ratePlanUsageBasedDTO, ratePlanUsageBased, ratePlanRepository);
        return ratePlanUsageBasedRepository.save(ratePlanUsageBased).getRatePlanUsageRateId();
    }

    @Override
    public void update(final Integer ratePlanUsageRateId,
            final RatePlanUsageBasedDTO ratePlanUsageBasedDTO) {
        final RatePlanUsageBased ratePlanUsageBased = ratePlanUsageBasedRepository.findById(ratePlanUsageRateId)
                .orElseThrow(NotFoundException::new);
        ratePlanUsageBasedMapper.updateRatePlanUsageBased(ratePlanUsageBasedDTO, ratePlanUsageBased, ratePlanRepository);
        ratePlanUsageBasedRepository.save(ratePlanUsageBased);
    }

    @Override
    public void delete(final Integer ratePlanUsageRateId) {
        ratePlanUsageBasedRepository.deleteById(ratePlanUsageRateId);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final Integer ratePlanUsageRateId) {
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
