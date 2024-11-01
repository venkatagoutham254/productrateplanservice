package aforo.productrateplanservie.product;

import aforo.productrateplanservie.rate_plan.CreateRatePlanRequest;
import aforo.productrateplanservie.rate_plan.RatePlanDTO;
import aforo.productrateplanservie.util.enums.Status;
import jakarta.validation.constraints.NotBlank;

public class CreateProductMapper {
    public static ProductDTO toProductDTO(CreateProductRequest request) {
        ProductDTO dto = new ProductDTO();
        dto.setProductName(request.getProductName());
        dto.setProductDescription(request.getProductDescription());
        dto.setStatus(request.getStatus());
        dto.setProducerId(request.getProducerId());
        dto.setOrganizationId(request.getOrganizationId());
        dto.setDivisionId(request.getDivisionId());

        // Convert LocalDateTime to LocalDate

        return dto;
    }

public static CreateProductRequest toCreateProductRequest(ProductDTO dto) {
    CreateProductRequest request = new CreateProductRequest();
    request.setProductName(dto.getProductName());
    request.setProductDescription(dto.getProductDescription());
    request.setStatus(dto.getStatus());
    request.setProducerId(dto.getProducerId());
    request.setOrganizationId(dto.getOrganizationId());
    request.setDivisionId(dto.getDivisionId());

    return request;
}
}
