package aforo.productrateplanservie.currencies;

import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.exception.ReferencedWarning;
import aforo.productrateplanservie.exception.ValidationException;
import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
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
class CurrenciesServiceImplTest {

    @Mock
    private CurrenciesRepository currenciesRepository;

    @Mock
    private CurrenciesMapper currenciesMapper;

    @Mock
    private RatePlanRepository ratePlanRepository;

    @InjectMocks
    private CurrenciesServiceImpl currenciesService;

    private Currencies currencies;
    private CurrenciesDTO currenciesDTO;
    private CreateCurrenciesRequest createCurrenciesRequest;
    private static final Long CURRENCY_ID = 1L;

    @BeforeEach
    void setUp() {
        currencies = new Currencies();
        currencies.setCurrencyId(CURRENCY_ID);
        currencies.setCurrencyName("USD");
        currencies.setCurrencyCode("USD");
        currencies.setIsActive(true);

        currenciesDTO = new CurrenciesDTO();
        currenciesDTO.setCurrencyId(CURRENCY_ID);
        currenciesDTO.setCurrencyName("USD");
        currenciesDTO.setCurrencyCode("USD");
        currenciesDTO.setIsActive(true);

        createCurrenciesRequest = new CreateCurrenciesRequest();
        createCurrenciesRequest.setCurrencyName("USD");
        createCurrenciesRequest.setCurrencyCode("USD");
        createCurrenciesRequest.setIsActive(true);
    }

    @Test
    void findAll_WithoutFilter_ShouldReturnPageOfCurrenciesDTO() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Currencies> page = new PageImpl<>(Arrays.asList(currencies));
        when(currenciesRepository.findAll(pageable)).thenReturn(page);
        when(currenciesMapper.updateCurrenciesDTO(any(), any())).thenReturn(currenciesDTO);

        Page<CurrenciesDTO> result = currenciesService.findAll(null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(currenciesDTO, result.getContent().get(0));
        verify(currenciesRepository).findAll(pageable);
    }

    @Test
    void findAll_WithFilter_ShouldReturnFilteredPageOfCurrenciesDTO() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Currencies> page = new PageImpl<>(Arrays.asList(currencies));
        when(currenciesRepository.findAllByCurrencyId(CURRENCY_ID, pageable)).thenReturn(page);
        when(currenciesMapper.updateCurrenciesDTO(any(), any())).thenReturn(currenciesDTO);

        Page<CurrenciesDTO> result = currenciesService.findAll(CURRENCY_ID.toString(), pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(currenciesDTO, result.getContent().get(0));
        verify(currenciesRepository).findAllByCurrencyId(CURRENCY_ID, pageable);
    }

    @Test
    void get_WithValidId_ShouldReturnCurrenciesDTO() {
        when(currenciesRepository.findById(CURRENCY_ID)).thenReturn(Optional.of(currencies));
        when(currenciesMapper.updateCurrenciesDTO(any(), any())).thenReturn(currenciesDTO);

        CurrenciesDTO result = currenciesService.get(CURRENCY_ID);

        assertNotNull(result);
        assertEquals(currenciesDTO, result);
        verify(currenciesRepository).findById(CURRENCY_ID);
    }

    @Test
    void get_WithInvalidId_ShouldThrowNotFoundException() {
        when(currenciesRepository.findById(CURRENCY_ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> currenciesService.get(CURRENCY_ID));
        verify(currenciesRepository).findById(CURRENCY_ID);
    }

    @Test
    void create_WithValidData_ShouldReturnNewCurrencyId() {
        when(currenciesMapper.createCurrenciesRequestToCurrenciesDTO(createCurrenciesRequest)).thenReturn(currenciesDTO);
        when(currenciesRepository.save(any(Currencies.class))).thenReturn(currencies);

        Long result = currenciesService.create(createCurrenciesRequest);

        assertEquals(CURRENCY_ID, result);
        verify(currenciesRepository).save(any(Currencies.class));
    }

    @Test
    void create_WithInvalidData_ShouldThrowValidationException() {
        createCurrenciesRequest.setCurrencyName("");
        assertThrows(ValidationException.class, () -> currenciesService.create(createCurrenciesRequest));

        createCurrenciesRequest.setCurrencyName("USD");
        createCurrenciesRequest.setCurrencyCode("");
        assertThrows(ValidationException.class, () -> currenciesService.create(createCurrenciesRequest));
    }

    @Test
    void update_WithValidData_ShouldUpdateCurrency() {
        when(currenciesRepository.findById(CURRENCY_ID)).thenReturn(Optional.of(currencies));
        when(currenciesMapper.updateCurrenciesDTO(any(), any())).thenReturn(currenciesDTO);

        createCurrenciesRequest.setCurrencyName("Updated USD");
        currenciesService.update(CURRENCY_ID, createCurrenciesRequest);

        verify(currenciesRepository).findById(CURRENCY_ID);
        verify(currenciesRepository).save(any(Currencies.class));
    }

    @Test
    void update_WithInvalidId_ShouldThrowNotFoundException() {
        when(currenciesRepository.findById(CURRENCY_ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> currenciesService.update(CURRENCY_ID, createCurrenciesRequest));
        verify(currenciesRepository).findById(CURRENCY_ID);
    }

    @Test
    void delete_WithValidId_ShouldDeleteCurrency() {
        currenciesService.delete(CURRENCY_ID);

        verify(currenciesRepository).deleteById(CURRENCY_ID);
    }

    @Test
    void getReferencedWarning_WithReferencedCurrency_ShouldReturnWarning() {
        RatePlan ratePlan = new RatePlan();
        ratePlan.setRatePlanId(1L);
        when(currenciesRepository.findById(CURRENCY_ID)).thenReturn(Optional.of(currencies));
        when(ratePlanRepository.findFirstByCurrency(currencies)).thenReturn(ratePlan);

        ReferencedWarning result = currenciesService.getReferencedWarning(CURRENCY_ID);

        assertNotNull(result);
        assertEquals("currencies.ratePlan.currency.referenced", result.getKey());
        assertEquals(1L, result.getParams().get(0));
    }

    @Test
    void getReferencedWarning_WithNoReferencedCurrency_ShouldReturnNull() {
        when(currenciesRepository.findById(CURRENCY_ID)).thenReturn(Optional.of(currencies));
        when(ratePlanRepository.findFirstByCurrency(currencies)).thenReturn(null);

        ReferencedWarning result = currenciesService.getReferencedWarning(CURRENCY_ID);

        assertNull(result);
    }
    @Test
    void getCurrencyCount_ShouldReturnTotalCurrenciesCount() {
        when(currenciesRepository.count()).thenReturn(5L);

        long result = currenciesService.getCurrencyCount();

        assertEquals(5L, result);
        verify(currenciesRepository).count();
    }
}

