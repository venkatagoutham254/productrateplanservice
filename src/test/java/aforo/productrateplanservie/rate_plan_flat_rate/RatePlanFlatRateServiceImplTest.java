package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetailsRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RatePlanFlatRateServiceImplTest {

    @InjectMocks
    private RatePlanFlatRateServiceImpl ratePlanFlatRateService;

    @Mock
    private RatePlanFlatRateRepository ratePlanFlatRateRepository;

    @Mock
    private RatePlanRepository ratePlanRepository;

    @Mock
    private RatePlanFlatRateDetailsRepository ratePlanFlatRateDetailsRepository;

    @Mock
    private RatePlanFlatRateMapper ratePlanFlatRateMapper;

    private RatePlan ratePlan;
    private RatePlanFlatRate ratePlanFlatRate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock data
        ratePlan = new RatePlan();
        ratePlan.setRatePlanId(1L);

        ratePlanFlatRate = new RatePlanFlatRate();
        ratePlanFlatRate.setRatePlanFlatRateId(1L);
        ratePlanFlatRate.setRatePlan(ratePlan);
    }

    @Test
    void testFindAll() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<RatePlanFlatRate> page = new PageImpl<>(List.of(ratePlanFlatRate));
        when(ratePlanFlatRateRepository.findAll(pageable)).thenReturn(page);

        Page<RatePlanFlatRateDTO> result = ratePlanFlatRateService.findAll(null, pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(ratePlanFlatRateRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGet() {
        when(ratePlanFlatRateRepository.findById(1L)).thenReturn(Optional.of(ratePlanFlatRate));
        when(ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(eq(ratePlanFlatRate), any())).thenReturn(new RatePlanFlatRateDTO());

        RatePlanFlatRateDTO result = ratePlanFlatRateService.get(1L);

        assertThat(result).isNotNull();
        verify(ratePlanFlatRateRepository, times(1)).findById(1L);
    }

    @Test
    void testGetNotFound() {
        when(ratePlanFlatRateRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ratePlanFlatRateService.get(1L));
    }

    @Test
    void testCreate() {
        // Arrange: Prepare the mock data and behavior
        Long ratePlanId = 1L;

        // Mock RatePlan
        RatePlan ratePlan = new RatePlan();
        ratePlan.setRatePlanId(ratePlanId);

        // Mock Request DTO
        CreateRatePlanFlatRateRequest request = new CreateRatePlanFlatRateRequest();
        request.setRatePlanFlatDescription("Test Flat Rate");
        request.setDescription("Test Description");

        // Mock Entity
        RatePlanFlatRate ratePlanFlatRate = new RatePlanFlatRate();
        ratePlanFlatRate.setRatePlanFlatDescription("Test Flat Rate");
        ratePlanFlatRate.setDescription("Test Description");

        // Mock Saved Entity with ID
        RatePlanFlatRate savedEntity = new RatePlanFlatRate();
        savedEntity.setRatePlanFlatRateId(1L);
        savedEntity.setRatePlanFlatDescription("Test Flat Rate");
        savedEntity.setDescription("Test Description");

        // Mock Behavior
        when(ratePlanRepository.findById(ratePlanId)).thenReturn(Optional.of(ratePlan));
        when(ratePlanFlatRateMapper.mapToEntity(request, ratePlan)).thenReturn(ratePlanFlatRate);
        when(ratePlanFlatRateRepository.save(ratePlanFlatRate)).thenReturn(savedEntity);

        // Act: Call the method under test
        Long result = ratePlanFlatRateService.create(ratePlanId, request);

        // Assert: Validate the result and interactions
        assertThat(result).isEqualTo(1L); // Check returned ID
        verify(ratePlanRepository, times(1)).findById(ratePlanId);
        verify(ratePlanFlatRateMapper, times(1)).mapToEntity(request, ratePlan);
        verify(ratePlanFlatRateRepository, times(1)).save(ratePlanFlatRate);
    }




    @Test
    void testUpdate() {
        UpdateRatePlanFlatRateRequest updateRequest = new UpdateRatePlanFlatRateRequest();
        updateRequest.setRatePlanFlatDescription("Updated Description");
        when(ratePlanRepository.existsById(1L)).thenReturn(true);
        when(ratePlanFlatRateRepository.findById(1L)).thenReturn(Optional.of(ratePlanFlatRate));

        ratePlanFlatRateService.update(1L, 1L, updateRequest);

        verify(ratePlanFlatRateRepository, times(1)).save(ratePlanFlatRate);
    }

    @Test
    void testUpdateNotFound() {
        UpdateRatePlanFlatRateRequest updateRequest = new UpdateRatePlanFlatRateRequest();
        when(ratePlanRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> ratePlanFlatRateService.update(1L, 1L, updateRequest));
    }

    @Test
    void testDelete() {
        when(ratePlanFlatRateRepository.findById(1L)).thenReturn(Optional.of(ratePlanFlatRate));

        ratePlanFlatRateService.delete(1L);

        verify(ratePlanFlatRateRepository, times(1)).delete(ratePlanFlatRate);
    }

    @Test
    void testDeleteNotFound() {
        when(ratePlanFlatRateRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ratePlanFlatRateService.delete(1L));
    }
    @Test
    void getRatePlanFlatRateCountShouldReturnCorrectCount() {
        // Arrange
        long expectedCount = 42L;

        // Mock the repository count method
        when(ratePlanFlatRateRepository.count()).thenReturn(expectedCount);

        // Act
        long actualCount = ratePlanFlatRateService.getRatePlanFlatRateCount();

        // Assert
        assertThat(actualCount).isEqualTo(expectedCount);

        // Verify that the repository count method was called
        verify(ratePlanFlatRateRepository, times(1)).count();
    }
}
