package pl.edu.agh.two.abrs.service.operator;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MulOperator extends NumericOperator {

	@Override
	public String name() {
		return "mul";
	}

	@Override
	protected Object applyInternal(List<Double> args) {
		return args.stream()
				.reduce((a, b) -> a * b)
				.get();
	}
}
