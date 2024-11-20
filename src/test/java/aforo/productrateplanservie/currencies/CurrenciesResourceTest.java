package aforo.productrateplanservie.currencies;

import aforo.productrateplanservie.exception.ReferencedException;
import aforo.productrateplanservie.exception.ReferencedWarning;
import aforo.productrateplanservie.model.SimpleValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CurrenciesResourceTest {

    @Mock
    private CurrenciesService currenciesService;

    @Mock
    private CurrenciesAssembler currenciesAssembler;

    @Mock
    private PagedResourcesAssembler<CurrenciesDTO> pagedResourcesAssembler;

    @InjectMocks
    private CurrenciesResource currenciesResource;

    private CurrenciesDTO currenciesDTO;
    private Long currencyId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize mock data
        currenciesDTO = new CurrenciesDTO(); // Populate as necessary
    }

    @Test
    void testGetAllCurrenciess() {
        // Mock PagedModel and CurrenciesDTO
        Page<CurrenciesDTO> mockPage = new PageImpl<>(Collections.singletonList(currenciesDTO));
        PagedModel<EntityModel<CurrenciesDTO>> mockPagedModel = PagedModel.of(
                Collections.singletonList(EntityModel.of(currenciesDTO)),
                new PagedModel.PageMetadata(1, 0, 1)
        );

        when(currenciesService.findAll(any(), any())).thenReturn(mockPage);
        when(pagedResourcesAssembler.toModel(mockPage, currenciesAssembler)).thenReturn(mockPagedModel);

        ResponseEntity<PagedModel<EntityModel<CurrenciesDTO>>> response = currenciesResource.getAllCurrenciess("filter", null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockPagedModel, response.getBody());
    }

    @Test
    void testGetCurrencies() {
        when(currenciesService.get(anyLong())).thenReturn(currenciesDTO);
        when(currenciesAssembler.toModel(currenciesDTO)).thenReturn(EntityModel.of(currenciesDTO));

        ResponseEntity<EntityModel<CurrenciesDTO>> response = currenciesResource.getCurrencies(currencyId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(EntityModel.of(currenciesDTO), response.getBody());
    }

    @Test
    void testCreateCurrencies() {
        CreateCurrenciesRequest createCurrenciesRequest = new CreateCurrenciesRequest(); // Populate as needed
        when(currenciesService.create(any(CreateCurrenciesRequest.class))).thenReturn(currencyId);
        when(currenciesAssembler.toSimpleModel(currencyId)).thenReturn(EntityModel.of(new SimpleValue<>(currencyId)));

        ResponseEntity<EntityModel<SimpleValue<Long>>> response = currenciesResource.createCurrencies(createCurrenciesRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(currencyId, response.getBody().getContent().getValue());
    }

    @Test
    void testUpdateCurrencies() {
        CreateCurrenciesRequest updateCurrenciesRequest = new CreateCurrenciesRequest(); // Populate as needed
        doNothing().when(currenciesService).update(anyLong(), any(CreateCurrenciesRequest.class));
        when(currenciesAssembler.toSimpleModel(currencyId)).thenReturn(EntityModel.of(new SimpleValue<>(currencyId)));

        ResponseEntity<EntityModel<SimpleValue<Long>>> response = currenciesResource.updateCurrencies(currencyId, updateCurrenciesRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(currencyId, response.getBody().getContent().getValue());
    }

    @Test
    void testDeleteCurrencies() {
        when(currenciesService.getReferencedWarning(currencyId)).thenReturn(null);
        doNothing().when(currenciesService).delete(anyLong());

        ResponseEntity<Void> response = currenciesResource.deleteCurrencies(currencyId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(currenciesService, times(1)).delete(currencyId);
    }

    @Test
    void testDeleteCurrencies_withReferencedWarning() {
        ReferencedWarning warning = new ReferencedWarning(); // Initialize as needed
        when(currenciesService.getReferencedWarning(currencyId)).thenReturn(warning);

        ReferencedException thrown = assertThrows(ReferencedException.class, () -> currenciesResource.deleteCurrencies(currencyId));

        assertNotNull(thrown);
        assertEquals(warning, thrown.getReferencedWarning());
        verify(currenciesService, times(0)).delete(currencyId);
    }
}