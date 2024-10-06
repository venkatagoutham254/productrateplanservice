package aforo.productrateplanservie.rate_plan_subscription_rate.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import aforo.productrateplanservie.model.SimpleValue;
import aforo.productrateplanservie.rate_plan.rest.RatePlanResource;
import aforo.productrateplanservie.rate_plan_subscription_rate.model.RatePlanSubscriptionRateDTO;
import java.util.UUID;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class RatePlanSubscriptionRateAssembler implements SimpleRepresentationModelAssembler<RatePlanSubscriptionRateDTO> {

    @Override
    public void addLinks(final EntityModel<RatePlanSubscriptionRateDTO> entityModel) {
        entityModel.add(linkTo(methodOn(RatePlanSubscriptionRateResource.class).getRatePlanSubscriptionRate(entityModel.getContent().getRatePlanSubscriptionRateId())).withSelfRel());
        entityModel.add(linkTo(methodOn(RatePlanSubscriptionRateResource.class).getAllRatePlanSubscriptionRates(null, null)).withRel(IanaLinkRelations.COLLECTION));
        entityModel.add(linkTo(methodOn(RatePlanResource.class).getRatePlan(entityModel.getContent().getRatePlan())).withRel("ratePlan"));
    }

    @Override
    public void addLinks(
            final CollectionModel<EntityModel<RatePlanSubscriptionRateDTO>> collectionModel) {
        collectionModel.add(linkTo(methodOn(RatePlanSubscriptionRateResource.class).getAllRatePlanSubscriptionRates(null, null)).withSelfRel());
    }

    public EntityModel<SimpleValue<UUID>> toSimpleModel(final UUID ratePlanSubscriptionRateId) {
        final EntityModel<SimpleValue<UUID>> simpleModel = SimpleValue.entityModelOf(ratePlanSubscriptionRateId);
        simpleModel.add(linkTo(methodOn(RatePlanSubscriptionRateResource.class).getRatePlanSubscriptionRate(ratePlanSubscriptionRateId)).withSelfRel());
        return simpleModel;
    }

}
