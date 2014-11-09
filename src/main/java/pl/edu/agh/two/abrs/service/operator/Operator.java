package pl.edu.agh.two.abrs.service.operator;

import java.util.List;
import java.util.function.Function;

public interface Operator extends Function<List<Object>, Object> {

	String name();

}
