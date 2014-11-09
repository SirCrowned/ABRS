package pl.edu.agh.two.abrs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Row {

	private final List<String> fields;

	public Row(String... fields) {
		this(Arrays.asList(fields));
	}

	public Row(List<String> fields) {
		this.fields = Collections.unmodifiableList(new ArrayList<>(fields));
	}

	public String get(int index) {
		return fields.get(index);
	}

	public int length() {
		return fields.size();
	}
}
