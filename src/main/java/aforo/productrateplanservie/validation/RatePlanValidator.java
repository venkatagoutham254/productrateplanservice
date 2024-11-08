package aforo.productrateplanservie.validation;

import aforo.productrateplanservie.currencies.CurrenciesRepository;
import aforo.productrateplanservie.rate_plan.CreateRatePlanRequest;
import aforo.productrateplanservie.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RatePlanValidator {
    private final CurrenciesRepository currencyRepository;

    public ValidationResult validate(CreateRatePlanRequest request) {
        List<String> errors = new ArrayList<>();

        // Basic null checks
        if (request == null) {
            errors.add("Request cannot be null");
            return ValidationResult.invalid(errors);
        }

        if (request.getRatePlanName() == null || request.getRatePlanName().trim().isEmpty()) {
            errors.add("Rate plan name is required");
        } else if (request.getRatePlanName().length() > 100) {
            errors.add("Rate plan name cannot exceed 100 characters");
        }

        if (request.getDescription() != null && request.getDescription().length() > 1000) {
            errors.add("Description cannot exceed 1000 characters");
        }

        if (request.getRatePlanType() == null) {
            errors.add("Rate plan type is required");
        }

        if (request.getStatus() == null) {
            errors.add("Status is required");
        }

        if (!Objects.equals(request.getStatus(), Status.ACTIVE)) {
            errors.add("Status must be in ACTIVE");
        }

        // Currency validation
        if (request.getCurrencyId() == null) {
            errors.add("Currency ID is required");
        } else if (request.getCurrencyId() <= 0) {
            errors.add("Currency ID must be positive");
        } else if (!currencyRepository.existsById(request.getCurrencyId())) {
            errors.add("Invalid currency ID");
        }

        // Date validation
        LocalDateTime now = LocalDateTime.now();

        if (request.getStartDate() == null) {
            errors.add("Start date is required");
        } else if (request.getStartDate().isBefore(now)) {
            errors.add("Start date must be in the future");
        }

        if (request.getEndDate() == null) {
            errors.add("End date is required");
        } else if (request.getEndDate().isBefore(now)) {
            errors.add("End date must be in the future");
        }

        // Date range validation
        if (request.getStartDate() != null && request.getEndDate() != null
                && !request.getStartDate().isBefore(request.getEndDate())) {
            errors.add("End date must be after start date");
        }

        return errors.isEmpty() ? ValidationResult.valid() : ValidationResult.invalid(errors);
    }
}