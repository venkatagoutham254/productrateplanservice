package aforo.productrateplanservie.service;

import aforo.productrateplanservie.domain.RatePlanFreemiumRate;
import aforo.productrateplanservie.domain.RatePlanFreemiumRateDetails;
import aforo.productrateplanservie.model.RatePlanFreemiumRateDTO;
import aforo.productrateplanservie.repos.RatePlanFreemiumRateDetailsRepository;
import aforo.productrateplanservie.repos.RatePlanFreemiumRateRepository;
import aforo.productrateplanservie.repos.RatePlanRepository;
import aforo.productrateplanservie.util.NotFoundException;
import aforo.productrateplanservie.util.ReferencedWarning;
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
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = ratePlanFreemiumRateRepository.findAllByRatePlanFreemiumRateId(integerFilter, pageable);
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
    public RatePlanFreemiumRateDTO get(final Integer ratePlanFreemiumRateId) {
        return ratePlanFreemiumRateRepository.findById(ratePlanFreemiumRateId)
                .map(ratePlanFreemiumRate -> ratePlanFreemiumRateMapper.updateRatePlanFreemiumRateDTO(ratePlanFreemiumRate, new RatePlanFreemiumRateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO) {
        final RatePlanFreemiumRate ratePlanFreemiumRate = new RatePlanFreemiumRate();
        ratePlanFreemiumRateMapper.updateRatePlanFreemiumRate(ratePlanFreemiumRateDTO, ratePlanFreemiumRate, ratePlanRepository);
        return ratePlanFreemiumRateRepository.save(ratePlanFreemiumRate).getRatePlanFreemiumRateId();
    }

    @Override
    public void update(final Integer ratePlanFreemiumRateId,
            final RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO) {
        final RatePlanFreemiumRate ratePlanFreemiumRate = ratePlanFreemiumRateRepository.findById(ratePlanFreemiumRateId)
                .orElseThrow(NotFoundException::new);
        ratePlanFreemiumRateMapper.updateRatePlanFreemiumRate(ratePlanFreemiumRateDTO, ratePlanFreemiumRate, ratePlanRepository);
        ratePlanFreemiumRateRepository.save(ratePlanFreemiumRate);
    }

    @Override
    public void delete(final Integer ratePlanFreemiumRateId) {
        ratePlanFreemiumRateRepository.deleteById(ratePlanFreemiumRateId);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final Integer ratePlanFreemiumRateId) {
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
