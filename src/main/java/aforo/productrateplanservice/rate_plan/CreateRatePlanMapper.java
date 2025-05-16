package aforo.productrateplanservice.rate_plan;

public class CreateRatePlanMapper {
    public static RatePlanDTO toRatePlanDTO(CreateRatePlanRequest request) {
        RatePlanDTO dto = new RatePlanDTO();
        dto.setRatePlanName(request.getRatePlanName());
        dto.setDescription(request.getDescription());
        dto.setRatePlanType(request.getRatePlanType());
        dto.setStatus(request.getStatus());
        dto.setCurrencyId(request.getCurrencyId());

        // Convert LocalDateTime to LocalDate
        dto.setStartDate(request.getStartDate().toLocalDate());
        dto.setEndDate(request.getEndDate().toLocalDate());

        return dto;
    }

    public static CreateRatePlanRequest toCreateRatePlanRequest(RatePlanDTO dto) {
        CreateRatePlanRequest request = new CreateRatePlanRequest();
        request.setRatePlanName(dto.getRatePlanName());
        request.setDescription(dto.getDescription());
        request.setRatePlanType(dto.getRatePlanType());
        request.setStatus(dto.getStatus());
        request.setCurrencyId(dto.getCurrencyId());

        // Convert LocalDate to LocalDateTime (set to start of day or based on your logic)
        request.setStartDate(dto.getStartDate().atStartOfDay());
        request.setEndDate(dto.getEndDate().atStartOfDay());

        return request;
    }

}
