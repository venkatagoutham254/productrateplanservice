package aforo.productrateplanservie.rate_plan_flat_rate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import aforo.productrateplanservice.exception.ReferencedException;
import aforo.productrateplanservice.exception.ReferencedWarning;
import aforo.productrateplanservice.model.SimpleValue;
import aforo.productrateplanservice.rate_plan_flat_rate.CreateRatePlanFlatRateRequest;
import aforo.productrateplanservice.rate_plan_flat_rate.RatePlanFlatRateAssembler;
import aforo.productrateplanservice.rate_plan_flat_rate.RatePlanFlatRateDTO;
import aforo.productrateplanservice.rate_plan_flat_rate.RatePlanFlatRateResource;
import aforo.productrateplanservice.rate_plan_flat_rate.RatePlanFlatRateService;
import aforo.productrateplanservice.rate_plan_flat_rate.UpdateRatePlanFlatRateRequest;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatePlanFlatRateResourceTest {

    @Mock
    private RatePlanFlatRateService ratePlanFlatRateService;

    @Mock
    private RatePlanFlatRateAssembler ratePlanFlatRateAssembler;

    @Mock
    private PagedResourcesAssembler<RatePlanFlatRateDTO> pagedResourcesAssembler;

    @InjectMocks
    private RatePlanFlatRateResource ratePlanFlatRateResource;

    @Test
    void getAllRatePlanFlatRateShouldReturnPagedModel() {
        // Arrange
        String filter = null;
        Pageable pageable = Pageable.ofSize(20);
        Page<RatePlanFlatRateDTO> ratePlanFlatRateDTOs = new PageImpl<>(List.of(new RatePlanFlatRateDTO()));
        PagedModel<EntityModel<RatePlanFlatRateDTO>> pagedModel = PagedModel.empty();

        when(ratePlanFlatRateService.findAll(filter, pageable)).thenReturn(ratePlanFlatRateDTOs);
        when(pagedResourcesAssembler.toModel(ratePlanFlatRateDTOs, ratePlanFlatRateAssembler)).thenReturn(pagedModel);

        // Act
        ResponseEntity<PagedModel<EntityModel<RatePlanFlatRateDTO>>> response =
                ratePlanFlatRateResource.getAllRatePlanFlatRates(filter, pageable);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(pagedModel);
        verify(ratePlanFlatRateService).findAll(filter, pageable);
        verify(pagedResourcesAssembler).toModel(ratePlanFlatRateDTOs, ratePlanFlatRateAssembler);
    }

    @Test
    void getRatePlanFlatRatesByRatePlanIdShouldReturnPagedModel() {
        // Arrange
        Long ratePlanId = 1L;
        Pageable pageable = Pageable.ofSize(20);
        Page<RatePlanFlatRateDTO> ratePlanFlatRateDTOs = new PageImpl<>(List.of(new RatePlanFlatRateDTO()));
        PagedModel<EntityModel<RatePlanFlatRateDTO>> pagedModel = PagedModel.empty();

        when(ratePlanFlatRateService.findAllByRatePlanId(ratePlanId, pageable)).thenReturn(ratePlanFlatRateDTOs);
        when(pagedResourcesAssembler.toModel(ratePlanFlatRateDTOs, ratePlanFlatRateAssembler)).thenReturn(pagedModel);

        // Act
        ResponseEntity<PagedModel<EntityModel<RatePlanFlatRateDTO>>> response =
                ratePlanFlatRateResource.getRatePlanFlatRatesByRatePlanId(ratePlanId, pageable);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(pagedModel);
        verify(ratePlanFlatRateService).findAllByRatePlanId(ratePlanId, pageable);
        verify(pagedResourcesAssembler).toModel(ratePlanFlatRateDTOs, ratePlanFlatRateAssembler);
    }

    @Test
    void getRatePlanFlatRateShouldReturnEntityModel() {
        // Arrange
        Long ratePlanFlatRateId = 1L;
        RatePlanFlatRateDTO dto = new RatePlanFlatRateDTO();
        EntityModel<RatePlanFlatRateDTO> entityModel = EntityModel.of(dto);

        when(ratePlanFlatRateService.get(ratePlanFlatRateId)).thenReturn(dto);
        when(ratePlanFlatRateAssembler.toModel(dto)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<RatePlanFlatRateDTO>> response =
                ratePlanFlatRateResource.getRatePlanFlatRate(ratePlanFlatRateId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(entityModel);
        verify(ratePlanFlatRateService).get(ratePlanFlatRateId);
        verify(ratePlanFlatRateAssembler).toModel(dto);
    }

    @Test
    void createRatePlanFlatRateShouldReturnCreatedResponse() {
        // Arrange
        Long ratePlanId = 1L;
        CreateRatePlanFlatRateRequest request = new CreateRatePlanFlatRateRequest();
        Long createdId = 1L;
        EntityModel<SimpleValue<Long>> entityModel = EntityModel.of(new SimpleValue<>(createdId));

        when(ratePlanFlatRateService.create(ratePlanId, request)).thenReturn(createdId);
        when(ratePlanFlatRateAssembler.toSimpleModel(createdId)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<SimpleValue<Long>>> response =
                ratePlanFlatRateResource.createRatePlanFlatRate(ratePlanId, request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(entityModel);
        verify(ratePlanFlatRateService).create(ratePlanId, request);
        verify(ratePlanFlatRateAssembler).toSimpleModel(createdId);
    }

    @Test
    void updateRatePlanFlatRateShouldReturnUpdatedResponse() {
        // Arrange
        Long ratePlanId = 1L;
        Long ratePlanFlatRateId = 1L;
        UpdateRatePlanFlatRateRequest updateRequest = new UpdateRatePlanFlatRateRequest();
        EntityModel<SimpleValue<Long>> entityModel = EntityModel.of(new SimpleValue<>(ratePlanFlatRateId));

        when(ratePlanFlatRateAssembler.toSimpleModel(ratePlanFlatRateId)).thenReturn(entityModel);

        // Act
        ResponseEntity<EntityModel<SimpleValue<Long>>> response =
                ratePlanFlatRateResource.updateRatePlanFlatRate(ratePlanId, ratePlanFlatRateId, updateRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(entityModel);
        verify(ratePlanFlatRateService).update(ratePlanId, ratePlanFlatRateId, updateRequest);
        verify(ratePlanFlatRateAssembler).toSimpleModel(ratePlanFlatRateId);
    }

    @Test
    void deleteRatePlanFlatRateShouldDeleteEntity() {
        // Given
        Long ratePlanFlatRateId = 2L;

        // When
        doNothing().when(ratePlanFlatRateService).delete(ratePlanFlatRateId);

        ResponseEntity<Void> response = ratePlanFlatRateResource.deleteRatePlanFlatRate(ratePlanFlatRateId);

        // Then
        verify(ratePlanFlatRateService, times(1)).delete(ratePlanFlatRateId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


    @Test
    void deleteRatePlanFlatRateShouldThrowReferencedException() {
        // Given
        Long ratePlanFlatRateId = 1L;
        ReferencedWarning referencedWarning = new ReferencedWarning();
        referencedWarning.setKey("Reference exists");
        referencedWarning.addParam("RatePlanFlatRate");
        ReferencedException referencedException = new ReferencedException(referencedWarning);

        // When
        doThrow(referencedException).when(ratePlanFlatRateService).delete(ratePlanFlatRateId);

        // Then
        ReferencedException thrownException = assertThrows(
                ReferencedException.class,
                () -> ratePlanFlatRateResource.deleteRatePlanFlatRate(ratePlanFlatRateId)
        );

        assertNotNull(thrownException.getReferencedWarning());
        assertEquals("Reference exists,RatePlanFlatRate", thrownException.getMessage());
    }
    @Test
    void getRatePlanFlatRateCountShouldReturnCorrectCount() {
        // Arrange
        long expectedCount = 42L;

        // Mock the service method to return the expected count
        when(ratePlanFlatRateService.getRatePlanFlatRateCount()).thenReturn(expectedCount);

        // Act
        ResponseEntity<Long> response = ratePlanFlatRateResource.getRatePlanFlatRateCount();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedCount);

        // Verify that the service method was called
        verify(ratePlanFlatRateService).getRatePlanFlatRateCount();
    }
}
