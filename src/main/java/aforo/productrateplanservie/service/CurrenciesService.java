package aforo.productrateplanservie.service;

import aforo.productrateplanservie.model.CurrenciesDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CurrenciesService {

    Page<CurrenciesDTO> findAll(String filter, Pageable pageable);

    CurrenciesDTO get(Integer currencyId);

    Integer create(CurrenciesDTO currenciesDTO);

    void update(Integer currencyId, CurrenciesDTO currenciesDTO);

    void delete(Integer currencyId);

    ReferencedWarning getReferencedWarning(Integer currencyId);

}
