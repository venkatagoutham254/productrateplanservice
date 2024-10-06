package aforo.productrateplanservie.currencies;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import aforo.productrateplanservie.model.SimpleValue;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class CurrenciesAssembler implements SimpleRepresentationModelAssembler<CurrenciesDTO> {

    @Override
    public void addLinks(final EntityModel<CurrenciesDTO> entityModel) {
        entityModel.add(linkTo(methodOn(CurrenciesResource.class).getCurrencies(entityModel.getContent().getCurrencyId())).withSelfRel());
        entityModel.add(linkTo(methodOn(CurrenciesResource.class).getAllCurrenciess(null, null)).withRel(IanaLinkRelations.COLLECTION));
    }

    @Override
    public void addLinks(final CollectionModel<EntityModel<CurrenciesDTO>> collectionModel) {
        collectionModel.add(linkTo(methodOn(CurrenciesResource.class).getAllCurrenciess(null, null)).withSelfRel());
    }

    public EntityModel<SimpleValue<Long>> toSimpleModel(final Long currencyId) {
        final EntityModel<SimpleValue<Long>> simpleModel = SimpleValue.entityModelOf(currencyId);
        simpleModel.add(linkTo(methodOn(CurrenciesResource.class).getCurrencies(currencyId)).withSelfRel());
        return simpleModel;
    }

}
