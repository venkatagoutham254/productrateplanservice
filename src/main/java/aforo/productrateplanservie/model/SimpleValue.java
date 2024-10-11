package aforo.productrateplanservie.model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.EntityModel;
public class SimpleValue<T> {
	private final T value;
	// Default constructor (required for Jackson serialization)
	public SimpleValue() {
		this.value = null; // Initialize with a default value
	}
	// Constructor used for setting value
	@JsonCreator
	public SimpleValue(@JsonProperty("value") final T value) {
		this.value = value;
	}
	public static <T> EntityModel<SimpleValue<T>> entityModelOf(final T value) {
		final SimpleValue<T> simpleValue = new SimpleValue<>(value);
		return EntityModel.of(simpleValue);
	}
	public T getValue() {
		return value;
	}
}
