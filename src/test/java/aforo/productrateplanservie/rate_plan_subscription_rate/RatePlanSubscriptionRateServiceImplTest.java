package aforo.productrateplanservie.rate_plan_subscription_rate;

import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetails;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetailsRepository;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.UpdateRatePlanSubscriptionRateDetailsRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatePlanSubscriptionRateServiceImplTest {

    @Mock
    private RatePlanSubscriptionRateRepository ratePlanSubscriptionRateRepository;

    @Mock
    private RatePlanRepository ratePlanRepository;

    @Mock
    private RatePlanSubscriptionRateMapper ratePlanSubscriptionRateMapper;

    @Mock
    private RatePlanSubscriptionRateDetailsRepository ratePlanSubscriptionRateDetailsRepository;

    @InjectMocks
    private RatePlanSubscriptionRateServiceImpl ratePlanSubscriptionRateService;

    @Test
    void testFindAll() {
        // Arrange
        Pageable pageable = Pageable.ofSize(10);
        RatePlanSubscriptionRate ratePlanSubscriptionRate = new RatePlanSubscriptionRate();
        ratePlanSubscriptionRate.setRatePlanSubscriptionRateId(1L);
        Page<RatePlanSubscriptionRate> ratePlanPage = new PageImpl<>(List.of(ratePlanSubscriptionRate));

        when(ratePlanSubscriptionRateRepository.findAll(pageable)).thenReturn(ratePlanPage);
        when(ratePlanSubscriptionRateMapper.updateRatePlanSubscriptionRateDTO(any(), any()))
                .thenReturn(new RatePlanSubscriptionRateDTO());

        // Act
        Page<RatePlanSubscriptionRateDTO> result = ratePlanSubscriptionRateService.findAll(null, pageable);

        // Assert
        assertThat(result).hasSize(1);
        verify(ratePlanSubscriptionRateRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGet() {
        // Arrange
        RatePlanSubscriptionRate ratePlanSubscriptionRate = new RatePlanSubscriptionRate();
        ratePlanSubscriptionRate.setRatePlanSubscriptionRateId(1L);
        RatePlanSubscriptionRateDTO dto = new RatePlanSubscriptionRateDTO();

        when(ratePlanSubscriptionRateRepository.findById(1L)).thenReturn(Optional.of(ratePlanSubscriptionRate));
        when(ratePlanSubscriptionRateMapper.updateRatePlanSubscriptionRateDTO(ratePlanSubscriptionRate, new RatePlanSubscriptionRateDTO()))
                .thenReturn(dto);

        // Act
        RatePlanSubscriptionRateDTO result = ratePlanSubscriptionRateService.get(1L);

        // Assert
        assertThat(result).isNotNull();
        verify(ratePlanSubscriptionRateRepository, times(1)).findById(1L);
    }

    @Test
    void testCreate() {
        // Arrange
        Long ratePlanId = 1L;
        CreateRatePlanSubscriptionRateRequest request = new CreateRatePlanSubscriptionRateRequest();
        RatePlanSubscriptionRate ratePlanSubscriptionRate = new RatePlanSubscriptionRate();
        ratePlanSubscriptionRate.setRatePlanSubscriptionRateId(1L);
        RatePlan ratePlan = new RatePlan(ratePlanId);

        when(ratePlanRepository.findById(ratePlanId)).thenReturn(Optional.of(ratePlan));
        when(ratePlanSubscriptionRateMapper.mapToEntity(request, ratePlan)).thenReturn(ratePlanSubscriptionRate);
        when(ratePlanSubscriptionRateRepository.save(ratePlanSubscriptionRate)).thenReturn(ratePlanSubscriptionRate);

        // Act
        Long result = ratePlanSubscriptionRateService.create(ratePlanId, request);

        // Assert
        assertThat(result).isEqualTo(1L);
        verify(ratePlanSubscriptionRateRepository, times(1)).save(ratePlanSubscriptionRate);
    }
    @Test
    void testUpdate() {
        // Arrange
        Long ratePlanId = 1L;
        Long ratePlanSubscriptionRateId = 1L;

        // Prepare update request
        UpdateRatePlanSubscriptionRateRequest request = new UpdateRatePlanSubscriptionRateRequest();
        request.setRatePlanSubscriptionDescription("Updated Description");
        request.setDescription("Updated RatePlan Description");

        UpdateRatePlanSubscriptionRateDetailsRequest detailsRequest = new UpdateRatePlanSubscriptionRateDetailsRequest();
        detailsRequest.setId(1L);
        detailsRequest.setUnitPriceFixed(BigDecimal.valueOf(50.0));
        detailsRequest.setSubscriptionMaxUnitQuantity(BigDecimal.valueOf(10L));
        request.setRatePlanSubscriptionRateDetails(Set.of(detailsRequest));

        // Mock existing RatePlanSubscriptionRate and its details
        RatePlanSubscriptionRate existingRatePlanSubscriptionRate = new RatePlanSubscriptionRate();
        existingRatePlanSubscriptionRate.setRatePlanSubscriptionRateId(ratePlanSubscriptionRateId);
        existingRatePlanSubscriptionRate.setRatePlanSubscriptionDescription("Old Description");
        existingRatePlanSubscriptionRate.setDescription("Old RatePlan Description");

        RatePlanSubscriptionRateDetails existingDetail = new RatePlanSubscriptionRateDetails();
        existingDetail.setId(1L);
        existingDetail.setUnitPriceFixed(BigDecimal.valueOf(40.0));
        existingDetail.setSubscriptionMaxUnitQuantity(BigDecimal.valueOf(5L));
        existingDetail.setRatePlanSubscriptionRate(existingRatePlanSubscriptionRate);

        existingRatePlanSubscriptionRate.setRatePlanSubscriptionRateDetails(new HashSet<>(Set.of(existingDetail)));

        // Configure mocks
        when(ratePlanRepository.existsById(ratePlanId)).thenReturn(true);
        when(ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId))
                .thenReturn(Optional.of(existingRatePlanSubscriptionRate));

        // Act
        ratePlanSubscriptionRateService.update(ratePlanId, ratePlanSubscriptionRateId, request);

        // Assert
        verify(ratePlanRepository, times(1)).existsById(ratePlanId);
        verify(ratePlanSubscriptionRateRepository, times(1)).findById(ratePlanSubscriptionRateId);
        verify(ratePlanSubscriptionRateRepository, times(1)).save(existingRatePlanSubscriptionRate);

        // Verify main RatePlanSubscriptionRate fields
        assertThat(existingRatePlanSubscriptionRate.getRatePlanSubscriptionDescription())
                .isEqualTo("Updated Description");
        assertThat(existingRatePlanSubscriptionRate.getDescription())
                .isEqualTo("Updated RatePlan Description");

        // Verify updated details
        RatePlanSubscriptionRateDetails updatedDetail = existingRatePlanSubscriptionRate.getRatePlanSubscriptionRateDetails().iterator().next();
        assertThat(updatedDetail.getUnitPriceFixed()).isEqualTo(BigDecimal.valueOf(50.0));
        assertThat(updatedDetail.getSubscriptionMaxUnitQuantity()).isEqualTo(BigDecimal.valueOf(10L));
    }

    @Test
    void testDelete() {
        // Arrange
        Long ratePlanSubscriptionRateId = 1L;
        RatePlanSubscriptionRate ratePlanSubscriptionRate = new RatePlanSubscriptionRate();
        ratePlanSubscriptionRate.setRatePlanSubscriptionRateId(ratePlanSubscriptionRateId);

        // Mock repository behavior
        when(ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)).thenReturn(Optional.of(ratePlanSubscriptionRate));

        // Act
        ratePlanSubscriptionRateService.delete(ratePlanSubscriptionRateId);

        // Assert
        verify(ratePlanSubscriptionRateRepository, times(1)).findById(ratePlanSubscriptionRateId);
        verify(ratePlanSubscriptionRateRepository, times(1)).delete(ratePlanSubscriptionRate); // Correct method call
    }


    @Test
    void testDeleteThrowsExceptionWhenNotFound() {
        // Arrange
        Long ratePlanSubscriptionRateId = 1L;

        when(ratePlanSubscriptionRateRepository.findById(ratePlanSubscriptionRateId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class,
                () -> ratePlanSubscriptionRateService.delete(ratePlanSubscriptionRateId));
        assertThat(thrown.getMessage()).isEqualTo("Rate Plan Subscription Rate not found with id: " + ratePlanSubscriptionRateId);

        verify(ratePlanSubscriptionRateRepository, never()).deleteById(ratePlanSubscriptionRateId);
    }
    @Test
    void testGetRatePlanSubscriptionRateCount() {
        // Arrange
        long expectedCount = 42L; // Example value
        when(ratePlanSubscriptionRateRepository.count()).thenReturn(expectedCount);

        // Act
        long actualCount = ratePlanSubscriptionRateService.getRatePlanSubscriptionRateCount();

        // Assert
        assertThat(actualCount).isEqualTo(expectedCount);
        verify(ratePlanSubscriptionRateRepository, times(1)).count();
    }

}
