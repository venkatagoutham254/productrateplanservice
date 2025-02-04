package aforo.productrateplanservie.product;

import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.exception.ValidationException;
import aforo.productrateplanservie.product.CreateProductRequest;
import aforo.productrateplanservie.product.CustomerClientServiceImpl;
import aforo.productrateplanservie.product.Product;
import aforo.productrateplanservie.product.ProductDTO;
import aforo.productrateplanservie.product.ProductMapper;
import aforo.productrateplanservie.product.ProductRepository;
import aforo.productrateplanservie.product.ProductServiceImpl;
import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.exception.ReferencedWarning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private ProductMapper productMapper;

	@Mock
	private RatePlanRepository ratePlanRepository;

	@Mock
	private CustomerClientServiceImpl customerClientServiceImpl;

	@InjectMocks
	private ProductServiceImpl productService;

	private Product product;
	private ProductDTO productDTO;
	private CreateProductRequest createProductRequest;
	private static final Long PRODUCT_ID = 1L;

	@BeforeEach
	void setUp() {
		product = new Product();
		product.setProductId(PRODUCT_ID);
		product.setProductName("Test Product");

		productDTO = new ProductDTO();
		productDTO.setProductId(PRODUCT_ID);
		productDTO.setProductName("Test Product");

		createProductRequest = new CreateProductRequest();
		createProductRequest.setProductName("Test Product");
	}

	@Test
	void findAll_WithoutFilter_ShouldReturnPageOfProductDTO() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Product> productPage = new PageImpl<>(Arrays.asList(product));
		when(productRepository.findAll(pageable)).thenReturn(productPage);
		when(productMapper.updateProductDTO(any(), any())).thenReturn(productDTO);

		Page<ProductDTO> result = productService.findAll(null, null, null, null, pageable);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals(productDTO, result.getContent().get(0));
		verify(productRepository).findAll(pageable);
	}

	@Test
	void get_WithValidId_ShouldReturnProductDTO() {
		when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
		when(productMapper.updateProductDTO(any(), any())).thenReturn(productDTO);

		ProductDTO result = productService.get(PRODUCT_ID);

		assertNotNull(result);
		assertEquals(productDTO, result);
		verify(productRepository).findById(PRODUCT_ID);
	}

	@Test
	void get_WithInvalidId_ShouldThrowNotFoundException() {
		when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> productService.get(PRODUCT_ID));
		verify(productRepository).findById(PRODUCT_ID);
	}

	@Test
	void create_WithValidData_ShouldReturnNewProductId() {
		when(productMapper.createProductRequestToProductDTO(createProductRequest)).thenReturn(productDTO);
		when(productRepository.save(any(Product.class))).thenReturn(product);

		Long result = productService.create(createProductRequest);

		assertEquals(PRODUCT_ID, result);
		verify(productRepository).save(any(Product.class));
	}

	@Test
	void create_WithInvalidData_ShouldThrowValidationException() {
		createProductRequest.setProductName(" ");
		assertThrows(ValidationException.class, () -> productService.create(createProductRequest));
	}

	@Test
	void update_WithValidData_ShouldUpdateProduct() {
		when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
		when(productMapper.updateProductDTO(any(), any())).thenReturn(productDTO);

		createProductRequest.setProductName("Updated Product");

		productService.update(PRODUCT_ID, createProductRequest);

		verify(productRepository).findById(PRODUCT_ID);
		verify(productRepository).save(any(Product.class));
	}

	@Test
	void update_WithInvalidId_ShouldThrowNotFoundException() {
		when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> productService.update(PRODUCT_ID, createProductRequest));
		verify(productRepository).findById(PRODUCT_ID);
	}

	@Test
	void delete_WithValidId_ShouldDeleteProduct() {
		when(productRepository.existsById(PRODUCT_ID)).thenReturn(true);

		productService.delete(PRODUCT_ID);

		verify(productRepository).existsById(PRODUCT_ID);
		verify(productRepository).deleteById(PRODUCT_ID);
	}

	@Test
	void delete_WithInvalidId_ShouldThrowNotFoundException() {
		when(productRepository.existsById(PRODUCT_ID)).thenReturn(false);

		assertThrows(NotFoundException.class, () -> productService.delete(PRODUCT_ID));
		verify(productRepository).existsById(PRODUCT_ID);
	}

}