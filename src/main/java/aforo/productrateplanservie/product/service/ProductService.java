package aforo.productrateplanservie.product.service;

import aforo.productrateplanservie.product.model.ProductDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    Page<ProductDTO> findAll(String filter, Pageable pageable);

    ProductDTO get(UUID productId);

    UUID create(ProductDTO productDTO);

    void update(UUID productId, ProductDTO productDTO);

    void delete(UUID productId);

    ReferencedWarning getReferencedWarning(UUID productId);

}
