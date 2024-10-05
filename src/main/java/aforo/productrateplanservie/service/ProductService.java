package aforo.productrateplanservie.service;

import aforo.productrateplanservie.model.ProductDTO;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    Page<ProductDTO> findAll(String filter, Pageable pageable);

    ProductDTO get(Integer productId);

    Integer create(ProductDTO productDTO);

    void update(Integer productId, ProductDTO productDTO);

    void delete(Integer productId);

    ReferencedWarning getReferencedWarning(Integer productId);

}
