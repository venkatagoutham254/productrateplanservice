package aforo.productrateplanservie.product.repos;

import aforo.productrateplanservie.product.domain.Product;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findAllByProductId(UUID productId, Pageable pageable);

}
