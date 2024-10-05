package aforo.productrateplanservie.service;

import aforo.productrateplanservie.domain.RatePlan;
import aforo.productrateplanservie.domain.RatePlanFlatRate;
import aforo.productrateplanservie.domain.RatePlanFreemiumRate;
import aforo.productrateplanservie.domain.RatePlanSubscriptionRate;
import aforo.productrateplanservie.domain.RatePlanTieredRate;
import aforo.productrateplanservie.domain.RatePlanUsageBased;
import aforo.productrateplanservie.model.RatePlanDTO;
import aforo.productrateplanservie.repos.CurrenciesRepository;
import aforo.productrateplanservie.repos.ProductRepository;
import aforo.productrateplanservie.repos.RatePlanFlatRateRepository;
import aforo.productrateplanservie.repos.RatePlanFreemiumRateRepository;
import aforo.productrateplanservie.repos.RatePlanRepository;
import aforo.productrateplanservie.repos.RatePlanSubscriptionRateRepository;
import aforo.productrateplanservie.repos.RatePlanTieredRateRepository;
import aforo.productrateplanservie.repos.RatePlanUsageBasedRepository;
import aforo.productrateplanservie.util.NotFoundException;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class RatePlanServiceImpl implements RatePlanService {

    private final RatePlanRepository ratePlanRepository;
    private final ProductRepository productRepository;
    private final CurrenciesRepository currenciesRepository;
    private final RatePlanMapper ratePlanMapper;
    private final RatePlanUsageBasedRepository ratePlanUsageBasedRepository;
    private final RatePlanTieredRateRepository ratePlanTieredRateRepository;
    private final RatePlanFlatRateRepository ratePlanFlatRateRepository;
    private final RatePlanSubscriptionRateRepository ratePlanSubscriptionRateRepository;
    private final RatePlanFreemiumRateRepository ratePlanFreemiumRateRepository;

    public RatePlanServiceImpl(final RatePlanRepository ratePlanRepository,
            final ProductRepository productRepository,
            final CurrenciesRepository currenciesRepository, final RatePlanMapper ratePlanMapper,
            final RatePlanUsageBasedRepository ratePlanUsageBasedRepository,
            final RatePlanTieredRateRepository ratePlanTieredRateRepository,
            final RatePlanFlatRateRepository ratePlanFlatRateRepository,
            final RatePlanSubscriptionRateRepository ratePlanSubscriptionRateRepository,
            final RatePlanFreemiumRateRepository ratePlanFreemiumRateRepository) {
        this.ratePlanRepository = ratePlanRepository;
        this.productRepository = productRepository;
        this.currenciesRepository = currenciesRepository;
        this.ratePlanMapper = ratePlanMapper;
        this.ratePlanUsageBasedRepository = ratePlanUsageBasedRepository;
        this.ratePlanTieredRateRepository = ratePlanTieredRateRepository;
        this.ratePlanFlatRateRepository = ratePlanFlatRateRepository;
        this.ratePlanSubscriptionRateRepository = ratePlanSubscriptionRateRepository;
        this.ratePlanFreemiumRateRepository = ratePlanFreemiumRateRepository;
    }

    @Override
    public Page<RatePlanDTO> findAll(final String filter, final Pageable pageable) {
        Page<RatePlan> page;
        if (filter != null) {
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = ratePlanRepository.findAllByRatePlanId(integerFilter, pageable);
        } else {
            page = ratePlanRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlan -> ratePlanMapper.updateRatePlanDTO(ratePlan, new RatePlanDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public RatePlanDTO get(final Integer ratePlanId) {
        return ratePlanRepository.findById(ratePlanId)
                .map(ratePlan -> ratePlanMapper.updateRatePlanDTO(ratePlan, new RatePlanDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final RatePlanDTO ratePlanDTO) {
        final RatePlan ratePlan = new RatePlan();
        ratePlanMapper.updateRatePlan(ratePlanDTO, ratePlan, productRepository, currenciesRepository);
        return ratePlanRepository.save(ratePlan).getRatePlanId();
    }

    @Override
    public void update(final Integer ratePlanId, final RatePlanDTO ratePlanDTO) {
        final RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
                .orElseThrow(NotFoundException::new);
        ratePlanMapper.updateRatePlan(ratePlanDTO, ratePlan, productRepository, currenciesRepository);
        ratePlanRepository.save(ratePlan);
    }

    @Override
    public void delete(final Integer ratePlanId) {
        ratePlanRepository.deleteById(ratePlanId);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final Integer ratePlanId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
                .orElseThrow(NotFoundException::new);
        final RatePlanUsageBased ratePlanRatePlanUsageBased = ratePlanUsageBasedRepository.findFirstByRatePlan(ratePlan);
        if (ratePlanRatePlanUsageBased != null) {
            referencedWarning.setKey("ratePlan.ratePlanUsageBased.ratePlan.referenced");
            referencedWarning.addParam(ratePlanRatePlanUsageBased.getRatePlanUsageRateId());
            return referencedWarning;
        }
        final RatePlanTieredRate ratePlanRatePlanTieredRate = ratePlanTieredRateRepository.findFirstByRatePlan(ratePlan);
        if (ratePlanRatePlanTieredRate != null) {
            referencedWarning.setKey("ratePlan.ratePlanTieredRate.ratePlan.referenced");
            referencedWarning.addParam(ratePlanRatePlanTieredRate.getRatePlanTieredRateId());
            return referencedWarning;
        }
        final RatePlanFlatRate ratePlanRatePlanFlatRate = ratePlanFlatRateRepository.findFirstByRatePlan(ratePlan);
        if (ratePlanRatePlanFlatRate != null) {
            referencedWarning.setKey("ratePlan.ratePlanFlatRate.ratePlan.referenced");
            referencedWarning.addParam(ratePlanRatePlanFlatRate.getRatePlanFlatRateId());
            return referencedWarning;
        }
        final RatePlanSubscriptionRate ratePlanRatePlanSubscriptionRate = ratePlanSubscriptionRateRepository.findFirstByRatePlan(ratePlan);
        if (ratePlanRatePlanSubscriptionRate != null) {
            referencedWarning.setKey("ratePlan.ratePlanSubscriptionRate.ratePlan.referenced");
            referencedWarning.addParam(ratePlanRatePlanSubscriptionRate.getRatePlanSubscriptionRateId());
            return referencedWarning;
        }
        final RatePlanFreemiumRate ratePlanRatePlanFreemiumRate = ratePlanFreemiumRateRepository.findFirstByRatePlan(ratePlan);
        if (ratePlanRatePlanFreemiumRate != null) {
            referencedWarning.setKey("ratePlan.ratePlanFreemiumRate.ratePlan.referenced");
            referencedWarning.addParam(ratePlanRatePlanFreemiumRate.getRatePlanFreemiumRateId());
            return referencedWarning;
        }
        return null;
    }

}
