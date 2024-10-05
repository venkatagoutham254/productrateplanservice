package aforo.rateplanservie.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import aforo.rateplanservie.model.RatePlanTieredRateDTO;
import aforo.rateplanservie.model.SimpleValue;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class RatePlanTieredRateAssembler implements SimpleRepresentationModelAssembler<RatePlanTieredRateDTO> {

    @Override
    public void addLinks(final EntityModel<RatePlanTieredRateDTO> entityModel) {
        entityModel.add(linkTo(methodOn(RatePlanTieredRateResource.class).getRatePlanTieredRate(entityModel.getContent().getRatePlanTieredRateId())).withSelfRel());
        entityModel.add(linkTo(methodOn(RatePlanTieredRateResource.class).getAllRatePlanTieredRates(null, null)).withRel(IanaLinkRelations.COLLECTION));
        entityModel.add(linkTo(methodOn(RatePlanResource.class).getRatePlan(entityModel.getContent().getRatePlan())).withRel("ratePlan"));
    }

    @Override
    public void addLinks(
            final CollectionModel<EntityModel<RatePlanTieredRateDTO>> collectionModel) {
        collectionModel.add(linkTo(methodOn(RatePlanTieredRateResource.class).getAllRatePlanTieredRates(null, null)).withSelfRel());
    }

    public EntityModel<SimpleValue<Integer>> toSimpleModel(final Integer ratePlanTieredRateId) {
        final EntityModel<SimpleValue<Integer>> simpleModel = SimpleValue.entityModelOf(ratePlanTieredRateId);
        simpleModel.add(linkTo(methodOn(RatePlanTieredRateResource.class).getRatePlanTieredRate(ratePlanTieredRateId)).withSelfRel());
        return simpleModel;
    }

}
