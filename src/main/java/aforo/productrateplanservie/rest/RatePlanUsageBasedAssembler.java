package aforo.productrateplanservie.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import aforo.productrateplanservie.model.RatePlanUsageBasedDTO;
import aforo.productrateplanservie.model.SimpleValue;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class RatePlanUsageBasedAssembler implements SimpleRepresentationModelAssembler<RatePlanUsageBasedDTO> {

    @Override
    public void addLinks(final EntityModel<RatePlanUsageBasedDTO> entityModel) {
        entityModel.add(linkTo(methodOn(RatePlanUsageBasedResource.class).getRatePlanUsageBased(entityModel.getContent().getRatePlanUsageRateId())).withSelfRel());
        entityModel.add(linkTo(methodOn(RatePlanUsageBasedResource.class).getAllRatePlanUsageBaseds(null, null)).withRel(IanaLinkRelations.COLLECTION));
        entityModel.add(linkTo(methodOn(RatePlanResource.class).getRatePlan(entityModel.getContent().getRatePlan())).withRel("ratePlan"));
    }

    @Override
    public void addLinks(
            final CollectionModel<EntityModel<RatePlanUsageBasedDTO>> collectionModel) {
        collectionModel.add(linkTo(methodOn(RatePlanUsageBasedResource.class).getAllRatePlanUsageBaseds(null, null)).withSelfRel());
    }

    public EntityModel<SimpleValue<Integer>> toSimpleModel(final Integer ratePlanUsageRateId) {
        final EntityModel<SimpleValue<Integer>> simpleModel = SimpleValue.entityModelOf(ratePlanUsageRateId);
        simpleModel.add(linkTo(methodOn(RatePlanUsageBasedResource.class).getRatePlanUsageBased(ratePlanUsageRateId)).withSelfRel());
        return simpleModel;
    }

}
