package aforo.productrateplanservice.enumValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum annotation) {
        this.enumClass = annotation.enumClass();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        // If the value is null, allow it (handled by @NotNull if required in the DTO)
        if (value == null) {
            return true;
        }
        // Check if the value belongs to the specified enum class
        return enumClass.isInstance(value);
    }
}
