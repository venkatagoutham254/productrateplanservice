package aforo.productrateplanservie.product;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import aforo.productrateplanservie.model.SimpleValue;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class ProductAssembler implements SimpleRepresentationModelAssembler<ProductDTO> {

    @Override
    public void addLinks(final EntityModel<ProductDTO> entityModel) {
        entityModel.add(linkTo(methodOn(ProductResource.class).getProduct(entityModel.getContent().getProductId())).withSelfRel());
        entityModel.add(linkTo(methodOn(ProductResource.class).getAllProducts(null, null)).withRel(IanaLinkRelations.COLLECTION));
    }

    @Override
    public void addLinks(final CollectionModel<EntityModel<ProductDTO>> collectionModel) {
        collectionModel.add(linkTo(methodOn(ProductResource.class).getAllProducts(null, null)).withSelfRel());
    }

    public EntityModel<SimpleValue<Long>> toSimpleModel(final Long productId) {
        final EntityModel<SimpleValue<Long>> simpleModel = SimpleValue.entityModelOf(productId);
        simpleModel.add(linkTo(methodOn(ProductResource.class).getProduct(productId)).withSelfRel());
        return simpleModel;
    }

}
