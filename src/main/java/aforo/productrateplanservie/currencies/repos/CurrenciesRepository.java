package aforo.productrateplanservie.currencies.repos;

import aforo.productrateplanservie.currencies.domain.Currencies;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurrenciesRepository extends JpaRepository<Currencies, UUID> {

    Page<Currencies> findAllByCurrencyId(UUID currencyId, Pageable pageable);

}
