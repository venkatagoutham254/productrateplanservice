package aforo.productrateplanservie.currencies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurrenciesRepository extends JpaRepository<Currencies, Long> {

    Page<Currencies> findAllByCurrencyId(Long currencyId, Pageable pageable);

}
