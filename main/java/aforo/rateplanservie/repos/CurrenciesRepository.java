package aforo.rateplanservie.repos;

import aforo.rateplanservie.domain.Currencies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurrenciesRepository extends JpaRepository<Currencies, Integer> {

    Page<Currencies> findAllByCurrencyId(Integer currencyId, Pageable pageable);

}
