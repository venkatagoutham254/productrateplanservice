package aforo.productrateplanservie.rate_plan_tiered_rate;

import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.CreateRatePlanTieredRateDetailsRequest;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetails;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetailsRepository;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.UpdateRatePlanTieredRateDetailsRequest;
import aforo.productrateplanservie.util.enums.UnitCalculation;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
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
    void testUpdateRatePlanTieredRate() {
        // Arrange
        Long ratePlanId = 1L;
        Long ratePlanTieredRateId = 1L;

        // Mock the update request
        UpdateRatePlanTieredRateRequest updateRequest = new UpdateRatePlanTieredRateRequest();
        updateRequest.setRatePlanTieredDescription("Updated Tiered Description");
        updateRequest.setDescription("Updated Description");
        updateRequest.setUnitType(UnitType.API);
        updateRequest.setUnitMeasurement(UnitMeasurement.MB);
        updateRequest.setUnitCalculation(UnitCalculation.DAILY);

        UpdateRatePlanTieredRateDetailsRequest detailRequest = new UpdateRatePlanTieredRateDetailsRequest();
        detailRequest.setTierNumber(1L);
        detailRequest.setTierStart(BigDecimal.valueOf(100.0));
        detailRequest.setTierRate(BigDecimal.valueOf(50.0));
        detailRequest.setTierEnd(BigDecimal.valueOf(200.0));
        updateRequest.setRatePlanTieredRateDetails(Set.of(detailRequest));

        // Mock the existing RatePlanTieredRate
        RatePlanTieredRate existingRatePlanTieredRate = new RatePlanTieredRate();
        existingRatePlanTieredRate.setRatePlanTieredRateId(ratePlanTieredRateId);
        existingRatePlanTieredRate.setRatePlanTieredDescription("Old Tiered Description");
        existingRatePlanTieredRate.setDescription("Old Description");
        existingRatePlanTieredRate.setUnitType(UnitType.API);
        existingRatePlanTieredRate.setUnitMeasurement(UnitMeasurement.MB);
        existingRatePlanTieredRate.setUnitCalculation(UnitCalculation.DAILY);

        RatePlanTieredRateDetails existingDetail = new RatePlanTieredRateDetails();
        existingDetail.setTierNumber(1L);
        existingDetail.setTierStart(BigDecimal.valueOf(50.0));
        existingDetail.setTierRate(BigDecimal.valueOf(25.0));
        existingDetail.setTierEnd(BigDecimal.valueOf(150.0));
        existingRatePlanTieredRate.setRatePlanTieredRateDetails(new HashSet<>(Set.of(existingDetail)));

        // Mock repository behavior
        when(ratePlanRepository.existsById(ratePlanId)).thenReturn(true);
        when(ratePlanTieredRateRepository.findById(ratePlanTieredRateId)).thenReturn(Optional.of(existingRatePlanTieredRate));

        // Act
        ratePlanTieredRateService.update(ratePlanId, ratePlanTieredRateId, updateRequest);

        // Assert
        verify(ratePlanRepository, times(1)).existsById(ratePlanId);
        verify(ratePlanTieredRateRepository, times(1)).findById(ratePlanTieredRateId);
        verify(ratePlanTieredRateRepository, times(1)).save(existingRatePlanTieredRate);

        // Validate updated main fields
        assertThat(existingRatePlanTieredRate.getRatePlanTieredDescription()).isEqualTo("Updated Tiered Description");
        assertThat(existingRatePlanTieredRate.getDescription()).isEqualTo("Updated Description");
        assertThat(existingRatePlanTieredRate.getUnitType()).isEqualTo(UnitType.API);
        assertThat(existingRatePlanTieredRate.getUnitMeasurement()).isEqualTo(UnitMeasurement.MB);
        assertThat(existingRatePlanTieredRate.getUnitCalculation()).isEqualTo(UnitCalculation.DAILY);

        // Validate updated details
        RatePlanTieredRateDetails updatedDetail = existingRatePlanTieredRate.getRatePlanTieredRateDetails().iterator().next();
        assertThat(updatedDetail.getTierNumber()).isEqualTo(1L);
        assertThat(updatedDetail.getTierStart()).isEqualTo(BigDecimal.valueOf(100.0));
        assertThat(updatedDetail.getTierRate()).isEqualTo(BigDecimal.valueOf(50.0));
        assertThat(updatedDetail.getTierEnd()).isEqualTo(BigDecimal.valueOf(200.0));
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
