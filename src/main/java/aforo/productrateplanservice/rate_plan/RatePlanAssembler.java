package aforo.productrateplanservice.rate_plan;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import aforo.productrateplanservice.currencies.CurrenciesResource;
import aforo.productrateplanservice.model.SimpleValue;
import aforo.productrateplanservice.product.resource.ProductResource;

@Component
public class RatePlanAssembler implements SimpleRepresentationModelAssembler<RatePlanDTO> {

	@Override
	public void addLinks(final EntityModel<RatePlanDTO> entityModel) {
		RatePlanDTO content = entityModel.getContent();
		if (content != null) {
			entityModel.add(linkTo(methodOn(RatePlanResource.class).getRatePlan(content.getRatePlanId()))
					.withSelfRel());
			entityModel.add(linkTo(methodOn(RatePlanResource.class).getAllRatePlans(null, null))
					.withRel(IanaLinkRelations.COLLECTION));
			entityModel.add(linkTo(methodOn(ProductResource.class).getProductById(content.getProductId()))
					.withRel("product"));
			entityModel.add(linkTo(methodOn(CurrenciesResource.class).getCurrencies(content.getCurrencyId()))
					.withRel("currency"));
		}
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
