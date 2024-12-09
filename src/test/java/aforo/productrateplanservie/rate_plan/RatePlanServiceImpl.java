package aforo.productrateplanservie.rate_plan;

import aforo.productrateplanservie.currencies.Currencies;
import aforo.productrateplanservie.currencies.CurrenciesRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.exception.ValidationException;
import aforo.productrateplanservie.product.Product;
import aforo.productrateplanservie.product.ProductRepository;
import aforo.productrateplanservie.rate_plan.CreateRatePlanRequest;
import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanDTO;
import aforo.productrateplanservie.rate_plan.RatePlanMapper;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan.RatePlanServiceImpl;
import aforo.productrateplanservie.rate_plan_flat_rate.RatePlanFlatRate;
import aforo.productrateplanservie.rate_plan_flat_rate.RatePlanFlatRateRepository;
import aforo.productrateplanservie.rate_plan_freemium_rate.RatePlanFreemiumRateRepository;
import aforo.productrateplanservie.rate_plan_subscription_rate.RatePlanSubscriptionRateRepository;
import aforo.productrateplanservie.rate_plan_tiered_rate.RatePlanTieredRateRepository;
import aforo.productrateplanservie.rate_plan_usage_based.RatePlanUsageBasedRepository;
import aforo.productrateplanservie.validation.RatePlanValidator;
import aforo.productrateplanservie.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatePlanServiceImplTest {

    @Mock
    private RatePlanRepository ratePlanRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CurrenciesRepository currenciesRepository;

    @Mock
    private RatePlanMapper ratePlanMapper;

    @Mock
    private RatePlanUsageBasedRepository ratePlanUsageBasedRepository;

    @Mock
    private RatePlanTieredRateRepository ratePlanTieredRateRepository;

    @Mock
    private RatePlanFlatRateRepository ratePlanFlatRateRepository;

    @Mock
    private RatePlanSubscriptionRateRepository ratePlanSubscriptionRateRepository;

    @Mock
    private RatePlanFreemiumRateRepository ratePlanFreemiumRateRepository;

    @Mock
    private RatePlanValidator ratePlanValidator;

    @InjectMocks
    private RatePlanServiceImpl ratePlanService;

    private RatePlan ratePlan;
    private RatePlanDTO ratePlanDTO;
    private CreateRatePlanRequest createRatePlanRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock data
        ratePlan = new RatePlan();
        ratePlanDTO = new RatePlanDTO();
        createRatePlanRequest = new CreateRatePlanRequest();
    }

    @Test
    void testFindAll() {
        Page<RatePlan> mockPage = new PageImpl<>(List.of(ratePlan));
        when(ratePlanRepository.findAll(any(Pageable.class))).thenReturn(mockPage);
        when(ratePlanMapper.updateRatePlanDTO(any(RatePlan.class), any(RatePlanDTO.class))).thenReturn(ratePlanDTO);

        Page<RatePlanDTO> result = ratePlanService.findAll(null, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(ratePlanRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testGetRatePlansByProductId() {
        Page<RatePlan> mockPage = new PageImpl<>(List.of(ratePlan));
        when(ratePlanRepository.findByProductId(anyLong(), any(Pageable.class))).thenReturn(mockPage);
        when(ratePlanMapper.updateRatePlanDTO(any(RatePlan.class), any(RatePlanDTO.class))).thenReturn(ratePlanDTO);

        Page<RatePlanDTO> result = ratePlanService.getRatePlansByProductId(1L, null, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(ratePlanRepository, times(1)).findByProductId(anyLong(), any(Pageable.class));
    }

    @Test
    void testGet() {
        when(ratePlanRepository.findById(anyLong())).thenReturn(Optional.of(ratePlan));
        when(ratePlanMapper.updateRatePlanDTO(any(RatePlan.class), any(RatePlanDTO.class))).thenReturn(ratePlanDTO);

        RatePlanDTO result = ratePlanService.get(1L);

        assertNotNull(result);
        verify(ratePlanRepository, times(1)).findById(anyLong());
    }

    @Test
    void testCreate() {
        // Mock the product repository to simulate an existing product
        Product mockProduct = new Product(); // Replace Product with your actual entity class
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(mockProduct));

        // Mock the validation result to indicate valid input
        ValidationResult validResult = mock(ValidationResult.class);
        when(validResult.isValid()).thenReturn(true);
        when(validResult.getErrors()).thenReturn(Collections.emptyList());
        when(ratePlanValidator.validate(any(CreateRatePlanRequest.class))).thenReturn(validResult);

        // Mock the ratePlanMapper to return a non-null RatePlanDTO
        RatePlanDTO mockRatePlanDTO = new RatePlanDTO();
        when(ratePlanMapper.createRatePlanRequestToRatePlanDTO(any(CreateRatePlanRequest.class))).thenReturn(mockRatePlanDTO);

        // Mock the ratePlanRepository to simulate saving a rate plan
        RatePlan savedRatePlan = new RatePlan();
        savedRatePlan.setRatePlanId(1L); // Set an ID to mimic a saved entity
        when(ratePlanRepository.save(any(RatePlan.class))).thenReturn(savedRatePlan);

        // Call the method under test
        Long result = ratePlanService.create(1L, createRatePlanRequest);

        // Perform assertions
        assertNotNull(result); // Ensure the result is not null
        assertEquals(1L, result); // Ensure the returned ID matches the saved rate plan
        verify(productRepository, times(1)).findById(anyLong()); // Verify product repository interaction
        verify(ratePlanValidator, times(1)).validate(any(CreateRatePlanRequest.class)); // Verify validation
        verify(ratePlanMapper, times(1)).createRatePlanRequestToRatePlanDTO(any(CreateRatePlanRequest.class)); // Verify mapper
        verify(ratePlanRepository, times(1)).save(any(RatePlan.class)); // Verify save interaction
    }

    @Test
    void testCreate_withInvalidValidation() {
        // Mock the product
        Product mockProduct = new Product(); // Replace with the correct class
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(mockProduct));

        // Mock the validation result
        ValidationResult invalidResult = mock(ValidationResult.class);
        when(invalidResult.isValid()).thenReturn(false);
        when(invalidResult.getErrors()).thenReturn(List.of("Invalid data"));
        when(ratePlanValidator.validate(any(CreateRatePlanRequest.class))).thenReturn(invalidResult);

        // Perform the test and assert a ValidationException is thrown
        ValidationException thrown = assertThrows(ValidationException.class, () -> ratePlanService.create(1L, createRatePlanRequest));

        // Perform assertions
        assertNotNull(thrown);
        assertEquals("Invalid rate plan request: Invalid data", thrown.getMessage());
        verify(productRepository, times(1)).findById(anyLong());
        verify(ratePlanValidator, times(1)).validate(any(CreateRatePlanRequest.class));
        verify(ratePlanRepository, never()).save(any(RatePlan.class)); // Ensure save is not called
    }


    @Test
    void testUpdate() {
        // Mock the existing rate plan
        RatePlan mockRatePlan = new RatePlan();
        mockRatePlan.setRatePlanName("Old Name");
        mockRatePlan.setDescription("Old Description");

        // Mock the currency and associate it with the rate plan
        Currencies mockCurrency = new Currencies();
        mockCurrency.setCurrencyId(1L); // Set a valid currency ID
        mockRatePlan.setCurrency(mockCurrency); // Associate the currency with the rate plan

        when(ratePlanRepository.findById(anyLong())).thenReturn(Optional.of(mockRatePlan));

        // Mock the currencies repository to simulate a valid currency
        when(currenciesRepository.findById(anyLong())).thenReturn(Optional.of(mockCurrency));

        // Mock the ratePlanMapper to return a non-null RatePlanDTO
        RatePlanDTO mockRatePlanDTO = new RatePlanDTO();
        mockRatePlanDTO.setRatePlanName("Old Name");
        mockRatePlanDTO.setDescription("Old Description");

        when(ratePlanMapper.updateRatePlanDTO(any(RatePlan.class), any(RatePlanDTO.class))).thenReturn(mockRatePlanDTO);

        // Mock the save method to return the updated rate plan
        when(ratePlanRepository.save(any(RatePlan.class))).thenReturn(mockRatePlan);

        // Set up the createRatePlanRequest with changes to trigger an update
        createRatePlanRequest.setRatePlanName("New Name");
        createRatePlanRequest.setDescription("New Description");
        createRatePlanRequest.setCurrencyId(1L); // Ensure this matches the mockCurrency

        // Call the method under test
        ratePlanService.update(1L, createRatePlanRequest);

        // Verify interactions
        verify(ratePlanRepository, times(1)).findById(anyLong()); // Verify findById was called
        verify(ratePlanRepository, times(1)).save(any(RatePlan.class)); // Verify save was called
        verify(currenciesRepository, times(1)).findById(anyLong()); // Verify currency validation
        verify(ratePlanMapper, times(1)).updateRatePlanDTO(any(RatePlan.class), any(RatePlanDTO.class)); // Verify mapper interaction
    }


    @Test
    void testDelete() {
        when(ratePlanRepository.findById(anyLong())).thenReturn(Optional.of(ratePlan));
        doNothing().when(ratePlanRepository).deleteById(anyLong());

        ratePlanService.delete(1L);

        verify(ratePlanRepository, times(1)).findById(anyLong());
        verify(ratePlanRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGetSelectedRatePlanTypeId() {
        // Mock a rate plan
        RatePlan mockRatePlan = new RatePlan();
        when(ratePlanRepository.findById(anyLong())).thenReturn(Optional.of(mockRatePlan));

        // Mock the specific repository for the rate plan type
        RatePlanFlatRate mockFlatRate = new RatePlanFlatRate();
        mockFlatRate.setRatePlanFlatRateId(1L); // Set an ID for the mocked flat rate
        when(ratePlanFlatRateRepository.findFirstByRatePlan(any(RatePlan.class))).thenReturn(Optional.of(mockFlatRate));

        // Call the method under test
        Optional<Long> result = ratePlanService.getSelectedRatePlanTypeId(1L, "FLAT_RATE");

        // Perform assertions
        assertTrue(result.isPresent(), "Expected result to be present");
        assertEquals(1L, result.get(), "Expected the ID to match the mocked flat rate ID");
    }
    @Test
    void testGetRatePlanCount() {
        // Mock the repository count method to return a specific value
        when(ratePlanRepository.count()).thenReturn(10L);

        // Call the method
        long count = ratePlanService.getRatePlanCount();

        // Assert the count
        assertEquals(10L, count);

        // Verify that the repository count method was called
        verify(ratePlanRepository, times(1)).count();
    }
}
