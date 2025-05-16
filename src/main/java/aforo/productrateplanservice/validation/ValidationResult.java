package aforo.productrateplanservice.validation;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ValidationResult {
    private boolean valid;
    private List<String> errors;

    public static ValidationResult valid() {
        return ValidationResult.builder()
                .valid(true)
                .errors(new ArrayList<>())
                .build();
    }

    public static ValidationResult invalid(List<String> errors) {
        return ValidationResult.builder()
                .valid(false)
                .errors(errors)
                .build();
    }
}