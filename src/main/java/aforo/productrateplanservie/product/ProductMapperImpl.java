package aforo.productrateplanservie.product;

public class ProductMapperImpl implements ProductMapper{

	@Override
	public ProductDTO updateProductDTO(Product product, ProductDTO productDTO) {
		ProductDTO dto= productDTO;
		dto.setProductId(product.getProductId());
		dto.setProductName(product.getProductName());
		dto.setProductDescription(product.getProductDescription());
		dto.setStatus(product.getStatus());
		dto.setProducerId(product.getProducerId());
		dto.setOrganizationId(product.getOrganizationId());
		dto.setDivisionId(product.getDivisionId());
		return dto;
	}

	@Override
	public Product updateProduct(ProductDTO productDTO, Product product) {
		Product localProduct = product;
		localProduct.setProductId(productDTO.getProductId());
		localProduct.setProductName(productDTO.getProductName());
		localProduct.setProductDescription(productDTO.getProductDescription());
		localProduct.setStatus(productDTO.getStatus());
		localProduct.setProducerId(productDTO.getProducerId());
		localProduct.setOrganizationId(productDTO.getOrganizationId());
		localProduct.setDivisionId(productDTO.getDivisionId());
		
		return localProduct;
	}

}
