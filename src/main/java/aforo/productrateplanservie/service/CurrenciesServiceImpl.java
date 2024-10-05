package aforo.productrateplanservie.service;

import aforo.productrateplanservie.domain.Currencies;
import aforo.productrateplanservie.domain.RatePlan;
import aforo.productrateplanservie.model.CurrenciesDTO;
import aforo.productrateplanservie.repos.CurrenciesRepository;
import aforo.productrateplanservie.repos.RatePlanRepository;
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
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = currenciesRepository.findAllByCurrencyId(integerFilter, pageable);
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
    public CurrenciesDTO get(final Integer currencyId) {
        return currenciesRepository.findById(currencyId)
                .map(currencies -> currenciesMapper.updateCurrenciesDTO(currencies, new CurrenciesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final CurrenciesDTO currenciesDTO) {
        final Currencies currencies = new Currencies();
        currenciesMapper.updateCurrencies(currenciesDTO, currencies);
        return currenciesRepository.save(currencies).getCurrencyId();
    }

    @Override
    public void update(final Integer currencyId, final CurrenciesDTO currenciesDTO) {
        final Currencies currencies = currenciesRepository.findById(currencyId)
                .orElseThrow(NotFoundException::new);
        currenciesMapper.updateCurrencies(currenciesDTO, currencies);
        currenciesRepository.save(currencies);
    }

    @Override
    public void delete(final Integer currencyId) {
        currenciesRepository.deleteById(currencyId);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final Integer currencyId) {
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
