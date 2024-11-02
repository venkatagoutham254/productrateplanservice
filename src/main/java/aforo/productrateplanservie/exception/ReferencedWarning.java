package aforo.productrateplanservie.exception;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.stream.Collectors;
@Setter
@Getter
public class ReferencedWarning {
	private String key = null;
	private ArrayList<Object> params = new ArrayList<>();
	public void addParam(final Object param) {
		params.add(param);
	}


    public String toMessage() {
		String message = key;
		if (!params.isEmpty()) {
			message += "," + params.stream()
			.map(Object::toString)
			.collect(Collectors.joining(","));
		}
		return message;
	}


	public boolean hasWarnings() {
		return params.size() > 0;
	}
}
