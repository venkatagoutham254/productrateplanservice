package aforo.rateplanservie.service;

import aforo.rateplanservie.domain.RatePlanFlatRate;
import aforo.rateplanservie.domain.RatePlanFlatRateDetails;
import aforo.rateplanservie.model.RatePlanFlatRateDTO;
import aforo.rateplanservie.repos.RatePlanFlatRateDetailsRepository;
import aforo.rateplanservie.repos.RatePlanFlatRateRepository;
import aforo.rateplanservie.repos.RatePlanRepository;
import aforo.rateplanservie.util.NotFoundException;
import aforo.rateplanservie.util.ReferencedWarning;
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
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = ratePlanFlatRateRepository.findAllByRatePlanFlatRateId(integerFilter, pageable);
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
    public RatePlanFlatRateDTO get(final Integer ratePlanFlatRateId) {
        return ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final RatePlanFlatRateDTO ratePlanFlatRateDTO) {
        final RatePlanFlatRate ratePlanFlatRate = new RatePlanFlatRate();
        ratePlanFlatRateMapper.updateRatePlanFlatRate(ratePlanFlatRateDTO, ratePlanFlatRate, ratePlanRepository);
        return ratePlanFlatRateRepository.save(ratePlanFlatRate).getRatePlanFlatRateId();
    }

    @Override
    public void update(final Integer ratePlanFlatRateId,
            final RatePlanFlatRateDTO ratePlanFlatRateDTO) {
        final RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(NotFoundException::new);
        ratePlanFlatRateMapper.updateRatePlanFlatRate(ratePlanFlatRateDTO, ratePlanFlatRate, ratePlanRepository);
        ratePlanFlatRateRepository.save(ratePlanFlatRate);
    }

    @Override
    public void delete(final Integer ratePlanFlatRateId) {
        ratePlanFlatRateRepository.deleteById(ratePlanFlatRateId);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final Integer ratePlanFlatRateId) {
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
