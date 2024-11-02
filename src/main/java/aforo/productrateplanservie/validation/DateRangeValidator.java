package aforo.productrateplanservie.validation;

import aforo.productrateplanservie.rate_plan.CreateRatePlanRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, CreateRatePlanRequest> {
    @Override
    public boolean isValid(CreateRatePlanRequest request, ConstraintValidatorContext context) {
        if (request.getStartDate() == null || request.getEndDate() == null) {
            return false;
        }
        return request.getStartDate().isBefore(request.getEndDate());
    }
}
