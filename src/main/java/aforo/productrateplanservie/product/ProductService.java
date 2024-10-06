package aforo.productrateplanservie.product;

import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    Page<ProductDTO> findAll(String filter, Pageable pageable);

    ProductDTO get(Long productId);

    Long create(ProductDTO productDTO);

    void update(Long productId, ProductDTO productDTO);

    void delete(Long productId);

    ReferencedWarning getReferencedWarning(Long productId);

}
