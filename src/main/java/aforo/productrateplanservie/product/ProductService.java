package aforo.productrateplanservie.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import aforo.productrateplanservie.exception.ReferencedWarning;
public interface ProductService {
	ProductDTO get(Long productId);
	// Long create(CreateProductRequest createProductRequest);
	Long create(CreateProductRequest createProductRequest, MultipartFile file, MultipartFile documentFile);
	void update(Long productId,CreateProductRequest createProductRequest);
	void delete(Long productId);
//	ReferencedWarning getReferencedWarning(Long productId);
	Page<ProductDTO> findAll(String filter, Long customerId, Long organizationId, Long divisionId, Pageable pageable);
	long getProductCount();
}
