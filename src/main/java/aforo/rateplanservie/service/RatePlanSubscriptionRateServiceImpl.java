package aforo.rateplanservie.service;

import aforo.rateplanservie.domain.RatePlanSubscriptionRate;
import aforo.rateplanservie.domain.RatePlanSubscriptionRateDetails;
import aforo.rateplanservie.model.RatePlanSubscriptionRateDTO;
import aforo.rateplanservie.repos.RatePlanRepository;
import aforo.rateplanservie.repos.RatePlanSubscriptionRateDetailsRepository;
import aforo.rateplanservie.repos.RatePlanSubscriptionRateRepository;
import aforo.rateplanservie.util.NotFoundException;
import aforo.rateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class RatePlanSubscriptionRateServiceImpl implements RatePlanSubscriptionRateService {

    private final RatePlanSubscriptionRateRepository ratePlanSubscriptionRateRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanSubscriptionRateMapper ratePlanSubscriptionRateMapper;
    private final RatePlanSubscriptionRateDetailsRepository ratePlanSubscriptionRateDetailsRepository;

    public RatePlanSubscriptionRateServiceImpl(
            final RatePlanSubscriptionRateRepository ratePlanSubscriptionRateRepository,
            final RatePlanRepository ratePlanRepository,
            final RatePlanSubscriptionRateMapper ratePlanSubscriptionRateMapper,
            final RatePlanSubscriptionRateDetailsRepository ratePlanSubscriptionRateDetailsRepository) {
        this.ratePlanSubscriptionRateRepository = ratePlanSubscriptionRateRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanSubscriptionRateMapper = ratePlanSubscriptionRateMapper;
        this.ratePlanSubscriptionRateDetailsRepository = ratePlanSubscriptionRateDetailsRepository;
    }

    @Override
    public Page<RatePlanSubscriptionRateDTO> findAll(final String filter, final Pageable pageable) {
        Page<RatePlanSubscriptionRate> page;
        if (filter != null) {
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = ratePlanSubscriptionRateRepository.findAllByRatePlanSubscriptionRateId(integerFilter, pageable);
        } else {
            page = ratePlanSubscriptionRateRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlanSubscriptionRate -> ratePlanSubscriptionRateMapper.updateRatePlanSubscriptionRateDTO(ratePlanSubscriptionRate, new RatePlanSubscriptionRateDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public RatePlanSubscriptionRateDTO get(final Integer ratePlanSubscriptionRateId) {
        return ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)
                .map(ratePlanSubscriptionRate -> ratePlanSubscriptionRateMapper.updateRatePlanSubscriptionRateDTO(ratePlanSubscriptionRate, new RatePlanSubscriptionRateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO) {
        final RatePlanSubscriptionRate ratePlanSubscriptionRate = new RatePlanSubscriptionRate();
        ratePlanSubscriptionRateMapper.updateRatePlanSubscriptionRate(ratePlanSubscriptionRateDTO, ratePlanSubscriptionRate, ratePlanRepository);
        return ratePlanSubscriptionRateRepository.save(ratePlanSubscriptionRate).getRatePlanSubscriptionRateId();
    }

    @Override
    public void update(final Integer ratePlanSubscriptionRateId,
            final RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO) {
        final RatePlanSubscriptionRate ratePlanSubscriptionRate = ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)
                .orElseThrow(NotFoundException::new);
        ratePlanSubscriptionRateMapper.updateRatePlanSubscriptionRate(ratePlanSubscriptionRateDTO, ratePlanSubscriptionRate, ratePlanRepository);
        ratePlanSubscriptionRateRepository.save(ratePlanSubscriptionRate);
    }

    @Override
    public void delete(final Integer ratePlanSubscriptionRateId) {
        ratePlanSubscriptionRateRepository.deleteById(ratePlanSubscriptionRateId);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final Integer ratePlanSubscriptionRateId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final RatePlanSubscriptionRate ratePlanSubscriptionRate = ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)
                .orElseThrow(NotFoundException::new);
        final RatePlanSubscriptionRateDetails ratePlanSubscriptionRateRatePlanSubscriptionRateDetails = ratePlanSubscriptionRateDetailsRepository.findFirstByRatePlanSubscriptionRate(ratePlanSubscriptionRate);
        if (ratePlanSubscriptionRateRatePlanSubscriptionRateDetails != null) {
            referencedWarning.setKey("ratePlanSubscriptionRate.ratePlanSubscriptionRateDetails.ratePlanSubscriptionRate.referenced");
            referencedWarning.addParam(ratePlanSubscriptionRateRatePlanSubscriptionRateDetails.getId());
            return referencedWarning;
        }
        return null;
    }

}
