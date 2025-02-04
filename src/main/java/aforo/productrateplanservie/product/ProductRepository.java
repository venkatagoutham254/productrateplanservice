package aforo.productrateplanservie.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Page<Product> findAllByCustomerIdAndOrganizationIdAndDivisionId(
			Long customerId, Long organizationId, Long divisionId, Pageable pageable);
	Page<Product> findAllByCustomerId(Long customerId, Pageable pageable);
	Page<Product> findAllByProductId(Long productId, Pageable pageable);
	Page<Product> findAllByOrganizationId(Long organizationId, Pageable pageable);
	Page<Product> findAllByDivisionId(Long divisionId, Pageable pageable);
}
