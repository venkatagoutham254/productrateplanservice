package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetails;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetailsRepository;
import aforo.productrateplanservie.util.NotFoundException;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class RatePlanFlatRateServiceImpl implements RatePlanFlatRateService {

    private final RatePlanFlatRateRepository ratePlanFlatRateRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanFlatRateMapper ratePlanFlatRateMapper;
    private final RatePlanFlatRateDetailsRepository ratePlanFlatRateDetailsRepository;

    public RatePlanFlatRateServiceImpl(final RatePlanFlatRateRepository ratePlanFlatRateRepository,
            final RatePlanRepository ratePlanRepository,
            final RatePlanFlatRateMapper ratePlanFlatRateMapper,
            final RatePlanFlatRateDetailsRepository ratePlanFlatRateDetailsRepository) {
        this.ratePlanFlatRateRepository = ratePlanFlatRateRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanFlatRateMapper = ratePlanFlatRateMapper;
        this.ratePlanFlatRateDetailsRepository = ratePlanFlatRateDetailsRepository;
    }

    @Override
    public Page<RatePlanFlatRateDTO> findAll(final String filter, final Pageable pageable) {
        Page<RatePlanFlatRate> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
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
    public RatePlanFlatRateDTO get(final Long ratePlanFlatRateId) {
        return ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final RatePlanFlatRateDTO ratePlanFlatRateDTO) {
        final RatePlanFlatRate ratePlanFlatRate = new RatePlanFlatRate();
        ratePlanFlatRateMapper.updateRatePlanFlatRate(ratePlanFlatRateDTO, ratePlanFlatRate, ratePlanRepository);
        return ratePlanFlatRateRepository.save(ratePlanFlatRate).getRatePlanFlatRateId();
    }

    @Override
    public void update(final Long ratePlanFlatRateId,
            final RatePlanFlatRateDTO ratePlanFlatRateDTO) {
        final RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(NotFoundException::new);
        ratePlanFlatRateMapper.updateRatePlanFlatRate(ratePlanFlatRateDTO, ratePlanFlatRate, ratePlanRepository);
        ratePlanFlatRateRepository.save(ratePlanFlatRate);
    }

    @Override
    public void delete(final Long ratePlanFlatRateId) {
        ratePlanFlatRateRepository.deleteById(ratePlanFlatRateId);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final Long ratePlanFlatRateId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(NotFoundException::new);
        final RatePlanFlatRateDetails ratePlanFlatRateRatePlanFlatRateDetails = ratePlanFlatRateDetailsRepository.findFirstByRatePlanFlatRate(ratePlanFlatRate);
        if (ratePlanFlatRateRatePlanFlatRateDetails != null) {
            referencedWarning.setKey("ratePlanFlatRate.ratePlanFlatRateDetails.ratePlanFlatRate.referenced");
            referencedWarning.addParam(ratePlanFlatRateRatePlanFlatRateDetails.getId());
            return referencedWarning;
        }
        return null;
    }

}
