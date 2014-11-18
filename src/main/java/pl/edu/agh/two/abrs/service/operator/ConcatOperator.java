package pl.edu.agh.two.abrs.service.operator;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ConcatOperator implements Operator {

	@Override
	public String name() {
		return "concat";
	}

	@Override
	public Object apply(List<Object> args) {
		return args.stream()
				.map(Object::toString)
				.reduce((x, y) -> x + y)
				.get();
	}
}
