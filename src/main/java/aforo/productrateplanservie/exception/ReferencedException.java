package aforo.productrateplanservie.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class ReferencedException extends RuntimeException {
	private final ReferencedWarning referencedWarning;
	public ReferencedException() {
		super();
		this.referencedWarning = null;
	}
	public ReferencedException(final ReferencedWarning referencedWarning) {
		super(referencedWarning.toMessage());
		this.referencedWarning = referencedWarning;
	}
	public ReferencedWarning getReferencedWarning() {
		return referencedWarning;
	}
}
