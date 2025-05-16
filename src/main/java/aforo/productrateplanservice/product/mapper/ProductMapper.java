package aforo.productrateplanservice.product.mapper;

import org.mapstruct.Mapper;
import aforo.productrateplanservice.product.dto.ProductDTO;
import aforo.productrateplanservice.product.entity.Product;
import aforo.productrateplanservice.product.request.CreateProductRequest;


@Mapper
public interface ProductMapper {

    Product toEntity(CreateProductRequest request);
    
    ProductDTO toDTO(Product product);
}
