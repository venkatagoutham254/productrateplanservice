package aforo.productrateplanservice.exception;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
		return ResponseEntity
				.badRequest()
				.body(Map.of("error", ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());

		return ResponseEntity
				.badRequest()
				.body(Map.of("error", String.join(", ", errors)));
	}

	// Catch enum-related conversion exceptions
	@ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageConversionException.class, InvalidFormatException.class})
	public ResponseEntity<String> handleInvalidEnumException(Exception ex) {
		String message = "Invalid value provided for an enum field.";
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
}
