package aforo.rateplanservie.service;

import aforo.rateplanservie.domain.Product;
import aforo.rateplanservie.model.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductMapper {

    ProductDTO updateProductDTO(Product product, @MappingTarget ProductDTO productDTO);

    @Mapping(target = "productId", ignore = true)
    Product updateProduct(ProductDTO productDTO, @MappingTarget Product product);

}
