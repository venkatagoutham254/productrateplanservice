package aforo.productrateplanservie.product.repos;

import aforo.productrateplanservie.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByProductId(Long productId, Pageable pageable);

}
