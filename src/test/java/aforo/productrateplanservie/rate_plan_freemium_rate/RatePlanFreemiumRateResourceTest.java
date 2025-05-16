package aforo.productrateplanservie.rate_plan_freemium_rate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import aforo.productrateplanservice.model.SimpleValue;
import aforo.productrateplanservice.rate_plan_freemium_rate.CreateRatePlanFreemiumRateRequest;
import aforo.productrateplanservice.rate_plan_freemium_rate.RatePlanFreemiumRateAssembler;
import aforo.productrateplanservice.rate_plan_freemium_rate.RatePlanFreemiumRateDTO;
import aforo.productrateplanservice.rate_plan_freemium_rate.RatePlanFreemiumRateResource;
import aforo.productrateplanservice.rate_plan_freemium_rate.RatePlanFreemiumRateService;
import aforo.productrateplanservice.rate_plan_freemium_rate.UpdateRatePlanFreemiumRateRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RatePlanFreemiumRateResourceTest {

    @Mock
    private RatePlanFreemiumRateService ratePlanFreemiumRateService;

    @Mock
    private RatePlanFreemiumRateAssembler ratePlanFreemiumRateAssembler;

    @Mock
    private PagedResourcesAssembler<RatePlanFreemiumRateDTO> pagedResourcesAssembler;

    @InjectMocks
    private RatePlanFreemiumRateResource ratePlanFreemiumRateResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRatePlanFreemiumRates() {
        // Arrange
        String filter = "test";
        Pageable pageable = Pageable.unpaged();
        Page<RatePlanFreemiumRateDTO> page = new PageImpl<>(List.of(new RatePlanFreemiumRateDTO()));
        PagedModel<EntityModel<RatePlanFreemiumRateDTO>> pagedModel = mock(PagedModel.class);

        when(ratePlanFreemiumRateService.findAll(filter, pageable)).thenReturn(page);
        when(pagedResourcesAssembler.toModel(page, ratePlanFreemiumRateAssembler)).thenReturn(pagedModel);

        // Act
        ResponseEntity<PagedModel<EntityModel<RatePlanFreemiumRateDTO>>> response = ratePlanFreemiumRateResource.getAllRatePlanFreemiumRates(filter, pageable);

        // Assert
        assertEquals(pagedModel, response.getBody());
        verify(ratePlanFreemiumRateService, times(1)).findAll(filter, pageable);
        verify(pagedResourcesAssembler, times(1)).toModel(page, ratePlanFreemiumRateAssembler);
    }

    @Test
    void getRatePlanFreemiumRate() {
        // Arrange
        Long ratePlanFreemiumRateId = 1L;
        RatePlanFreemiumRateDTO dto = new RatePlanFreemiumRateDTO();
        EntityModel<RatePlanFreemiumRateDTO> entityModel = mock(EntityModel.class);

        when(ratePlanFreemiumRateService.get(ratePlanFreemiumRateId)).thenReturn(dto);
        when(ratePlanFreemiumRateAssembler.toModel(dto)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<RatePlanFreemiumRateDTO>> response = ratePlanFreemiumRateResource.getRatePlanFreemiumRate(ratePlanFreemiumRateId);

        // Assert
        assertEquals(entityModel, response.getBody());
        verify(ratePlanFreemiumRateService, times(1)).get(ratePlanFreemiumRateId);
        verify(ratePlanFreemiumRateAssembler, times(1)).toModel(dto);
    }

    @Test
    void createRatePlanFreemiumRate() {
        // Arrange
        Long ratePlanId = 1L;
        CreateRatePlanFreemiumRateRequest request = new CreateRatePlanFreemiumRateRequest();
        Long createdId = 1L;
        EntityModel<SimpleValue<Long>> entityModel = mock(EntityModel.class);

        when(ratePlanFreemiumRateService.create(ratePlanId, request)).thenReturn(createdId);
        when(ratePlanFreemiumRateAssembler.toSimpleModel(createdId)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<SimpleValue<Long>>> response = ratePlanFreemiumRateResource.createRatePlanFreemiumRate(ratePlanId, request);

        // Assert
        assertEquals(entityModel, response.getBody());
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        verify(ratePlanFreemiumRateService, times(1)).create(ratePlanId, request);
        verify(ratePlanFreemiumRateAssembler, times(1)).toSimpleModel(createdId);
    }

    @Test
    void updateRatePlanFreemiumRate() {
        // Arrange
        Long ratePlanId = 1L;
        Long ratePlanFreemiumRateId = 1L;
        UpdateRatePlanFreemiumRateRequest request = new UpdateRatePlanFreemiumRateRequest();
        EntityModel<SimpleValue<Long>> entityModel = mock(EntityModel.class);

        when(ratePlanFreemiumRateAssembler.toSimpleModel(ratePlanFreemiumRateId)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<SimpleValue<Long>>> response = ratePlanFreemiumRateResource.updateRatePlanFreemiumRate(ratePlanId, ratePlanFreemiumRateId, request);

        // Assert
        assertEquals(entityModel, response.getBody());
        verify(ratePlanFreemiumRateService, times(1)).update(ratePlanId, ratePlanFreemiumRateId, request);
        verify(ratePlanFreemiumRateAssembler, times(1)).toSimpleModel(ratePlanFreemiumRateId);
    }

    @Test
    void deleteRatePlanFreemiumRate() {
        // Arrange
        Long ratePlanFreemiumRateId = 1L;

        // Act
        ResponseEntity<Void> response = ratePlanFreemiumRateResource.deleteRatePlanFreemiumRate(ratePlanFreemiumRateId);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(ratePlanFreemiumRateService, times(1)).delete(ratePlanFreemiumRateId);
    }
    @Test
    void getRatePlanFreemiumRateCount() {
        // Arrange
        long expectedCount = 42L; // Example count value
        when(ratePlanFreemiumRateService.getRatePlanFreemiumRateCount()).thenReturn(expectedCount);

        // Act
        ResponseEntity<Long> response = ratePlanFreemiumRateResource.getRatePlanFreemiumRateCount();

        // Assert
        assertEquals(expectedCount, response.getBody());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(ratePlanFreemiumRateService, times(1)).getRatePlanFreemiumRateCount();
    }

}
