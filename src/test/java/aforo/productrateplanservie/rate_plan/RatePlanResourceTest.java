package aforo.productrateplanservie.rate_plan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import aforo.productrateplanservice.model.SimpleValue;
import aforo.productrateplanservice.rate_plan.CreateRatePlanRequest;
import aforo.productrateplanservice.rate_plan.RatePlanAssembler;
import aforo.productrateplanservice.rate_plan.RatePlanDTO;
import aforo.productrateplanservice.rate_plan.RatePlanResource;
import aforo.productrateplanservice.rate_plan.RatePlanService;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class RatePlanResourceTest {

    @InjectMocks
    private RatePlanResource ratePlanResource;

    @Mock
    private RatePlanService ratePlanService;

    @Mock
    private RatePlanAssembler ratePlanAssembler;

    @Mock
    private PagedResourcesAssembler<RatePlanDTO> pagedResourcesAssembler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRatePlan() {
        Long ratePlanId = 1L;
        SimpleValue<Long> simpleValueMock = mock(SimpleValue.class); // Mocking SimpleValue
        when(simpleValueMock.getValue()).thenReturn(ratePlanId);
        EntityModel<SimpleValue<Long>> entityModel = EntityModel.of(simpleValueMock);

        when(ratePlanService.create(eq(123L), any(CreateRatePlanRequest.class))).thenReturn(ratePlanId);
        when(ratePlanAssembler.toSimpleModel(ratePlanId)).thenReturn(entityModel);

        ResponseEntity<EntityModel<SimpleValue<Long>>> response = ratePlanResource.createRatePlan(123L, new CreateRatePlanRequest());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(entityModel, response.getBody());
    }

    @Test
    void testGetRatePlan() {
        RatePlanDTO ratePlanDTO = new RatePlanDTO();
        EntityModel<RatePlanDTO> entityModel = EntityModel.of(ratePlanDTO);

        when(ratePlanService.get(1L)).thenReturn(ratePlanDTO);
        when(ratePlanAssembler.toModel(ratePlanDTO)).thenReturn(entityModel);

        ResponseEntity<EntityModel<RatePlanDTO>> response = ratePlanResource.getRatePlan(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entityModel, response.getBody());
    }

    @Test
    void testUpdateRatePlan() {
        Long ratePlanId = 1L;
        SimpleValue<Long> simpleValueMock = mock(SimpleValue.class); // Mocking SimpleValue
        EntityModel<SimpleValue<Long>> entityModel = EntityModel.of(simpleValueMock);

        when(ratePlanAssembler.toSimpleModel(ratePlanId)).thenReturn(entityModel);

        ResponseEntity<EntityModel<SimpleValue<Long>>> response = ratePlanResource.updateRatePlan(ratePlanId, new CreateRatePlanRequest());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entityModel, response.getBody());
        verify(ratePlanService, times(1)).update(eq(ratePlanId), any(CreateRatePlanRequest.class));
    }

    @Test
    void testDeleteRatePlan() {
        Long ratePlanId = 1L;

        ResponseEntity<Void> response = ratePlanResource.deleteRatePlan(ratePlanId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(ratePlanService, times(1)).delete(ratePlanId);
    }

    @Test
    void testGetAllRatePlansByProductId() {
        Page<RatePlanDTO> ratePlanPage = new PageImpl<>(Collections.singletonList(new RatePlanDTO()));
        PagedModel<EntityModel<RatePlanDTO>> pagedModel = PagedModel.of(Collections.emptyList(), new PagedModel.PageMetadata(1, 0, 1));

        when(ratePlanService.getRatePlansByProductId(eq(123L), eq("filter"), any(PageRequest.class))).thenReturn(ratePlanPage);
        when(pagedResourcesAssembler.toModel(eq(ratePlanPage), any(RatePlanAssembler.class))).thenReturn(pagedModel);

        ResponseEntity<PagedModel<EntityModel<RatePlanDTO>>> response = ratePlanResource.getAllRatePlansByProductId("filter", 123L, PageRequest.of(0, 20));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagedModel, response.getBody());
    }

    @Test
    void testGetSelectedRatePlanTypeId() {
        Long ratePlanId = 1L;
        String ratePlanType = "standard";
        Long typeId = 10L;

        when(ratePlanService.getSelectedRatePlanTypeId(ratePlanId, ratePlanType)).thenReturn(Optional.of(typeId));

        ResponseEntity<EntityModel<SimpleValue<Long>>> response = ratePlanResource.getSelectedRatePlanTypeId(ratePlanId, ratePlanType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(typeId, response.getBody().getContent().getValue());
    }
    @Test
    void testGetRatePlanCount() {
        // Mocking the service
        long expectedCount = 42;
        when(ratePlanService.getRatePlanCount()).thenReturn(expectedCount);

        // Calling the method under test
        ResponseEntity<Long> response = ratePlanResource.getRatePlanCount();

        // Verifying the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedCount, response.getBody());

        // Verifying the interaction
        verify(ratePlanService, times(1)).getRatePlanCount();
    }
}
