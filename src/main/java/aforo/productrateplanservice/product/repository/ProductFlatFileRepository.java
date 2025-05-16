package aforo.productrateplanservice.product.repository;

import aforo.productrateplanservice.product.entity.ProductFlatFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductFlatFileRepository extends JpaRepository<ProductFlatFile, Long> {
}
