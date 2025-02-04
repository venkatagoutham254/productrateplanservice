package aforo.productrateplanservie.product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
@Mapper(
		componentModel = MappingConstants.ComponentModel.SPRING,
		unmappedTargetPolicy = ReportingPolicy.IGNORE
		)
@Component
public interface ProductMapper {

	ProductDTO createProductRequestToProductDTO (CreateProductRequest createProductRequest);
	ProductDTO updateProductRequestToProductDTO (CreateProductRequest createProductRequest);
	ProductDTO updateProductDTO(Product product, @MappingTarget ProductDTO productDTO);
	@Mapping(target = "productId", ignore = true)
	@Mapping(target = "customerId", source = "productDTO.customerId")  // Explicit mapping for customerId
	@Mapping(target = "organizationId", source = "productDTO.organizationId")  // Explicit mapping for organizationId
	@Mapping(target = "divisionId", source = "productDTO.divisionId")  // Explicit mapping for divisionId
	Product updateProduct(ProductDTO productDTO, @MappingTarget Product product);
}
