package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetails;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetailsDTO;
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
public interface RatePlanFlatRateMapper {

    // Map Create DTO to Entity without requiring ratePlanFlatRateId
    @Mapping(target = "ratePlanFlatRateId", ignore = true)
    @Mapping(target = "ratePlan", ignore = true) // Set manually in the service
    @Mapping(target = "ratePlanFlatRateDetails", ignore = true) // Handle manually after mapping
    RatePlanFlatRate toEntity(CreateRatePlanFlatRateRequest dto);

    // Custom method to handle nested RatePlanFlatRateDetails and RatePlan
    default RatePlanFlatRate mapToEntity(CreateRatePlanFlatRateRequest dto, RatePlan ratePlan) {
        RatePlanFlatRate ratePlanFlatRate = toEntity(dto);
        ratePlanFlatRate.setRatePlan(ratePlan);

        // Map RatePlanFlatRateDetails
        Set<RatePlanFlatRateDetails> details = dto.getRatePlanFlatRateDetails().stream()
                .map(detailDto -> {
                    RatePlanFlatRateDetails detail = new RatePlanFlatRateDetails();
                    detail.setUnitRate(detailDto.getUnitRate());
                    detail.setMaxLimit(detailDto.getMaxLimit());
                    detail.setRatePlanFlatRate(ratePlanFlatRate); // Set parent reference
                    return detail;
                })
                .collect(Collectors.toSet());

        ratePlanFlatRate.setRatePlanFlatRateDetails(details);
        return ratePlanFlatRate;
    }

    // Map from Entity to DTO, including nested details
    @Mapping(target = "ratePlanId", source = "ratePlan.ratePlanId")
    @Mapping(target = "ratePlanFlatRateDetails", source = "ratePlanFlatRateDetails")
    RatePlanFlatRateDTO toDTO(RatePlanFlatRate entity);

    // Map nested RatePlanFlatRateDetails entity to DTO
    @Mapping(target = "id", source = "id")
    @Mapping(target = "unitRate", source = "unitRate")
    @Mapping(target = "maxLimit", source = "maxLimit")
    RatePlanFlatRateDetailsDTO toDetailsDTO(RatePlanFlatRateDetails details);

    // Map nested RatePlanFlatRateDetailsDTO to entity
    default RatePlanFlatRateDetails mapDetailDTOToEntity(RatePlanFlatRateDetailsDTO detailsDTO) {
        RatePlanFlatRateDetails details = new RatePlanFlatRateDetails();
        details.setId(detailsDTO.getId());
        details.setUnitRate(detailsDTO.getUnitRate());
        details.setMaxLimit(detailsDTO.getMaxLimit());
        return details;
    }

    // Update existing RatePlanFlatRate from DTO
    @Mapping(target = "ratePlanFlatRateId", ignore = true) // ID remains unchanged
    @Mapping(target = "ratePlan", ignore = true) // Handled manually
    @Mapping(target = "ratePlanFlatRateDetails", ignore = true) // Handle in after-mapping
    void updateRatePlanFlatRate(RatePlanFlatRateDTO ratePlanFlatRateDTO, @MappingTarget RatePlanFlatRate ratePlanFlatRate);

    // After-mapping logic to update nested RatePlanFlatRateDetails and RatePlan reference
    @AfterMapping
    default void afterUpdateRatePlanFlatRate(RatePlanFlatRateDTO ratePlanFlatRateDTO,
                                             @MappingTarget RatePlanFlatRate ratePlanFlatRate,
                                             @Context RatePlan ratePlan) {
        ratePlanFlatRate.setRatePlan(ratePlan);

        if (ratePlanFlatRateDTO.getRatePlanFlatRateDetails() != null) {
            Set<RatePlanFlatRateDetails> updatedDetails = ratePlanFlatRateDTO.getRatePlanFlatRateDetails().stream()
                    .map(this::mapDetailDTOToEntity)
                    .peek(detail -> detail.setRatePlanFlatRate(ratePlanFlatRate))
                    .collect(Collectors.toSet());

            Set<RatePlanFlatRateDetails> existingDetails = ratePlanFlatRate.getRatePlanFlatRateDetails();

            // Update or add new details
            for (RatePlanFlatRateDetails newDetail : updatedDetails) {
                RatePlanFlatRateDetails existingDetail = existingDetails.stream()
                        .filter(d -> d.getId() != null && d.getId().equals(newDetail.getId()))
                        .findFirst()
                        .orElse(null);

                if (existingDetail != null) {
                    existingDetail.setUnitRate(newDetail.getUnitRate());
                    existingDetail.setMaxLimit(newDetail.getMaxLimit());
                } else {
                    existingDetails.add(newDetail);
                }
            }

            // Remove orphaned details
            existingDetails.removeIf(existingDetail -> updatedDetails.stream()
                    .noneMatch(d -> d.getId() != null && d.getId().equals(existingDetail.getId())));

            ratePlanFlatRate.setRatePlanFlatRateDetails(existingDetails);
        }
    }

    // Map RatePlanFlatRate entity to DTO, including nested details
    @Mapping(target = "ratePlanId", ignore = true) // Set manually after mapping
    @Mapping(target = "ratePlanFlatRateDetails", source = "ratePlanFlatRateDetails")
    RatePlanFlatRateDTO updateRatePlanFlatRateDTO(RatePlanFlatRate ratePlanFlatRate,
                                                  @MappingTarget RatePlanFlatRateDTO ratePlanFlatRateDTO);

    // After-mapping logic to set ratePlanId
    @AfterMapping
    default void afterUpdateRatePlanFlatRateDTO(RatePlanFlatRate ratePlanFlatRate,
                                                @MappingTarget RatePlanFlatRateDTO ratePlanFlatRateDTO) {
        ratePlanFlatRateDTO.setRatePlanId(ratePlanFlatRate.getRatePlan() != null
                ? ratePlanFlatRate.getRatePlan().getRatePlanId()
                : null);
    }
}
