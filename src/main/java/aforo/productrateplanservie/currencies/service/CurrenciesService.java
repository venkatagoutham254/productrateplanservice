package aforo.productrateplanservie.currencies.service;

import aforo.productrateplanservie.currencies.model.CurrenciesDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CurrenciesService {

    Page<CurrenciesDTO> findAll(String filter, Pageable pageable);

    CurrenciesDTO get(Long currencyId);

    Long create(CurrenciesDTO currenciesDTO);

    void update(Long currencyId, CurrenciesDTO currenciesDTO);

    void delete(Long currencyId);

    ReferencedWarning getReferencedWarning(Long currencyId);

}
