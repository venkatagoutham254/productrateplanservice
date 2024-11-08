package aforo.productrateplanservie.currencies;

import aforo.productrateplanservie.exception.ValidationException;
import aforo.productrateplanservie.product.CreateProductRequest;
import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.exception.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;


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
    public Long create(final CreateCurrenciesRequest createCurrenciesRequest) {
        validateCreateRequest(createCurrenciesRequest);
        final Currencies currencies = new Currencies();
        CurrenciesDTO currenciesDTO = currenciesMapper.createCurrenciesRequestToCurrenciesDTO(createCurrenciesRequest);
        currenciesMapper.updateCurrencies(currenciesDTO, currencies);

        return currenciesRepository.save(currencies).getCurrencyId();
    }

    private void validateCreateRequest(CreateCurrenciesRequest createCurrenciesRequest) {
        if (createCurrenciesRequest.getCurrencyName().trim().isEmpty()) {
            throw new ValidationException("CurrencyName is required");
        }

        if (createCurrenciesRequest.getCurrencyCode().trim().isEmpty()) {
            throw new ValidationException("CurrencyCode is required");
        }
    }

    @Override
    public void update(final Long currencyId, final CreateCurrenciesRequest createCurrenciesRequest) {
        final Currencies currencies = currenciesRepository.findById(currencyId)
                .orElseThrow(NotFoundException::new);
        CurrenciesDTO currenciesDTO = currenciesMapper.updateCurrenciesDTO(currencies, new CurrenciesDTO());

        boolean isModified = false;

        if (createCurrenciesRequest.getCurrencyCode() != null && !createCurrenciesRequest.getCurrencyCode().trim().isEmpty() &&
                !Objects.equals(currencies.getCurrencyCode(), createCurrenciesRequest.getCurrencyCode())) {
            currenciesDTO.setCurrencyCode(createCurrenciesRequest.getCurrencyCode());
            isModified = true;
        }

        if (createCurrenciesRequest.getCurrencyName() != null && !createCurrenciesRequest.getCurrencyName().trim().isEmpty() &&
                !Objects.equals(currencies.getCurrencyName(), createCurrenciesRequest.getCurrencyName())) {
            currenciesDTO.setCurrencyName(createCurrenciesRequest.getCurrencyName());
            isModified = true;
        }

        if (createCurrenciesRequest.getIsActive() != null &&
                !Objects.equals(currencies.getIsActive(), createCurrenciesRequest.getIsActive())) {
            currenciesDTO.setIsActive(createCurrenciesRequest.getIsActive());
            isModified = true;
        }
        if (isModified) {
            currenciesMapper.updateCurrencies(currenciesDTO, currencies);
            currenciesRepository.save(currencies);
        }
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
