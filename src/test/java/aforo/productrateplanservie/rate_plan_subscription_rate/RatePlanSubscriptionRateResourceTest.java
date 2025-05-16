package aforo.productrateplanservie.rate_plan_subscription_rate;

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
import aforo.productrateplanservice.rate_plan_subscription_rate.CreateRatePlanSubscriptionRateRequest;
import aforo.productrateplanservice.rate_plan_subscription_rate.RatePlanSubscriptionRateAssembler;
import aforo.productrateplanservice.rate_plan_subscription_rate.RatePlanSubscriptionRateDTO;
import aforo.productrateplanservice.rate_plan_subscription_rate.RatePlanSubscriptionRateResource;
import aforo.productrateplanservice.rate_plan_subscription_rate.RatePlanSubscriptionRateService;
import aforo.productrateplanservice.rate_plan_subscription_rate.UpdateRatePlanSubscriptionRateRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RatePlanSubscriptionRateResourceTest {

    @Mock
    private RatePlanSubscriptionRateService ratePlanSubscriptionRateService;

    @Mock
    private RatePlanSubscriptionRateAssembler ratePlanSubscriptionRateAssembler;

    @Mock
    private PagedResourcesAssembler<RatePlanSubscriptionRateDTO> pagedResourcesAssembler;

    @InjectMocks
    private RatePlanSubscriptionRateResource ratePlanSubscriptionRateResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRatePlanSubscriptionRates() {
        // Arrange
        String filter = "test";
        Pageable pageable = Pageable.unpaged();
        Page<RatePlanSubscriptionRateDTO> page = new PageImpl<>(List.of(new RatePlanSubscriptionRateDTO()));
        PagedModel<EntityModel<RatePlanSubscriptionRateDTO>> pagedModel = mock(PagedModel.class);

        when(ratePlanSubscriptionRateService.findAll(filter, pageable)).thenReturn(page);
        when(pagedResourcesAssembler.toModel(page, ratePlanSubscriptionRateAssembler)).thenReturn(pagedModel);

        // Act
        ResponseEntity<PagedModel<EntityModel<RatePlanSubscriptionRateDTO>>> response = ratePlanSubscriptionRateResource.getAllRatePlanSubscriptionRates(filter, pageable);

        // Assert
        assertEquals(pagedModel, response.getBody());
        verify(ratePlanSubscriptionRateService, times(1)).findAll(filter, pageable);
        verify(pagedResourcesAssembler, times(1)).toModel(page, ratePlanSubscriptionRateAssembler);
    }

    @Test
    void getRatePlanSubscriptionRate() {
        // Arrange
        Long ratePlanSubscriptionRateId = 1L;
        RatePlanSubscriptionRateDTO dto = new RatePlanSubscriptionRateDTO();
        EntityModel<RatePlanSubscriptionRateDTO> entityModel = mock(EntityModel.class);

        when(ratePlanSubscriptionRateService.get(ratePlanSubscriptionRateId)).thenReturn(dto);
        when(ratePlanSubscriptionRateAssembler.toModel(dto)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<RatePlanSubscriptionRateDTO>> response = ratePlanSubscriptionRateResource.getRatePlanSubscriptionRate(ratePlanSubscriptionRateId);

        // Assert
        assertEquals(entityModel, response.getBody());
        verify(ratePlanSubscriptionRateService, times(1)).get(ratePlanSubscriptionRateId);
        verify(ratePlanSubscriptionRateAssembler, times(1)).toModel(dto);
    }

    @Test
    void createRatePlanSubscriptionRate() {
        // Arrange
        Long ratePlanId = 1L;
        CreateRatePlanSubscriptionRateRequest request = new CreateRatePlanSubscriptionRateRequest();
        Long createdId = 1L;
        EntityModel<SimpleValue<Long>> entityModel = mock(EntityModel.class);

        when(ratePlanSubscriptionRateService.create(ratePlanId, request)).thenReturn(createdId);
        when(ratePlanSubscriptionRateAssembler.toSimpleModel(createdId)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<SimpleValue<Long>>> response = ratePlanSubscriptionRateResource.createRatePlanSubscriptionRate(ratePlanId, request);

        // Assert
        assertEquals(entityModel, response.getBody());
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        verify(ratePlanSubscriptionRateService, times(1)).create(ratePlanId, request);
        verify(ratePlanSubscriptionRateAssembler, times(1)).toSimpleModel(createdId);
    }

    @Test
    void updateRatePlanSubscriptionRate() {
        // Arrange
        Long ratePlanId = 1L;
        Long ratePlanSubscriptionRateId = 1L;
        UpdateRatePlanSubscriptionRateRequest request = new UpdateRatePlanSubscriptionRateRequest();
        EntityModel<SimpleValue<Long>> entityModel = mock(EntityModel.class);

        when(ratePlanSubscriptionRateAssembler.toSimpleModel(ratePlanSubscriptionRateId)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<SimpleValue<Long>>> response = ratePlanSubscriptionRateResource.updateRatePlanSubscriptionRate(ratePlanId, ratePlanSubscriptionRateId, request);

        // Assert
        assertEquals(entityModel, response.getBody());
        verify(ratePlanSubscriptionRateService, times(1)).update(ratePlanId, ratePlanSubscriptionRateId, request);
        verify(ratePlanSubscriptionRateAssembler, times(1)).toSimpleModel(ratePlanSubscriptionRateId);
    }

    @Test
    void deleteRatePlanSubscriptionRate() {
        // Arrange
        Long ratePlanSubscriptionRateId = 1L;

        // Act
        ResponseEntity<Void> response = ratePlanSubscriptionRateResource.deleteRatePlanSubscriptionRate(ratePlanSubscriptionRateId);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(ratePlanSubscriptionRateService, times(1)).delete(ratePlanSubscriptionRateId);
    }
    @Test
    void getRatePlanSubscriptionRateCount() {
        // Arrange
        long expectedCount = 42L;
        when(ratePlanSubscriptionRateService.getRatePlanSubscriptionRateCount()).thenReturn(expectedCount);

        // Act
        ResponseEntity<Long> response = ratePlanSubscriptionRateResource.getRatePlanSubscriptionRateCount();

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(expectedCount);
        verify(ratePlanSubscriptionRateService, times(1)).getRatePlanSubscriptionRateCount();
    }

}
