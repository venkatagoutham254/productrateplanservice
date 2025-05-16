package aforo.productrateplanservice.product.repository;

import aforo.productrateplanservice.product.entity.ProductAPI;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAPIRepository extends JpaRepository<ProductAPI, Long> {
}
