package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetails;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetailsRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.exception.ReferencedWarning;
import aforo.productrateplanservie.rate_plan_flat_rate_details.UpdateRatePlanFlatRateDetailsRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class RatePlanFlatRateServiceImpl implements RatePlanFlatRateService {

    private final RatePlanFlatRateRepository ratePlanFlatRateRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanFlatRateDetailsRepository ratePlanFlatRateDetailsRepository;
    private final RatePlanFlatRateMapper ratePlanFlatRateMapper;

    public RatePlanFlatRateServiceImpl(final RatePlanFlatRateRepository ratePlanFlatRateRepository,
                                       final RatePlanRepository ratePlanRepository,
                                       final RatePlanFlatRateDetailsRepository ratePlanFlatRateDetailsRepository,
                                       final RatePlanFlatRateMapper ratePlanFlatRateMapper) {
        this.ratePlanFlatRateRepository = ratePlanFlatRateRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanFlatRateDetailsRepository = ratePlanFlatRateDetailsRepository;
        this.ratePlanFlatRateMapper = ratePlanFlatRateMapper;
    }

    @Override
    public Page<RatePlanFlatRateDTO> findAll(String filter, Pageable pageable) {
        Page<RatePlanFlatRate> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (NumberFormatException numberFormatException) {
                // Keep null - no parseable input
            }
            page = ratePlanFlatRateRepository.findAllByRatePlanFlatRateId(longFilter, pageable);
        } else {
            page = ratePlanFlatRateRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public RatePlanFlatRateDTO get(Long ratePlanFlatRateId) {
        return ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .orElseThrow(() -> new NotFoundException("RatePlanFlatRate not found with id: " + ratePlanFlatRateId));
    }

    @Override
    public Long create(Long ratePlanId, CreateRatePlanFlatRateRequest request) {
        // Validate if the RatePlan exists
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
                .orElseThrow(() -> new IllegalArgumentException("RatePlan with ID " + ratePlanId + " not found"));

        // Map the request to entity
        RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateMapper.mapToEntity(request, ratePlan);

        // Save the RatePlanFlatRate entity
        RatePlanFlatRate savedEntity = ratePlanFlatRateRepository.save(ratePlanFlatRate);

        // Return the ID of the saved entity
        return savedEntity.getRatePlanFlatRateId();
    }

    @Override
    @Transactional
    public void update(Long ratePlanId, Long ratePlanFlatRateId, @Valid UpdateRatePlanFlatRateRequest updateRequest) {
        // Check if RatePlan exists
        if (!ratePlanRepository.existsById(ratePlanId)) {
            throw new EntityNotFoundException("RatePlan with id " + ratePlanId + " not found");
        }

        // Retrieve the existing RatePlanFlatRate entity
        RatePlanFlatRate existingRatePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(() -> new EntityNotFoundException("RatePlanFlatRate with id " + ratePlanFlatRateId + " not found"));

        // Map existing entity to a DTO
        RatePlanFlatRateDTO ratePlanFlatRateDTO = new RatePlanFlatRateDTO();
        ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(existingRatePlanFlatRate, ratePlanFlatRateDTO);

        boolean isModified = false;

        // Update main RatePlanFlatRate fields
        if (updateRequest.getRatePlanFlatDescription() != null &&
                !updateRequest.getRatePlanFlatDescription().trim().isEmpty() &&
                !Objects.equals(ratePlanFlatRateDTO.getRatePlanFlatDescription(), updateRequest.getRatePlanFlatDescription())) {
            ratePlanFlatRateDTO.setRatePlanFlatDescription(updateRequest.getRatePlanFlatDescription());
            isModified = true;
        }

        if (updateRequest.getDescription() != null &&
                !Objects.equals(ratePlanFlatRateDTO.getDescription(), updateRequest.getDescription())) {
            ratePlanFlatRateDTO.setDescription(updateRequest.getDescription());
            isModified = true;
        }

        if (updateRequest.getUnitType() != null &&
                !Objects.equals(ratePlanFlatRateDTO.getUnitType(), updateRequest.getUnitType())) {
            ratePlanFlatRateDTO.setUnitType(updateRequest.getUnitType());
            isModified = true;
        }

        if (updateRequest.getUnitMeasurement() != null &&
                !Objects.equals(ratePlanFlatRateDTO.getUnitMeasurement(), updateRequest.getUnitMeasurement())) {
            ratePlanFlatRateDTO.setUnitMeasurement(updateRequest.getUnitMeasurement());
            isModified = true;
        }

        if (updateRequest.getFlatRateUnitCalculation() != null &&
                !Objects.equals(ratePlanFlatRateDTO.getFlatRateUnitCalculation(), updateRequest.getFlatRateUnitCalculation())) {
            ratePlanFlatRateDTO.setFlatRateUnitCalculation(updateRequest.getFlatRateUnitCalculation());
            isModified = true;
        }

        if (updateRequest.getMaxLimitFrequency() != null &&
                !Objects.equals(ratePlanFlatRateDTO.getMaxLimitFrequency(), updateRequest.getMaxLimitFrequency())) {
            ratePlanFlatRateDTO.setMaxLimitFrequency(updateRequest.getMaxLimitFrequency());
            isModified = true;
        }

        // Update nested RatePlanFlatRateDetails
        if (updateRequest.getRatePlanFlatRateDetails() != null) {
            isModified |= updateRatePlanFlatRateDetails(existingRatePlanFlatRate, updateRequest.getRatePlanFlatRateDetails());
        }

        // Map updated DTO back to the entity and save only if modifications were made
        if (isModified) {
            ratePlanFlatRateMapper.updateRatePlanFlatRate(ratePlanFlatRateDTO, existingRatePlanFlatRate);
            ratePlanFlatRateRepository.save(existingRatePlanFlatRate);
        }
    }

    private boolean updateRatePlanFlatRateDetails(RatePlanFlatRate existingRatePlanFlatRate,
                                                  List<UpdateRatePlanFlatRateDetailsRequest> detailsRequests) {
        boolean isModified = false;
        Set<RatePlanFlatRateDetails> existingDetails = existingRatePlanFlatRate.getRatePlanFlatRateDetails();

        // Update existing details or throw exception if not found
        for (UpdateRatePlanFlatRateDetailsRequest detailRequest : detailsRequests) {
            RatePlanFlatRateDetails detail = existingDetails.stream()
                    .filter(d -> d.getId().equals(detailRequest.getId()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("RatePlanFlatRateDetails not found for ID: " + detailRequest.getId()));

            if (detailRequest.getUnitRate() != null && !detailRequest.getUnitRate().equals(detail.getUnitRate())) {
                detail.setUnitRate(detailRequest.getUnitRate());
                isModified = true;
            }
            if (detailRequest.getMaxLimit() != null && !detailRequest.getMaxLimit().equals(detail.getMaxLimit())) {
                detail.setMaxLimit(detailRequest.getMaxLimit());
                isModified = true;
            }
        }

        // Save any new details provided in the request
        for (UpdateRatePlanFlatRateDetailsRequest newDetailRequest : detailsRequests) {
            if (newDetailRequest.getId() == null) {
                RatePlanFlatRateDetails newDetail = new RatePlanFlatRateDetails();
                newDetail.setUnitRate(newDetailRequest.getUnitRate());
                newDetail.setMaxLimit(newDetailRequest.getMaxLimit());
                newDetail.setRatePlanFlatRate(existingRatePlanFlatRate);
                existingRatePlanFlatRate.getRatePlanFlatRateDetails().add(newDetail);
                isModified = true;
            }
        }

        return isModified;
    }






    @Override
    @Transactional
    public void delete(Long ratePlanFlatRateId) {
        RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanFlatRate not found with id: " + ratePlanFlatRateId));

        ratePlanFlatRateRepository.delete(ratePlanFlatRate); // Details are deleted automatically
    }


    @Override
    public Page<RatePlanFlatRateDTO> findAllByRatePlanId(Long ratePlanId, Pageable pageable) {
        Page<RatePlanFlatRate> page = ratePlanFlatRateRepository.findAllByRatePlanRatePlanId(ratePlanId, pageable);
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }


    public Optional<RatePlanFlatRateDTO> findFirstByRatePlanId(Long ratePlanId) {
        Optional<RatePlan> ratePlanOpt = ratePlanRepository.findById(ratePlanId);
        if (ratePlanOpt.isEmpty()) {
            System.out.println("RatePlan not found with id: " + ratePlanId);
            return Optional.empty();
        }
        System.out.println("RatePlan found: " + ratePlanOpt.get());

        Optional<RatePlanFlatRate> ratePlanFlatRateOpt = ratePlanFlatRateRepository.findFirstByRatePlan(ratePlanOpt.get());
        if (ratePlanFlatRateOpt.isEmpty()) {
            System.out.println("RatePlanFlatRate not found for ratePlanId: " + ratePlanId);
            return Optional.empty();
        }
        return ratePlanFlatRateOpt.map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()));
    }



}
