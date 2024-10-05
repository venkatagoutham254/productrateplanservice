package aforo.rateplanservie.repos;

import aforo.rateplanservie.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAllByProductId(Integer productId, Pageable pageable);

}
