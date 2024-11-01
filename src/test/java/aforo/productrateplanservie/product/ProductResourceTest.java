package aforo.productrateplanservie.product;
import aforo.productrateplanservie.model.SimpleValue;
import aforo.productrateplanservie.util.ReferencedException;
import aforo.productrateplanservie.util.ReferencedWarning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
class ProductResourceTest {
	@Mock
	private ProductService productService;
	@Mock
	private ProductAssembler productAssembler;
	@Mock
	private PagedResourcesAssembler<ProductDTO> pagedResourcesAssembler;
	@InjectMocks
	private ProductResource productResource;
	private ProductDTO productDTO;
	private ProductDTO updatedProductDTO;
	private Long productId = 1L;
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		// Initialize mock data
		productDTO = new ProductDTO(); // Populate as necessary
		updatedProductDTO = new ProductDTO(); // Populate as necessary
	}
	@Test
	void testGetProduct() {
		when(productService.get(anyLong())).thenReturn(productDTO);
		when(productAssembler.toModel(productDTO)).thenReturn(EntityModel.of(productDTO));
		ResponseEntity<EntityModel<ProductDTO>> response = productResource.getProduct(productId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(EntityModel.of(productDTO), response.getBody());
	}
//	@Test
//	void testCreateProduct() {
//		when(productService.create(any(ProductDTO.class))).thenReturn(productId);
//		when(productAssembler.toSimpleModel(productId)).thenReturn(EntityModel.of(new SimpleValue<>(productId)));
//		ResponseEntity<EntityModel<SimpleValue<Long>>> response = productResource.createProduct(productDTO);
//		assertEquals(HttpStatus.CREATED, response.getStatusCode());
//		assertNotNull(response.getBody());
//		assertEquals(productId, response.getBody().getContent().getValue());
//	}
//	@Test
//	void testUpdateProduct() {
//		doNothing().when(productService).update(anyLong(), any(ProductDTO.class));
//		when(productAssembler.toSimpleModel(productId)).thenReturn(EntityModel.of(new SimpleValue<>(productId)));
//		ResponseEntity<EntityModel<SimpleValue<Long>>> response = productResource.updateProduct(productId, updatedProductDTO);
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertNotNull(response.getBody());
//		assertEquals(productId, response.getBody().getContent().getValue());
//	}
	@Test
	void testDeleteProduct() {
		@SuppressWarnings("unused")
		ReferencedWarning warning = new ReferencedWarning(); // Initialize as needed
		when(productService.getReferencedWarning(productId)).thenReturn(null);
		doNothing().when(productService).delete(anyLong());
		ResponseEntity<Void> response = productResource.deleteProduct(productId);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(productService, times(1)).delete(productId);
	}
	@Test
	void testDeleteProduct_withReferencedWarning() {
		ReferencedWarning warning = new ReferencedWarning(); // Initialize as needed
		when(productService.getReferencedWarning(productId)).thenReturn(warning);
		ReferencedException thrown = assertThrows(ReferencedException.class, () -> productResource.deleteProduct(productId));
		assertNotNull(thrown);
		assertEquals(warning, thrown.getReferencedWarning());
		verify(productService, times(0)).delete(productId);
	}
}
