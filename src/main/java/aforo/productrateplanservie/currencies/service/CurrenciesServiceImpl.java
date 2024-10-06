package aforo.productrateplanservie.currencies.service;

import aforo.productrateplanservie.currencies.domain.Currencies;
import aforo.productrateplanservie.currencies.model.CurrenciesDTO;
import aforo.productrateplanservie.currencies.repos.CurrenciesRepository;
import aforo.productrateplanservie.rate_plan.domain.RatePlan;
import aforo.productrateplanservie.rate_plan.repos.RatePlanRepository;
import aforo.productrateplanservie.util.NotFoundException;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CurrenciesServiceImpl implements CurrenciesService {

    private final CurrenciesRepository currenciesRepository;
    private final CurrenciesMapper currenciesMapper;
    private final RatePlanRepository ratePlanRepository;

    public CurrenciesServiceImpl(final CurrenciesRepository currenciesRepository,
            final CurrenciesMapper currenciesMapper, final RatePlanRepository ratePlanRepository) {
        this.currenciesRepository = currenciesRepository;
        this.currenciesMapper = currenciesMapper;
        this.ratePlanRepository = ratePlanRepository;
    }

    @Override
    public Page<CurrenciesDTO> findAll(final String filter, final Pageable pageable) {
        Page<Currencies> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = currenciesRepository.findAllByCurrencyId(longFilter, pageable);
        } else {
            page = currenciesRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(currencies -> currenciesMapper.updateCurrenciesDTO(currencies, new CurrenciesDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public CurrenciesDTO get(final Long currencyId) {
        return currenciesRepository.findById(currencyId)
                .map(currencies -> currenciesMapper.updateCurrenciesDTO(currencies, new CurrenciesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final CurrenciesDTO currenciesDTO) {
        final Currencies currencies = new Currencies();
        currenciesMapper.updateCurrencies(currenciesDTO, currencies);
        return currenciesRepository.save(currencies).getCurrencyId();
    }

    @Override
    public void update(final Long currencyId, final CurrenciesDTO currenciesDTO) {
        final Currencies currencies = currenciesRepository.findById(currencyId)
                .orElseThrow(NotFoundException::new);
        currenciesMapper.updateCurrencies(currenciesDTO, currencies);
        currenciesRepository.save(currencies);
    }

    @Override
    public void delete(final Long currencyId) {
        currenciesRepository.deleteById(currencyId);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final Long currencyId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Currencies currencies = currenciesRepository.findById(currencyId)
                .orElseThrow(NotFoundException::new);
        final RatePlan currencyRatePlan = ratePlanRepository.findFirstByCurrency(currencies);
        if (currencyRatePlan != null) {
            referencedWarning.setKey("currencies.ratePlan.currency.referenced");
            referencedWarning.addParam(currencyRatePlan.getRatePlanId());
            return referencedWarning;
        }
        return null;
    }

}
