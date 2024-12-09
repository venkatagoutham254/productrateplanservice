package aforo.productrateplanservie.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import aforo.productrateplanservie.exception.ReferencedWarning;
public interface ProductService {
	ProductDTO get(Long productId);
	Long create(CreateProductRequest createProductRequest);
	void update(Long productId,CreateProductRequest createProductRequest);
	void delete(Long productId);
//	ReferencedWarning getReferencedWarning(Long productId);
	Page<ProductDTO> findAll(String filter, Long producerId, Long organizationId, Long divisionId, Pageable pageable);
	long getProductCount();
}

