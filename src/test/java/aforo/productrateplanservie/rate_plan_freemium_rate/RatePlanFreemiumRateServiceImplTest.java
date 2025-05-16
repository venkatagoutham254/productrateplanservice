package aforo.productrateplanservie.rate_plan_freemium_rate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import aforo.productrateplanservice.exception.NotFoundException;
import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import aforo.productrateplanservice.rate_plan_freemium_rate.CreateRatePlanFreemiumRateRequest;
import aforo.productrateplanservice.rate_plan_freemium_rate.RatePlanFreemiumRate;
import aforo.productrateplanservice.rate_plan_freemium_rate.RatePlanFreemiumRateDTO;
import aforo.productrateplanservice.rate_plan_freemium_rate.RatePlanFreemiumRateMapper;
import aforo.productrateplanservice.rate_plan_freemium_rate.RatePlanFreemiumRateRepository;
import aforo.productrateplanservice.rate_plan_freemium_rate.RatePlanFreemiumRateServiceImpl;
import aforo.productrateplanservice.rate_plan_freemium_rate.UpdateRatePlanFreemiumRateRequest;
import aforo.productrateplanservice.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetailsRepository;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatePlanFreemiumRateServiceImplTest {

    @Mock
    private RatePlanFreemiumRateRepository ratePlanFreemiumRateRepository;

    @Mock
    private RatePlanRepository ratePlanRepository;

    @Mock
    private RatePlanFreemiumRateMapper ratePlanFreemiumRateMapper;


    @Mock
    private RatePlanFreemiumRateDetailsRepository ratePlanFreemiumRateDetailsRepository;

    @InjectMocks
    private RatePlanFreemiumRateServiceImpl ratePlanFreemiumRateService;

    @Test
    void testFindAll() {
        // Arrange
        Pageable pageable = Pageable.ofSize(10);
        RatePlanFreemiumRate ratePlanFreemiumRate = new RatePlanFreemiumRate();
        ratePlanFreemiumRate.setRatePlanFreemiumRateId(1L);
        Page<RatePlanFreemiumRate> ratePlanPage = new PageImpl<>(List.of(ratePlanFreemiumRate));

        when(ratePlanFreemiumRateRepository.findAll(pageable)).thenReturn(ratePlanPage);
        when(ratePlanFreemiumRateMapper.updateRatePlanFreemiumRateDTO(any(RatePlanFreemiumRate.class), any(RatePlanFreemiumRateDTO.class)))
                .thenReturn(new RatePlanFreemiumRateDTO());

        // Act
        Page<RatePlanFreemiumRateDTO> result = ratePlanFreemiumRateService.findAll(null, pageable);

        // Assert
        assertThat(result).hasSize(1);
        verify(ratePlanFreemiumRateRepository, times(1)).findAll(pageable);
        verify(ratePlanFreemiumRateMapper, times(1)).updateRatePlanFreemiumRateDTO(any(RatePlanFreemiumRate.class), any(RatePlanFreemiumRateDTO.class));
    }

    @Test
    void testUpdate() {
        // Arrange
        Long ratePlanId = 1L;
        Long ratePlanFreemiumRateId = 1L;
        UpdateRatePlanFreemiumRateRequest request = new UpdateRatePlanFreemiumRateRequest();
        request.setFreemiumRateDescription("Updated Description");

        RatePlanFreemiumRate existingRatePlanFreemiumRate = new RatePlanFreemiumRate();
        existingRatePlanFreemiumRate.setRatePlanFreemiumRateId(ratePlanFreemiumRateId);

        when(ratePlanRepository.existsById(ratePlanId)).thenReturn(true);
        when(ratePlanFreemiumRateRepository.findById(ratePlanFreemiumRateId)).thenReturn(Optional.of(existingRatePlanFreemiumRate));

        // Remove unnecessary stubbing
        // When stubbing is required, ensure it directly relates to the test logic.

        // Act
        ratePlanFreemiumRateService.update(ratePlanId, ratePlanFreemiumRateId, request);

        // Assert
        verify(ratePlanFreemiumRateRepository, times(1)).save(existingRatePlanFreemiumRate);
        assertThat(existingRatePlanFreemiumRate.getFreemiumRateDescription()).isEqualTo("Updated Description");
    }

    @Test
    void testGet() {
        RatePlanFreemiumRate ratePlanFreemiumRate = new RatePlanFreemiumRate();
        ratePlanFreemiumRate.setRatePlanFreemiumRateId(1L);
        RatePlanFreemiumRateDTO dto = new RatePlanFreemiumRateDTO();

        when(ratePlanFreemiumRateRepository.findById(1L)).thenReturn(Optional.of(ratePlanFreemiumRate));
        when(ratePlanFreemiumRateMapper.updateRatePlanFreemiumRateDTO(ratePlanFreemiumRate, new RatePlanFreemiumRateDTO())).thenReturn(dto);

        RatePlanFreemiumRateDTO result = ratePlanFreemiumRateService.get(1L);

        assertThat(result).isNotNull();
        verify(ratePlanFreemiumRateRepository, times(1)).findById(1L);
    }

    @Test
    void testCreate() {
        Long ratePlanId = 1L;
        CreateRatePlanFreemiumRateRequest request = new CreateRatePlanFreemiumRateRequest();
        RatePlanFreemiumRate ratePlanFreemiumRate = new RatePlanFreemiumRate();
        ratePlanFreemiumRate.setRatePlanFreemiumRateId(1L);

        when(ratePlanFreemiumRateMapper.mapToEntity(any(), any())).thenReturn(ratePlanFreemiumRate);
        when(ratePlanFreemiumRateRepository.save(ratePlanFreemiumRate)).thenReturn(ratePlanFreemiumRate);

        Long result = ratePlanFreemiumRateService.create(ratePlanId, request);

        assertThat(result).isEqualTo(1L);
        verify(ratePlanFreemiumRateRepository, times(1)).save(ratePlanFreemiumRate);
    }


    @Test
    void testDelete() {
        Long ratePlanFreemiumRateId = 1L;

        when(ratePlanFreemiumRateRepository.existsById(ratePlanFreemiumRateId)).thenReturn(true);

        ratePlanFreemiumRateService.delete(ratePlanFreemiumRateId);

        verify(ratePlanFreemiumRateRepository, times(1)).deleteById(ratePlanFreemiumRateId);
    }



    @Test
    void testDeleteThrowsExceptionWhenNotFound() {
        Long ratePlanFreemiumRateId = 1L;

        when(ratePlanFreemiumRateRepository.existsById(ratePlanFreemiumRateId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> ratePlanFreemiumRateService.delete(ratePlanFreemiumRateId));
    }
    @Test
    void testGetRatePlanFreemiumRateCount() {
        // Arrange
        long expectedCount = 42L; // Example count value
        when(ratePlanFreemiumRateRepository.count()).thenReturn(expectedCount);

        // Act
        long actualCount = ratePlanFreemiumRateService.getRatePlanFreemiumRateCount();

        // Assert
        assertThat(actualCount).isEqualTo(expectedCount);
        verify(ratePlanFreemiumRateRepository, times(1)).count();
    }

}
