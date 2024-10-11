package aforo.productrateplanservie.product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * This is a dummy validation controller that simulates the validation of 
 * Producer, Organization, and Division IDs.
 * 
 * Why this is needed:
 * -------------------
 * In the final implementation, these IDs (Producer, Organization, and Division) 
 * would be validated by making API calls to their respective microservices. However, 
 * since these microservices have not been implemented yet, we are using this 
 * dummy controller as a temporary solution. It allows us to validate these IDs 
 * within the current service and ensure that the rest of the business logic 
 * (such as creating or updating products) continues to function during the 
 * development process.
 * 
 * This controller mimics a real external microservice's behavior by returning
 * a validation status. Once the actual microservices are available, this 
 * dummy validation logic will be replaced by proper API calls.
 * 
 * For now:
 * - The IDs are considered valid if they are greater than 0.
 * - It returns a response with "ACTIVE" if the ID is valid.
 * - If the ID is invalid, it returns "INACTIVE" with a 404 status code.
 */
@RestController
@RequestMapping("/api/validate")
public class DummyValidationController {
	/**
	 * Dummy validation endpoint for validating the Producer ID.
	 * 
	 * @param producerId The Producer ID to be validated.
	 * @return ResponseEntity with validation status: "ACTIVE" if valid, 
	 *         "INACTIVE" if invalid.
	 */
	@GetMapping("/producer/{producerId}")
	public ResponseEntity<String> validateProducerId(@PathVariable Long producerId) {
		// Dummy logic for validation
		if (producerId != null && producerId > 0) {
			return ResponseEntity.ok("ACTIVE");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("INACTIVE");
	}
	/**
	 * Dummy validation endpoint for validating the Organization ID.
	 * 
	 * @param organizationId The Organization ID to be validated.
	 * @return ResponseEntity with validation status: "ACTIVE" if valid, 
	 *         "INACTIVE" if invalid.
	 */
	@GetMapping("/organization/{organizationId}")
	public ResponseEntity<String> validateOrganizationId(@PathVariable Long organizationId) {
		// Dummy logic for validation
		if (organizationId != null && organizationId > 0) {
			return ResponseEntity.ok("ACTIVE");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("INACTIVE");
	}
	/**
	 * Dummy validation endpoint for validating the Division ID.
	 * 
	 * @param divisionId The Division ID to be validated.
	 * @return ResponseEntity with validation status: "ACTIVE" if valid, 
	 *         "INACTIVE" if invalid.
	 */
	@GetMapping("/division/{divisionId}")
	public ResponseEntity<String> validateDivisionId(@PathVariable Long divisionId) {
		// Dummy logic for validation
		if (divisionId != null && divisionId > 0) {
			return ResponseEntity.ok("ACTIVE");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("INACTIVE");
	}
}
