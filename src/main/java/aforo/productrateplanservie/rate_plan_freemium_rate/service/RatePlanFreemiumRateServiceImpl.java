package aforo.productrateplanservie.rate_plan_freemium_rate.service;

import aforo.productrateplanservie.rate_plan.repos.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_freemium_rate.domain.RatePlanFreemiumRate;
import aforo.productrateplanservie.rate_plan_freemium_rate.model.RatePlanFreemiumRateDTO;
import aforo.productrateplanservie.rate_plan_freemium_rate.repos.RatePlanFreemiumRateRepository;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.domain.RatePlanFreemiumRateDetails;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.repos.RatePlanFreemiumRateDetailsRepository;
import aforo.productrateplanservie.util.NotFoundException;
import aforo.productrateplanservie.util.ReferencedWarning;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class RatePlanFreemiumRateServiceImpl implements RatePlanFreemiumRateService {

    private final RatePlanFreemiumRateRepository ratePlanFreemiumRateRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanFreemiumRateMapper ratePlanFreemiumRateMapper;
    private final RatePlanFreemiumRateDetailsRepository ratePlanFreemiumRateDetailsRepository;

    public RatePlanFreemiumRateServiceImpl(
            final RatePlanFreemiumRateRepository ratePlanFreemiumRateRepository,
            final RatePlanRepository ratePlanRepository,
            final RatePlanFreemiumRateMapper ratePlanFreemiumRateMapper,
            final RatePlanFreemiumRateDetailsRepository ratePlanFreemiumRateDetailsRepository) {
        this.ratePlanFreemiumRateRepository = ratePlanFreemiumRateRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanFreemiumRateMapper = ratePlanFreemiumRateMapper;
        this.ratePlanFreemiumRateDetailsRepository = ratePlanFreemiumRateDetailsRepository;
    }

    @Override
    public Page<RatePlanFreemiumRateDTO> findAll(final String filter, final Pageable pageable) {
        Page<RatePlanFreemiumRate> page;
        if (filter != null) {
            UUID uuidFilter = null;
            try {
                uuidFilter = UUID.fromString(filter);
            } catch (final IllegalArgumentException illegalArgumentException) {
                // keep null - no parseable input
            }
            page = ratePlanFreemiumRateRepository.findAllByRatePlanFreemiumRateId(uuidFilter, pageable);
        } else {
            page = ratePlanFreemiumRateRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlanFreemiumRate -> ratePlanFreemiumRateMapper.updateRatePlanFreemiumRateDTO(ratePlanFreemiumRate, new RatePlanFreemiumRateDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public RatePlanFreemiumRateDTO get(final UUID ratePlanFreemiumRateId) {
        return ratePlanFreemiumRateRepository.findById(ratePlanFreemiumRateId)
                .map(ratePlanFreemiumRate -> ratePlanFreemiumRateMapper.updateRatePlanFreemiumRateDTO(ratePlanFreemiumRate, new RatePlanFreemiumRateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public UUID create(final RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO) {
        final RatePlanFreemiumRate ratePlanFreemiumRate = new RatePlanFreemiumRate();
        ratePlanFreemiumRateMapper.updateRatePlanFreemiumRate(ratePlanFreemiumRateDTO, ratePlanFreemiumRate, ratePlanRepository);
        return ratePlanFreemiumRateRepository.save(ratePlanFreemiumRate).getRatePlanFreemiumRateId();
    }

    @Override
    public void update(final UUID ratePlanFreemiumRateId,
            final RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO) {
        final RatePlanFreemiumRate ratePlanFreemiumRate = ratePlanFreemiumRateRepository.findById(ratePlanFreemiumRateId)
                .orElseThrow(NotFoundException::new);
        ratePlanFreemiumRateMapper.updateRatePlanFreemiumRate(ratePlanFreemiumRateDTO, ratePlanFreemiumRate, ratePlanRepository);
        ratePlanFreemiumRateRepository.save(ratePlanFreemiumRate);
    }

    @Override
    public void delete(final UUID ratePlanFreemiumRateId) {
        ratePlanFreemiumRateRepository.deleteById(ratePlanFreemiumRateId);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final UUID ratePlanFreemiumRateId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final RatePlanFreemiumRate ratePlanFreemiumRate = ratePlanFreemiumRateRepository.findById(ratePlanFreemiumRateId)
                .orElseThrow(NotFoundException::new);
        final RatePlanFreemiumRateDetails ratePlanFreemiumRateRatePlanFreemiumRateDetails = ratePlanFreemiumRateDetailsRepository.findFirstByRatePlanFreemiumRate(ratePlanFreemiumRate);
        if (ratePlanFreemiumRateRatePlanFreemiumRateDetails != null) {
            referencedWarning.setKey("ratePlanFreemiumRate.ratePlanFreemiumRateDetails.ratePlanFreemiumRate.referenced");
            referencedWarning.addParam(ratePlanFreemiumRateRatePlanFreemiumRateDetails.getId());
            return referencedWarning;
        }
        return null;
    }

}
