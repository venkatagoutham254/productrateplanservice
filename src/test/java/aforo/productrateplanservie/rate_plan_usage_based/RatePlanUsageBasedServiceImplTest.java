package aforo.productrateplanservie.rate_plan_usage_based;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import aforo.productrateplanservice.exception.NotFoundException;
import aforo.productrateplanservice.rate_plan.RatePlan;
import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import aforo.productrateplanservice.rate_plan_usage_based.CreateRatePlanUsageBasedRequest;
import aforo.productrateplanservice.rate_plan_usage_based.RatePlanUsageBased;
import aforo.productrateplanservice.rate_plan_usage_based.RatePlanUsageBasedDTO;
import aforo.productrateplanservice.rate_plan_usage_based.RatePlanUsageBasedMapper;
import aforo.productrateplanservice.rate_plan_usage_based.RatePlanUsageBasedRepository;
import aforo.productrateplanservice.rate_plan_usage_based.RatePlanUsageBasedServiceImpl;
import aforo.productrateplanservice.rate_plan_usage_based.UpdateRatePlanUsageBasedRequest;
import aforo.productrateplanservice.rate_plan_usage_based_rates.CreateRatePlanUsageBasedRatesRequest;
import aforo.productrateplanservice.rate_plan_usage_based_rates.RatePlanUsageBasedRates;
import aforo.productrateplanservice.rate_plan_usage_based_rates.RatePlanUsageBasedRatesRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatePlanUsageBasedServiceImplTest {

    @Mock
    private RatePlanUsageBasedRepository ratePlanUsageBasedRepository;

    @Mock
    private RatePlanRepository ratePlanRepository;

    @Mock
    private RatePlanUsageBasedMapper ratePlanUsageBasedMapper;

    @Mock
    private RatePlanUsageBasedRatesRepository ratePlanUsageBasedRatesRepository;

    @InjectMocks
    private RatePlanUsageBasedServiceImpl ratePlanUsageBasedService;

    @Test
    void testFindAll() {
        Pageable pageable = Pageable.ofSize(10);
        RatePlanUsageBased ratePlanUsageBased = new RatePlanUsageBased();
        ratePlanUsageBased.setRatePlanUsageRateId(1L);

        Page<RatePlanUsageBased> ratePlanPage = new PageImpl<>(List.of(ratePlanUsageBased));
        when(ratePlanUsageBasedRepository.findAll(pageable)).thenReturn(ratePlanPage);
        when(ratePlanUsageBasedMapper.updateRatePlanUsageBasedDTO(any(), any())).thenReturn(new RatePlanUsageBasedDTO());

        Page<RatePlanUsageBasedDTO> result = ratePlanUsageBasedService.findAll(null, pageable);

        assertThat(result).hasSize(1);
        verify(ratePlanUsageBasedRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGet() {
        RatePlanUsageBased ratePlanUsageBased = new RatePlanUsageBased();
        ratePlanUsageBased.setRatePlanUsageRateId(1L);

        when(ratePlanUsageBasedRepository.findById(1L)).thenReturn(Optional.of(ratePlanUsageBased));
        when(ratePlanUsageBasedMapper.updateRatePlanUsageBasedDTO(ratePlanUsageBased, new RatePlanUsageBasedDTO()))
                .thenReturn(new RatePlanUsageBasedDTO());

        RatePlanUsageBasedDTO result = ratePlanUsageBasedService.get(1L);

        assertThat(result).isNotNull();
        verify(ratePlanUsageBasedRepository, times(1)).findById(1L);
    }

    @Test
    void testCreate() {
        // Arrange
        Long ratePlanId = 1L;

        // Mock request
        CreateRatePlanUsageBasedRequest request = new CreateRatePlanUsageBasedRequest();
        CreateRatePlanUsageBasedRatesRequest rateRequest = new CreateRatePlanUsageBasedRatesRequest();
        rateRequest.setUnitRate(BigDecimal.valueOf(10.0));
        request.setRatePlanUsageBasedRatesDTO(Set.of(rateRequest));

        // Mock entities
        RatePlan ratePlan = new RatePlan();
        ratePlan.setRatePlanId(ratePlanId);

        RatePlanUsageBased ratePlanUsageBased = new RatePlanUsageBased();
        ratePlanUsageBased.setRatePlanUsageRateId(1L);

        RatePlanUsageBasedRates ratePlanUsageBasedRates = new RatePlanUsageBasedRates();
        ratePlanUsageBasedRates.setUnitRate(BigDecimal.valueOf(10.0));

        // Mocking mapper and repository behavior
        when(ratePlanRepository.findById(ratePlanId)).thenReturn(Optional.of(ratePlan));
        when(ratePlanUsageBasedMapper.toEntity(any(CreateRatePlanUsageBasedRequest.class))).thenReturn(ratePlanUsageBased);
        when(ratePlanUsageBasedRepository.save(ratePlanUsageBased)).thenReturn(ratePlanUsageBased);
        when(ratePlanUsageBasedMapper.toEntity(any(CreateRatePlanUsageBasedRatesRequest.class))).thenReturn(ratePlanUsageBasedRates);

        // Act
        Long result = ratePlanUsageBasedService.create(ratePlanId, request);

        // Assert
        assertThat(result).isEqualTo(1L);
        verify(ratePlanRepository, times(1)).findById(ratePlanId);
        verify(ratePlanUsageBasedRepository, times(1)).save(ratePlanUsageBased);
        verify(ratePlanUsageBasedRatesRepository, times(1)).save(ratePlanUsageBasedRates);
    }



    @Test
    void testUpdate() {
        Long ratePlanId = 1L;
        Long ratePlanUsageRateId = 1L;
        UpdateRatePlanUsageBasedRequest request = new UpdateRatePlanUsageBasedRequest();
        request.setRatePlanUsageDescription("Updated Description");
        RatePlanUsageBased existingRatePlanUsageBased = new RatePlanUsageBased();
        existingRatePlanUsageBased.setRatePlanUsageRateId(ratePlanUsageRateId);

        when(ratePlanRepository.existsById(ratePlanId)).thenReturn(true);
        when(ratePlanUsageBasedRepository.findById(ratePlanUsageRateId)).thenReturn(Optional.of(existingRatePlanUsageBased));

        ratePlanUsageBasedService.update(ratePlanId, ratePlanUsageRateId, request);

        verify(ratePlanUsageBasedRepository, times(1)).save(existingRatePlanUsageBased);
    }

    @Test
    void testDelete() {
        Long ratePlanUsageRateId = 1L;

        when(ratePlanUsageBasedRepository.existsById(ratePlanUsageRateId)).thenReturn(true);

        ratePlanUsageBasedService.delete(ratePlanUsageRateId);

        verify(ratePlanUsageBasedRepository, times(1)).deleteById(ratePlanUsageRateId);
    }

    @Test
    void testDeleteThrowsExceptionWhenNotFound() {
        Long ratePlanUsageRateId = 1L;

        when(ratePlanUsageBasedRepository.existsById(ratePlanUsageRateId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> ratePlanUsageBasedService.delete(ratePlanUsageRateId));
    }
    @Test
    void testGetRatePlanUsageBasedCount() {
        // Arrange
        long expectedCount = 42L;
        when(ratePlanUsageBasedRepository.count()).thenReturn(expectedCount);

        // Act
        long actualCount = ratePlanUsageBasedService.getRatePlanUsageBasedCount();

        // Assert
        assertThat(actualCount).isEqualTo(expectedCount);
        verify(ratePlanUsageBasedRepository, times(1)).count();
    }

}
