package aforo.productrateplanservie.currencies;

import aforo.productrateplanservie.exception.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CurrenciesService {

    Page<CurrenciesDTO> findAll(String filter, Pageable pageable);

    CurrenciesDTO get(Long currencyId);

    Long create(CreateCurrenciesRequest createCurrenciesRequest);

    void update(Long currencyId, CreateCurrenciesRequest createCurrenciesRequest);

    void delete(Long currencyId);

    ReferencedWarning getReferencedWarning(Long currencyId);

    long getCurrencyCount();
}
