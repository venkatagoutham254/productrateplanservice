package aforo.productrateplanservice.rate_plan;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import aforo.productrateplanservice.rate_plan.RatePlanDTO;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Component
public class RatePlanAssembler {

    public RatePlanDTO toDTO(RatePlan ratePlan) {
        return RatePlanDTO.builder()
                .ratePlanId(ratePlan.getRatePlanId())
                .productId(ratePlan.getProduct().getProductId())
                .productName(ratePlan.getProduct().getProductName())
                .ratePlanName(ratePlan.getRatePlanName())
                .description(ratePlan.getDescription())
                .ratePlanType(ratePlan.getRatePlanType())
                .status(ratePlan.getStatus())
                .createdAt(ratePlan.getCreatedAt())
                .build();
    }
}
