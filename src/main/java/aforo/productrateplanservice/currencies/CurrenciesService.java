package aforo.productrateplanservice.currencies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import aforo.productrateplanservice.exception.ReferencedWarning;


public interface CurrenciesService {

    Page<CurrenciesDTO> findAll(String filter, Pageable pageable);

    CurrenciesDTO get(Long currencyId);

    Long create(CreateCurrenciesRequest createCurrenciesRequest);

    void update(Long currencyId, CreateCurrenciesRequest createCurrenciesRequest);

    void delete(Long currencyId);

    ReferencedWarning getReferencedWarning(Long currencyId);

    long getCurrencyCount();
}
