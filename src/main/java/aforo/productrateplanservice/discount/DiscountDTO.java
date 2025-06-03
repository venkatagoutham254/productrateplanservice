package aforo.productrateplanservice.discount;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountDTO {
    private Long ratePlanId;
    private String discountType;
    private String eligibility;
    private LocalDate startDate;
    private LocalDate endDate;
}
