package pl.edu.agh.two.abrs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Row {

    private final List<Object> fields;

    public Row(List<Object> fields) {
        this.fields = Collections.unmodifiableList(new ArrayList<>(fields));
    }

    public Object get(int index) {
        return fields.get(index);
    }

    public int length() {
        return fields.size();
    }
}
