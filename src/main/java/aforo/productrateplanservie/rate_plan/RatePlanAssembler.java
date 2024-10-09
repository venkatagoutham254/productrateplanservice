package aforo.productrateplanservie.rate_plan;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import aforo.productrateplanservie.currencies.CurrenciesResource;
import aforo.productrateplanservie.model.SimpleValue;
import aforo.productrateplanservie.product.ProductResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class RatePlanAssembler implements SimpleRepresentationModelAssembler<RatePlanDTO> {

    @Override
    public void addLinks(final EntityModel<RatePlanDTO> entityModel) {
        entityModel.add(linkTo(methodOn(RatePlanResource.class).getRatePlan(entityModel.getContent().getRatePlanId())).withSelfRel());
        entityModel.add(linkTo(methodOn(RatePlanResource.class).getAllRatePlans(null, null)).withRel(IanaLinkRelations.COLLECTION));
        entityModel.add(linkTo(methodOn(ProductResource.class).getProduct(entityModel.getContent().getProductId())).withRel("product"));
        entityModel.add(linkTo(methodOn(CurrenciesResource.class).getCurrencies(entityModel.getContent().getCurrencyId())).withRel("currency"));
    }

    @Override
    public void addLinks(final CollectionModel<EntityModel<RatePlanDTO>> collectionModel) {
        collectionModel.add(linkTo(methodOn(RatePlanResource.class).getAllRatePlans(null, null)).withSelfRel());
    }

    public EntityModel<SimpleValue<Long>> toSimpleModel(final Long ratePlanId) {
        final EntityModel<SimpleValue<Long>> simpleModel = SimpleValue.entityModelOf(ratePlanId);
        simpleModel.add(linkTo(methodOn(RatePlanResource.class).getRatePlan(ratePlanId)).withSelfRel());
        return simpleModel;
    }

}
