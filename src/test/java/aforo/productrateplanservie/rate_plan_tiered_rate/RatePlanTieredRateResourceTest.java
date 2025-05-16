package aforo.productrateplanservie.rate_plan_tiered_rate;

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
import aforo.productrateplanservice.rate_plan_tiered_rate.CreateRatePlanTieredRateRequest;
import aforo.productrateplanservice.rate_plan_tiered_rate.RatePlanTieredRateAssembler;
import aforo.productrateplanservice.rate_plan_tiered_rate.RatePlanTieredRateDTO;
import aforo.productrateplanservice.rate_plan_tiered_rate.RatePlanTieredRateResource;
import aforo.productrateplanservice.rate_plan_tiered_rate.RatePlanTieredRateService;
import aforo.productrateplanservice.rate_plan_tiered_rate.UpdateRatePlanTieredRateRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RatePlanTieredRateResourceTest {

    @Mock
    private RatePlanTieredRateService ratePlanTieredRateService;

    @Mock
    private RatePlanTieredRateAssembler ratePlanTieredRateAssembler;

    @Mock
    private PagedResourcesAssembler<RatePlanTieredRateDTO> pagedResourcesAssembler;

    @InjectMocks
    private RatePlanTieredRateResource ratePlanTieredRateResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRatePlanTieredRates() {
        // Arrange
        String filter = "test";
        Pageable pageable = Pageable.unpaged();
        Page<RatePlanTieredRateDTO> page = new PageImpl<>(List.of(new RatePlanTieredRateDTO()));
        PagedModel<EntityModel<RatePlanTieredRateDTO>> pagedModel = mock(PagedModel.class);

        when(ratePlanTieredRateService.findAll(filter, pageable)).thenReturn(page);
        when(pagedResourcesAssembler.toModel(page, ratePlanTieredRateAssembler)).thenReturn(pagedModel);

        // Act
        ResponseEntity<PagedModel<EntityModel<RatePlanTieredRateDTO>>> response = ratePlanTieredRateResource.getAllRatePlanTieredRates(filter, pageable);

        // Assert
        assertEquals(pagedModel, response.getBody());
        verify(ratePlanTieredRateService, times(1)).findAll(filter, pageable);
        verify(pagedResourcesAssembler, times(1)).toModel(page, ratePlanTieredRateAssembler);
    }

    @Test
    void getRatePlanTieredRate() {
        // Arrange
        Long ratePlanTieredRateId = 1L;
        RatePlanTieredRateDTO dto = new RatePlanTieredRateDTO();
        EntityModel<RatePlanTieredRateDTO> entityModel = mock(EntityModel.class);

        when(ratePlanTieredRateService.get(ratePlanTieredRateId)).thenReturn(dto);
        when(ratePlanTieredRateAssembler.toModel(dto)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<RatePlanTieredRateDTO>> response = ratePlanTieredRateResource.getRatePlanTieredRate(ratePlanTieredRateId);

        // Assert
        assertEquals(entityModel, response.getBody());
        verify(ratePlanTieredRateService, times(1)).get(ratePlanTieredRateId);
        verify(ratePlanTieredRateAssembler, times(1)).toModel(dto);
    }

    @Test
    void createRatePlanTieredRate() {
        // Arrange
        Long ratePlanId = 1L;
        CreateRatePlanTieredRateRequest request = new CreateRatePlanTieredRateRequest();
        Long createdId = 1L;
        EntityModel<SimpleValue<Long>> entityModel = mock(EntityModel.class);

        when(ratePlanTieredRateService.create(ratePlanId, request)).thenReturn(createdId);
        when(ratePlanTieredRateAssembler.toSimpleModel(createdId)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<SimpleValue<Long>>> response = ratePlanTieredRateResource.createRatePlanTieredRate(ratePlanId, request);

        // Assert
        assertEquals(entityModel, response.getBody());
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        verify(ratePlanTieredRateService, times(1)).create(ratePlanId, request);
        verify(ratePlanTieredRateAssembler, times(1)).toSimpleModel(createdId);
    }

    @Test
    void updateRatePlanTieredRate() {
        // Arrange
        Long ratePlanId = 1L;
        Long ratePlanTieredRateId = 1L;
        UpdateRatePlanTieredRateRequest request = new UpdateRatePlanTieredRateRequest();
        EntityModel<SimpleValue<Long>> entityModel = mock(EntityModel.class);

        when(ratePlanTieredRateAssembler.toSimpleModel(ratePlanTieredRateId)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<SimpleValue<Long>>> response = ratePlanTieredRateResource.updateRatePlanTieredRate(ratePlanId, ratePlanTieredRateId, request);

        // Assert
        assertEquals(entityModel, response.getBody());
        verify(ratePlanTieredRateService, times(1)).update(ratePlanId, ratePlanTieredRateId, request);
        verify(ratePlanTieredRateAssembler, times(1)).toSimpleModel(ratePlanTieredRateId);
    }

    @Test
    void deleteRatePlanTieredRate() {
        // Arrange
        Long ratePlanTieredRateId = 1L;

        // Act
        ResponseEntity<Void> response = ratePlanTieredRateResource.deleteRatePlanTieredRate(ratePlanTieredRateId);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(ratePlanTieredRateService, times(1)).delete(ratePlanTieredRateId);
    }
    @Test
    void getRatePlanTieredRateCount() {
        // Arrange
        long expectedCount = 42L; // Example value
        when(ratePlanTieredRateService.getRatePlanTieredRateCount()).thenReturn(expectedCount);

        // Act
        ResponseEntity<Long> response = ratePlanTieredRateResource.getRatePlanTieredRateCount();

        // Assert
        assertEquals(expectedCount, response.getBody());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(ratePlanTieredRateService, times(1)).getRatePlanTieredRateCount();
    }

}
