package pl.edu.agh.two.abrs.service.operator;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DivOperator extends NumericOperator {
	
	@Override
	public String name() {
		return "div";
	}

	@Override
	protected Object applyInternal(List<Double> args) {
		return args.stream()
				.reduce((a, b) -> a / b)
				.get();
	}
}
