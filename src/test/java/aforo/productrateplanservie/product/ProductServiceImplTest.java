package aforo.productrateplanservie.product;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.util.NotFoundException;
class ProductServiceImplTest {
	@Mock
	private ProductRepository productRepository;
	@Mock
	private ProductMapper productMapper;
	@Mock
	private RatePlanRepository ratePlanRepository;
	@Mock
	private RestTemplate restTemplate;
	@InjectMocks
	private ProductServiceImpl productService;
	private Product product;
	private ProductDTO productDTO;
	private Pageable pageable;
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		product = new Product();
		product.setProductId(1L);
		productDTO = new ProductDTO();
		pageable = PageRequest.of(0, 10);
	}
	@Test
	void testFindAll_withProducerOrganizationDivision() {
		Page<Product> products = new PageImpl<>(List.of(product));
		when(productRepository.findAllByProducerIdAndOrganizationIdAndDivisionId(1L, 2L, 3L, pageable)).thenReturn(products);
		when(productMapper.updateProductDTO(any(Product.class), any(ProductDTO.class))).thenReturn(productDTO);
		Page<ProductDTO> result = productService.findAll(null, 1L, 2L, 3L, pageable);

		assertEquals(1, result.getTotalElements());
		verify(productRepository, times(1)).findAllByProducerIdAndOrganizationIdAndDivisionId(1L, 2L, 3L, pageable);
	}
	@Test
	void testFindAll_withProducerOnly() {
		Page<Product> products = new PageImpl<>(List.of(product));
		when(productRepository.findAllByProducerId(1L, pageable)).thenReturn(products);
		when(productMapper.updateProductDTO(any(Product.class), any(ProductDTO.class))).thenReturn(productDTO);

		Page<ProductDTO> result = productService.findAll(null, 1L, null, null, pageable);

		assertEquals(1, result.getTotalElements());
		verify(productRepository, times(1)).findAllByProducerId(1L, pageable);
	}
	@Test
	void testGet_nonExistingProduct() {
		when(productRepository.findById(1L)).thenReturn(Optional.empty());

		NotFoundException thrown = assertThrows(NotFoundException.class, () -> productService.get(1L));

		assertEquals("Product not found with ID: 1", thrown.getMessage());
	}
//	@Test
//	void testCreate() {
//		when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(ResponseEntity.ok("ACTIVE"));
//		when(productRepository.save(any(Product.class))).thenReturn(product);
//		when(productMapper.updateProduct(any(ProductDTO.class), any(Product.class))).thenAnswer(invocation -> {
//			Product p = invocation.getArgument(1);
//			p.setProductId(1L);
//			return null;
//		});
//		Long result = productService.create(productDTO);
//		assertNotNull(result);
//		assertEquals(1L, result);
//		verify(productRepository, times(1)).save(any(Product.class));
//	}
//	@Test
//	void testCreate_invalidProducerId() {
//		when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(ResponseEntity.ok("INACTIVE"));
//
//		NotFoundException thrown = assertThrows(NotFoundException.class, () -> productService.create(productDTO));
//
//		assertEquals("producer ID is inactive or not found", thrown.getMessage());
//	}
//	@Test
//	void testUpdate_existingProduct() {
//		when(productRepository.findById(1L)).thenReturn(Optional.of(product));
//		when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(ResponseEntity.ok("ACTIVE"));
//		when(productMapper.updateProduct(any(ProductDTO.class), any(Product.class))).thenAnswer(invocation -> {
//			Product p = invocation.getArgument(1);
//			p.setProductId(1L);
//			return null;
//		});
//		productService.update(1L, productDTO);

//		verify(productRepository, times(1)).save(product);
	}
//	@Test
//	void testDelete_existingProduct() {
//		when(productRepository.existsById(1L)).thenReturn(true);
//		doNothing().when(productRepository).deleteById(1L);
//
//		productService.delete(1L);
//
//		verify(productRepository, times(1)).deleteById(1L);
//	}
//	@Test
//	void testDelete_nonExistingProduct() {
//		when(productRepository.existsById(1L)).thenReturn(false);
//
//		NotFoundException thrown = assertThrows(NotFoundException.class, () -> productService.delete(1L));
//
//		assertEquals("Product not found with ID: 1", thrown.getMessage());
//	}
//}
