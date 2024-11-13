package aforo.productrateplanservie.rate_plan_freemium_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetails;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetailsDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RatePlanFreemiumRateMapper {

    // Mapping from DTO to Entity without requiring ratePlanFreemiumRateId
    @Mapping(target = "ratePlanFreemiumRateId", ignore = true)
    @Mapping(target = "freemiumRateDescription", source = "freemiumRateDescription")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "ratePlan", ignore = true) // Set manually after mapping
    @Mapping(target = "ratePlanFreemiumRateDetails", ignore = true) // Handle manually in after-mapping
    void updateRatePlanFreemiumRate(RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO, @MappingTarget RatePlanFreemiumRate ratePlanFreemiumRate);

    // After-mapping logic to set RatePlan and update nested details
    @AfterMapping
    default void afterUpdateRatePlanFreemiumRate(RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO, @MappingTarget RatePlanFreemiumRate ratePlanFreemiumRate, @Context RatePlan ratePlan) {
        ratePlanFreemiumRate.setRatePlan(ratePlan);

        if (ratePlanFreemiumRateDTO.getRatePlanFreemiumRateDetailsDTO() != null) {
            Set<RatePlanFreemiumRateDetails> updatedDetails = ratePlanFreemiumRateDTO.getRatePlanFreemiumRateDetailsDTO().stream()
                    .map(this::mapDetailDTOToEntity)
                    .peek(detail -> detail.setRatePlanFreemiumRate(ratePlanFreemiumRate))
                    .collect(Collectors.toSet());

            // Update the existing details with the provided DTOs
            Set<RatePlanFreemiumRateDetails> existingDetails = ratePlanFreemiumRate.getRatePlanFreemiumRateDetails();

            // Update or add new details
            for (RatePlanFreemiumRateDetails newDetail : updatedDetails) {
                RatePlanFreemiumRateDetails existingDetail = existingDetails.stream()
                        .filter(d -> d.getId() != null && d.getId().equals(newDetail.getId()))
                        .findFirst()
                        .orElse(null);

                if (existingDetail != null) {
                    existingDetail.setFreemiumMaxUnitQuantity(newDetail.getFreemiumMaxUnitQuantity());
                } else {
                    // Add new details if they don't exist
                    existingDetails.add(newDetail);
                }
            }

            // Remove any orphaned details not included in the DTO
            existingDetails.removeIf(existingDetail -> updatedDetails.stream()
                    .noneMatch(d -> d.getId() != null && d.getId().equals(existingDetail.getId())));

            // Assign updated set back to the entity
            ratePlanFreemiumRate.setRatePlanFreemiumRateDetails(existingDetails);
        }
    }

    // Mapping from Entity to DTO, ratePlanId is set after mapping
    @Mapping(target = "ratePlanId", ignore = true) // Set manually after mapping
    @Mapping(target = "ratePlanFreemiumRateDetailsDTO", source = "ratePlanFreemiumRateDetails")
    RatePlanFreemiumRateDTO updateRatePlanFreemiumRateDTO(RatePlanFreemiumRate ratePlanFreemiumRate, @MappingTarget RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO);

    // After-mapping to set ratePlanId and other details
    @AfterMapping
    default void afterUpdateRatePlanFreemiumRateDTO(RatePlanFreemiumRate ratePlanFreemiumRate, @MappingTarget RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO) {
        ratePlanFreemiumRateDTO.setRatePlanId(ratePlanFreemiumRate.getRatePlan() != null ? ratePlanFreemiumRate.getRatePlan().getRatePlanId() : null);
    }

    // Helper method to map RatePlanFreemiumRateDetailsDTO to RatePlanFreemiumRateDetails entity
    default RatePlanFreemiumRateDetails mapDetailDTOToEntity(RatePlanFreemiumRateDetailsDTO detailsDTO) {
        RatePlanFreemiumRateDetails details = new RatePlanFreemiumRateDetails();
        details.setId(detailsDTO.getId());
        details.setFreemiumMaxUnitQuantity(detailsDTO.getFreemiumMaxUnitQuantity());
        return details;
    }

    // Helper method to map RatePlanFreemiumRateDetails entity to RatePlanFreemiumRateDetailsDTO
    default RatePlanFreemiumRateDetailsDTO mapDetailEntityToDTO(RatePlanFreemiumRateDetails details) {
        RatePlanFreemiumRateDetailsDTO detailsDTO = new RatePlanFreemiumRateDetailsDTO();
        detailsDTO.setId(details.getId());
        detailsDTO.setFreemiumMaxUnitQuantity(details.getFreemiumMaxUnitQuantity());
        detailsDTO.setRatePlanFreemiumRateId(details.getRatePlanFreemiumRate() != null ? details.getRatePlanFreemiumRate().getRatePlanFreemiumRateId() : null);
        detailsDTO.setDateCreated(details.getDateCreated());
        detailsDTO.setLastUpdated(details.getLastUpdated());
        return detailsDTO;
    }

    // Mapping from Create DTO to Entity
    @Mapping(target = "ratePlanFreemiumRateId", ignore = true)
    @Mapping(target = "ratePlan", ignore = true)
    RatePlanFreemiumRate toEntity(CreateRatePlanFreemiumRateRequest dto);

    default RatePlanFreemiumRate mapToEntity(CreateRatePlanFreemiumRateRequest dto, RatePlan ratePlan) {
        // Use MapStruct to map basic fields
        RatePlanFreemiumRate ratePlanFreemiumRate = toEntity(dto);

        // Set ratePlan manually
        ratePlanFreemiumRate.setRatePlan(ratePlan);

        // Map and set nested RatePlanFreemiumRateDetails
        Set<RatePlanFreemiumRateDetails> details = dto.getRatePlanFreemiumRateDetailsDTO().stream()
                .map(detailDto -> {
                    RatePlanFreemiumRateDetails detail = new RatePlanFreemiumRateDetails();
                    detail.setFreemiumMaxUnitQuantity(detailDto.getFreemiumMaxUnitQuantity());
                    detail.setRatePlanFreemiumRate(ratePlanFreemiumRate);
                    return detail;
                })
                .collect(Collectors.toSet());

        ratePlanFreemiumRate.setRatePlanFreemiumRateDetails(details);
        return ratePlanFreemiumRate;
    }
}
