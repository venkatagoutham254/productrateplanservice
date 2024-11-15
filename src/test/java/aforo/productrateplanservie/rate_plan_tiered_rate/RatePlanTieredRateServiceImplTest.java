package aforo.productrateplanservie.rate_plan_tiered_rate;

import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.CreateRatePlanTieredRateDetailsRequest;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetails;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatePlanTieredRateServiceImplTest {

    @Mock
    private RatePlanTieredRateRepository ratePlanTieredRateRepository;

    @Mock
    private RatePlanRepository ratePlanRepository;

    @Mock
    private RatePlanTieredRateMapper ratePlanTieredRateMapper;

    @Mock
    private RatePlanTieredRateDetailsRepository ratePlanTieredRateDetailsRepository;

    @InjectMocks
    private RatePlanTieredRateServiceImpl ratePlanTieredRateService;

    @Test
    void testFindAll() {
        // Arrange
        Pageable pageable = Pageable.ofSize(10);
        RatePlanTieredRate ratePlanTieredRate = new RatePlanTieredRate();
        ratePlanTieredRate.setRatePlanTieredRateId(1L);
        Page<RatePlanTieredRate> ratePlanPage = new PageImpl<>(List.of(ratePlanTieredRate));

        when(ratePlanTieredRateRepository.findAll(pageable)).thenReturn(ratePlanPage);
        when(ratePlanTieredRateMapper.updateRatePlanTieredRateDTO(any(), any())).thenReturn(new RatePlanTieredRateDTO());

        // Act
        Page<RatePlanTieredRateDTO> result = ratePlanTieredRateService.findAll(null, pageable);

        // Assert
        assertThat(result).hasSize(1);
        verify(ratePlanTieredRateRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGet() {
        // Arrange
        RatePlanTieredRate ratePlanTieredRate = new RatePlanTieredRate();
        ratePlanTieredRate.setRatePlanTieredRateId(1L);
        RatePlanTieredRateDTO dto = new RatePlanTieredRateDTO();

        when(ratePlanTieredRateRepository.findById(1L)).thenReturn(Optional.of(ratePlanTieredRate));
        when(ratePlanTieredRateMapper.updateRatePlanTieredRateDTO(ratePlanTieredRate, new RatePlanTieredRateDTO())).thenReturn(dto);

        // Act
        RatePlanTieredRateDTO result = ratePlanTieredRateService.get(1L);

        // Assert
        assertThat(result).isNotNull();
        verify(ratePlanTieredRateRepository, times(1)).findById(1L);
    }
    @Test
    void testCreate() {
        // Arrange
        Long ratePlanId = 1L;

        // Mock request with details
        CreateRatePlanTieredRateRequest request = new CreateRatePlanTieredRateRequest();
        CreateRatePlanTieredRateDetailsRequest detailsRequest = new CreateRatePlanTieredRateDetailsRequest();
        detailsRequest.setTierStart(BigDecimal.valueOf(0.0));
        detailsRequest.setTierEnd(BigDecimal.valueOf(100.0));
        detailsRequest.setTierRate(BigDecimal.valueOf(10.0));
        request.setRatePlanTieredRateDetails(Set.of(detailsRequest)); // Provide a non-empty set

        RatePlan ratePlan = new RatePlan();
        ratePlan.setRatePlanId(ratePlanId);

        RatePlanTieredRate ratePlanTieredRate = new RatePlanTieredRate();
        ratePlanTieredRate.setRatePlanTieredRateId(1L);

        RatePlanTieredRateDetails ratePlanTieredRateDetails = new RatePlanTieredRateDetails();
        ratePlanTieredRateDetails.setTierRate(BigDecimal.valueOf(10.0));

        when(ratePlanRepository.findById(ratePlanId)).thenReturn(Optional.of(ratePlan));
        when(ratePlanTieredRateMapper.mapToRatePlanTieredRate(any(CreateRatePlanTieredRateRequest.class)))
                .thenReturn(ratePlanTieredRate);
        when(ratePlanTieredRateRepository.save(ratePlanTieredRate)).thenReturn(ratePlanTieredRate);
        when(ratePlanTieredRateMapper.mapToRatePlanTieredRateDetails(any(CreateRatePlanTieredRateDetailsRequest.class)))
                .thenReturn(ratePlanTieredRateDetails);

        // Act
        Long result = ratePlanTieredRateService.create(ratePlanId, request);

        // Assert
        assertThat(result).isEqualTo(1L);
        verify(ratePlanTieredRateRepository, times(1)).save(ratePlanTieredRate);
        verify(ratePlanTieredRateDetailsRepository, times(1)).save(ratePlanTieredRateDetails);
    }


    @Test
    void testUpdate() {
        // Arrange
        Long ratePlanId = 1L;
        Long ratePlanTieredRateId = 1L;
        UpdateRatePlanTieredRateRequest request = new UpdateRatePlanTieredRateRequest();
        request.setRatePlanTieredDescription("Updated Description");

        RatePlanTieredRate existingRatePlanTieredRate = new RatePlanTieredRate();
        existingRatePlanTieredRate.setRatePlanTieredRateId(ratePlanTieredRateId);

        when(ratePlanRepository.existsById(ratePlanId)).thenReturn(true);
        when(ratePlanTieredRateRepository.findById(ratePlanTieredRateId)).thenReturn(Optional.of(existingRatePlanTieredRate));

        // Act
        ratePlanTieredRateService.update(ratePlanId, ratePlanTieredRateId, request);

        // Assert
        verify(ratePlanTieredRateRepository, times(1)).save(existingRatePlanTieredRate);
        assertThat(existingRatePlanTieredRate.getRatePlanTieredDescription()).isEqualTo("Updated Description");
    }

    @Test
    void testDelete() {
        // Arrange
        Long ratePlanTieredRateId = 1L;

        when(ratePlanTieredRateRepository.findById(ratePlanTieredRateId)).thenReturn(Optional.of(new RatePlanTieredRate()));

        // Act
        ratePlanTieredRateService.delete(ratePlanTieredRateId);

        // Assert
        verify(ratePlanTieredRateRepository, times(1)).delete(any(RatePlanTieredRate.class));
    }

    @Test
    void testDeleteThrowsExceptionWhenNotFound() {
        // Arrange
        Long ratePlanTieredRateId = 1L;

        when(ratePlanTieredRateRepository.findById(ratePlanTieredRateId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> ratePlanTieredRateService.delete(ratePlanTieredRateId));
    }
}
