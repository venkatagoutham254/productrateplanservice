package aforo.productrateplanservice.product.repository;


import aforo.productrateplanservice.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByInternalSkuCode(String internalSkuCode);
}
