package aforo.productrateplanservie.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import aforo.productrateplanservie.model.RatePlanFreemiumRateDTO;
import aforo.productrateplanservie.model.SimpleValue;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class RatePlanFreemiumRateAssembler implements SimpleRepresentationModelAssembler<RatePlanFreemiumRateDTO> {

    @Override
    public void addLinks(final EntityModel<RatePlanFreemiumRateDTO> entityModel) {
        entityModel.add(linkTo(methodOn(RatePlanFreemiumRateResource.class).getRatePlanFreemiumRate(entityModel.getContent().getRatePlanFreemiumRateId())).withSelfRel());
        entityModel.add(linkTo(methodOn(RatePlanFreemiumRateResource.class).getAllRatePlanFreemiumRates(null, null)).withRel(IanaLinkRelations.COLLECTION));
        entityModel.add(linkTo(methodOn(RatePlanResource.class).getRatePlan(entityModel.getContent().getRatePlan())).withRel("ratePlan"));
    }

    @Override
    public void addLinks(
            final CollectionModel<EntityModel<RatePlanFreemiumRateDTO>> collectionModel) {
        collectionModel.add(linkTo(methodOn(RatePlanFreemiumRateResource.class).getAllRatePlanFreemiumRates(null, null)).withSelfRel());
    }

    public EntityModel<SimpleValue<Integer>> toSimpleModel(final Integer ratePlanFreemiumRateId) {
        final EntityModel<SimpleValue<Integer>> simpleModel = SimpleValue.entityModelOf(ratePlanFreemiumRateId);
        simpleModel.add(linkTo(methodOn(RatePlanFreemiumRateResource.class).getRatePlanFreemiumRate(ratePlanFreemiumRateId)).withSelfRel());
        return simpleModel;
    }

}
