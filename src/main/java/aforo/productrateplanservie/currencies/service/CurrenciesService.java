package aforo.productrateplanservie.currencies.service;

import aforo.productrateplanservie.currencies.model.CurrenciesDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CurrenciesService {

    Page<CurrenciesDTO> findAll(String filter, Pageable pageable);

    CurrenciesDTO get(UUID currencyId);

    UUID create(CurrenciesDTO currenciesDTO);

    void update(UUID currencyId, CurrenciesDTO currenciesDTO);

    void delete(UUID currencyId);

    ReferencedWarning getReferencedWarning(UUID currencyId);

}
