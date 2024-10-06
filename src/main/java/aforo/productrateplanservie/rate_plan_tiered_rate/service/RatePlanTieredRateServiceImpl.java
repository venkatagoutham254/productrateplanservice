package aforo.productrateplanservie.rate_plan_tiered_rate.service;

import aforo.productrateplanservie.rate_plan.repos.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_tiered_rate.domain.RatePlanTieredRate;
import aforo.productrateplanservie.rate_plan_tiered_rate.model.RatePlanTieredRateDTO;
import aforo.productrateplanservie.rate_plan_tiered_rate.repos.RatePlanTieredRateRepository;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.domain.RatePlanTieredRateDetails;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.repos.RatePlanTieredRateDetailsRepository;
import aforo.productrateplanservie.util.NotFoundException;
import aforo.productrateplanservie.util.ReferencedWarning;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class RatePlanTieredRateServiceImpl implements RatePlanTieredRateService {

    private final RatePlanTieredRateRepository ratePlanTieredRateRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanTieredRateMapper ratePlanTieredRateMapper;
    private final RatePlanTieredRateDetailsRepository ratePlanTieredRateDetailsRepository;

    public RatePlanTieredRateServiceImpl(
            final RatePlanTieredRateRepository ratePlanTieredRateRepository,
            final RatePlanRepository ratePlanRepository,
            final RatePlanTieredRateMapper ratePlanTieredRateMapper,
            final RatePlanTieredRateDetailsRepository ratePlanTieredRateDetailsRepository) {
        this.ratePlanTieredRateRepository = ratePlanTieredRateRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanTieredRateMapper = ratePlanTieredRateMapper;
        this.ratePlanTieredRateDetailsRepository = ratePlanTieredRateDetailsRepository;
    }

    @Override
    public Page<RatePlanTieredRateDTO> findAll(final String filter, final Pageable pageable) {
        Page<RatePlanTieredRate> page;
        if (filter != null) {
            UUID uuidFilter = null;
            try {
                uuidFilter = UUID.fromString(filter);
            } catch (final IllegalArgumentException illegalArgumentException) {
                // keep null - no parseable input
            }
            page = ratePlanTieredRateRepository.findAllByRatePlanTieredRateId(uuidFilter, pageable);
        } else {
            page = ratePlanTieredRateRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlanTieredRate -> ratePlanTieredRateMapper.updateRatePlanTieredRateDTO(ratePlanTieredRate, new RatePlanTieredRateDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public RatePlanTieredRateDTO get(final UUID ratePlanTieredRateId) {
        return ratePlanTieredRateRepository.findById(ratePlanTieredRateId)
                .map(ratePlanTieredRate -> ratePlanTieredRateMapper.updateRatePlanTieredRateDTO(ratePlanTieredRate, new RatePlanTieredRateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public UUID create(final RatePlanTieredRateDTO ratePlanTieredRateDTO) {
        final RatePlanTieredRate ratePlanTieredRate = new RatePlanTieredRate();
        ratePlanTieredRateMapper.updateRatePlanTieredRate(ratePlanTieredRateDTO, ratePlanTieredRate, ratePlanRepository);
        return ratePlanTieredRateRepository.save(ratePlanTieredRate).getRatePlanTieredRateId();
    }

    @Override
    public void update(final UUID ratePlanTieredRateId,
            final RatePlanTieredRateDTO ratePlanTieredRateDTO) {
        final RatePlanTieredRate ratePlanTieredRate = ratePlanTieredRateRepository.findById(ratePlanTieredRateId)
                .orElseThrow(NotFoundException::new);
        ratePlanTieredRateMapper.updateRatePlanTieredRate(ratePlanTieredRateDTO, ratePlanTieredRate, ratePlanRepository);
        ratePlanTieredRateRepository.save(ratePlanTieredRate);
    }

    @Override
    public void delete(final UUID ratePlanTieredRateId) {
        ratePlanTieredRateRepository.deleteById(ratePlanTieredRateId);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final UUID ratePlanTieredRateId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final RatePlanTieredRate ratePlanTieredRate = ratePlanTieredRateRepository.findById(ratePlanTieredRateId)
                .orElseThrow(NotFoundException::new);
        final RatePlanTieredRateDetails ratePlanTieredRateRatePlanTieredRateDetails = ratePlanTieredRateDetailsRepository.findFirstByRatePlanTieredRate(ratePlanTieredRate);
        if (ratePlanTieredRateRatePlanTieredRateDetails != null) {
            referencedWarning.setKey("ratePlanTieredRate.ratePlanTieredRateDetails.ratePlanTieredRate.referenced");
            referencedWarning.addParam(ratePlanTieredRateRatePlanTieredRateDetails.getTierNumber());
            return referencedWarning;
        }
        return null;
    }

}
