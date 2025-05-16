package aforo.productrateplanservice.currencies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrenciesRepository extends JpaRepository<Currencies, Long> {

    Page<Currencies> findAllByCurrencyId(Long currencyId, Pageable pageable);

}
