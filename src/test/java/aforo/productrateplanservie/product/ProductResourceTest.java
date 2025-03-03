package aforo.productrateplanservie.product;

import aforo.productrateplanservie.exception.ReferencedException;
import aforo.productrateplanservie.exception.ReferencedWarning;
import aforo.productrateplanservie.model.SimpleValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
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
    
    @Mock
    private MultipartFile mockFile;
    
    @Mock
    private MultipartFile mockDocumentFile;

    private ProductDTO productDTO;
    private ProductDTO updatedProductDTO;
    private Long productId = 1L;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize mock data
        productDTO = new ProductDTO(); // Populate as necessary
        updatedProductDTO = new ProductDTO(); // Populate as necessary
        objectMapper = new ObjectMapper();
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

    // Uncomment and update when needed
    // @Test
    // void testCreateProduct() throws JsonProcessingException {
    //     CreateProductRequest createProductRequest = new CreateProductRequest(); // Populate if needed
    //     String requestJson = objectMapper.writeValueAsString(createProductRequest);
    //     
    //     when(productService.create(any(CreateProductRequest.class), any(), any())).thenReturn(productId);
    //     when(productAssembler.toSimpleModel(productId)).thenReturn(EntityModel.of(new SimpleValue<>(productId)));
    //
    //     ResponseEntity<EntityModel<SimpleValue<Long>>> response = 
    //         productResource.createProduct(requestJson, mockFile, mockDocumentFile);
    //
    //     assertEquals(HttpStatus.CREATED, response.getStatusCode());
    //     assertNotNull(response.getBody());
    //     assertEquals(productId, response.getBody().getContent().getValue());
    // }

    @Test
    void testUpdateProduct() throws JsonProcessingException {
        CreateProductRequest updateProductRequest = new CreateProductRequest(); // Populate if needed
        String requestJson = objectMapper.writeValueAsString(updateProductRequest);
        
        when(productService.update(eq(productId), any(CreateProductRequest.class), 
                any(MultipartFile.class), any(MultipartFile.class))).thenReturn(productId);
        when(productAssembler.toSimpleModel(productId)).thenReturn(EntityModel.of(new SimpleValue<>(productId)));

        ResponseEntity<EntityModel<SimpleValue<Long>>> response = 
            productResource.updateProduct(productId, requestJson, mockFile, mockDocumentFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productId, response.getBody().getContent().getValue());
        verify(productService).update(eq(productId), any(CreateProductRequest.class), 
                eq(mockFile), eq(mockDocumentFile));
    }

    // Uncomment and update when needed
    // @Test
    // void testDeleteProduct() {
    //     when(productService.getReferencedWarning(productId)).thenReturn(null);
    //     doNothing().when(productService).delete(anyLong());
    //
    //     ResponseEntity<Void> response = productResource.deleteProduct(productId);
    //
    //     assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    //     verify(productService, times(1)).delete(productId);
    // }

    // @Test
    // void testDeleteProduct_withReferencedWarning() {
    //     // Create the ReferencedWarning object using the correct constructor
    //     ReferencedWarning warning = new ReferencedWarning(); // Adjust initialization if parameters are required
    //
    //     // Mock the productService to return the warning
    //     when(productService.getReferencedWarning(productId)).thenReturn(warning);
    //
    //     // Perform the test and assert that the ReferencedException is thrown
    //     ReferencedException thrown = assertThrows(ReferencedException.class, () -> productResource.deleteProduct(productId));
    //
    //     // Assert that the thrown exception contains the correct warning object
    //     assertNotNull(thrown);
    //     assertEquals(warning, thrown.getReferencedWarning());
    //
    //     // Verify that the delete method was never called
    //     verify(productService, times(0)).delete(productId);
    // }

    @Test
    void testGetAllProducts() {
        // Mock PagedModel and ProductDTO
        PagedModel<EntityModel<ProductDTO>> mockPagedModel = PagedModel.of(
                Collections.singletonList(EntityModel.of(productDTO)),
                new PagedModel.PageMetadata(1, 0, 1)
        );

        when(productService.findAll(any(), any(), any(), any(), any())).thenReturn(null);
        when(pagedResourcesAssembler.toModel(any(), any(ProductAssembler.class))).thenReturn(mockPagedModel);

        ResponseEntity<PagedModel<EntityModel<ProductDTO>>> response = productResource.getAllProducts(
                "filter", 1L, 2L, 3L, null
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockPagedModel, response.getBody());
    }
    
    @Test
    void testGetProductCount() {
        // Mocking the service
        long expectedCount = 42;
        when(productService.getProductCount()).thenReturn(expectedCount);

        // Calling the method under test
        ResponseEntity<Long> response = productResource.getProductCount();

        // Verifying the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedCount, response.getBody());

        // Verifying the interaction
        verify(productService, times(1)).getProductCount();
    }
    
    @Test
    void getProductCount_ShouldReturnTotalProductCount() {
        // Arrange
        long expectedCount = 42L;
        when(productService.getProductCount()).thenReturn(expectedCount);

        // Act
        ResponseEntity<Long> response = productResource.getProductCount();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCount, response.getBody());
        verify(productService).getProductCount();
    }
}