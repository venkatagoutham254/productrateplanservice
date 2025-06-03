package aforo.productrateplanservice.freemium;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreemiumDTO {
    private Long ratePlanId;
    private FreemiumType freemiumType;
    private String eligibility;
    private LocalDate startDate;
    private LocalDate endDate;
}
