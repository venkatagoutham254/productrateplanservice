package aforo.productrateplanservie.rate_plan_flat_rate.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import aforo.productrateplanservie.model.SimpleValue;
import aforo.productrateplanservie.rate_plan.rest.RatePlanResource;
import aforo.productrateplanservie.rate_plan_flat_rate.model.RatePlanFlatRateDTO;
import java.util.UUID;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class RatePlanFlatRateAssembler implements SimpleRepresentationModelAssembler<RatePlanFlatRateDTO> {

    @Override
    public void addLinks(final EntityModel<RatePlanFlatRateDTO> entityModel) {
        entityModel.add(linkTo(methodOn(RatePlanFlatRateResource.class).getRatePlanFlatRate(entityModel.getContent().getRatePlanFlatRateId())).withSelfRel());
        entityModel.add(linkTo(methodOn(RatePlanFlatRateResource.class).getAllRatePlanFlatRates(null, null)).withRel(IanaLinkRelations.COLLECTION));
        entityModel.add(linkTo(methodOn(RatePlanResource.class).getRatePlan(entityModel.getContent().getRatePlan())).withRel("ratePlan"));
    }

    @Override
    public void addLinks(final CollectionModel<EntityModel<RatePlanFlatRateDTO>> collectionModel) {
        collectionModel.add(linkTo(methodOn(RatePlanFlatRateResource.class).getAllRatePlanFlatRates(null, null)).withSelfRel());
    }

    public EntityModel<SimpleValue<UUID>> toSimpleModel(final UUID ratePlanFlatRateId) {
        final EntityModel<SimpleValue<UUID>> simpleModel = SimpleValue.entityModelOf(ratePlanFlatRateId);
        simpleModel.add(linkTo(methodOn(RatePlanFlatRateResource.class).getRatePlanFlatRate(ratePlanFlatRateId)).withSelfRel());
        return simpleModel;
    }

}
