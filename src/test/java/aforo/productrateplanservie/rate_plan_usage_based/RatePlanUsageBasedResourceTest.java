package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.model.SimpleValue;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RatePlanUsageBasedResourceTest {

    @Mock
    private RatePlanUsageBasedService ratePlanUsageBasedService;

    @Mock
    private RatePlanUsageBasedAssembler ratePlanUsageBasedAssembler;

    @Mock
    private PagedResourcesAssembler<RatePlanUsageBasedDTO> pagedResourcesAssembler;

    @InjectMocks
    private RatePlanUsageBasedResource ratePlanUsageBasedResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRatePlanUsageBaseds() {
        // Arrange
        String filter = "testFilter";
        Pageable pageable = Pageable.unpaged();
        Page<RatePlanUsageBasedDTO> page = new PageImpl<>(List.of(new RatePlanUsageBasedDTO()));
        PagedModel<EntityModel<RatePlanUsageBasedDTO>> pagedModel = mock(PagedModel.class);

        when(ratePlanUsageBasedService.findAll(filter, pageable)).thenReturn(page);
        when(pagedResourcesAssembler.toModel(page, ratePlanUsageBasedAssembler)).thenReturn(pagedModel);

        // Act
        ResponseEntity<PagedModel<EntityModel<RatePlanUsageBasedDTO>>> response =
                ratePlanUsageBasedResource.getAllRatePlanUsageBaseds(filter, pageable);

        // Assert
        assertThat(response.getBody()).isEqualTo(pagedModel);
        verify(ratePlanUsageBasedService, times(1)).findAll(filter, pageable);
        verify(pagedResourcesAssembler, times(1)).toModel(page, ratePlanUsageBasedAssembler);
    }

    @Test
    void getRatePlanUsageBased() {
        // Arrange
        Long ratePlanUsageRateId = 1L;
        RatePlanUsageBasedDTO dto = new RatePlanUsageBasedDTO();
        EntityModel<RatePlanUsageBasedDTO> entityModel = mock(EntityModel.class);

        when(ratePlanUsageBasedService.get(ratePlanUsageRateId)).thenReturn(dto);
        when(ratePlanUsageBasedAssembler.toModel(dto)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<RatePlanUsageBasedDTO>> response =
                ratePlanUsageBasedResource.getRatePlanUsageBased(ratePlanUsageRateId);

        // Assert
        assertThat(response.getBody()).isEqualTo(entityModel);
        verify(ratePlanUsageBasedService, times(1)).get(ratePlanUsageRateId);
        verify(ratePlanUsageBasedAssembler, times(1)).toModel(dto);
    }

    @Test
    void createRatePlanUsageBased() {
        // Arrange
        Long ratePlanId = 1L;
        CreateRatePlanUsageBasedRequest request = new CreateRatePlanUsageBasedRequest();
        Long createdId = 1L;
        EntityModel<SimpleValue<Long>> entityModel = mock(EntityModel.class);

        when(ratePlanUsageBasedService.create(ratePlanId, request)).thenReturn(createdId);
        when(ratePlanUsageBasedAssembler.toSimpleModel(createdId)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<SimpleValue<Long>>> response =
                ratePlanUsageBasedResource.createRatePlanUsageBased(ratePlanId, request);

        // Assert
        assertThat(response.getBody()).isEqualTo(entityModel);
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        verify(ratePlanUsageBasedService, times(1)).create(ratePlanId, request);
        verify(ratePlanUsageBasedAssembler, times(1)).toSimpleModel(createdId);
    }

    @Test
    void updateRatePlanUsageBased() {
        // Arrange
        Long ratePlanId = 1L;
        Long ratePlanUsageRateId = 1L;
        UpdateRatePlanUsageBasedRequest request = new UpdateRatePlanUsageBasedRequest();
        EntityModel<SimpleValue<Long>> entityModel = mock(EntityModel.class);

        when(ratePlanUsageBasedAssembler.toSimpleModel(ratePlanUsageRateId)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<SimpleValue<Long>>> response =
                ratePlanUsageBasedResource.updateRatePlanUsageBased(ratePlanId, ratePlanUsageRateId, request);

        // Assert
        assertThat(response.getBody()).isEqualTo(entityModel);
        verify(ratePlanUsageBasedService, times(1)).update(ratePlanId, ratePlanUsageRateId, request);
        verify(ratePlanUsageBasedAssembler, times(1)).toSimpleModel(ratePlanUsageRateId);
    }

    @Test
    void deleteRatePlanUsageBased() {
        // Arrange
        Long ratePlanUsageRateId = 1L;

        // Act
        ResponseEntity<Void> response = ratePlanUsageBasedResource.deleteRatePlanUsageBased(ratePlanUsageRateId);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(ratePlanUsageBasedService, times(1)).delete(ratePlanUsageRateId);
    }
}
