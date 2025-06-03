package aforo.productrateplanservice.minimumcommitment;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MinimumCommitmentDTO {
    private Long ratePlanId;
    private Double minimumCommitment;
    private String commitmentDuration;
    private String commitmentUnit;
}
