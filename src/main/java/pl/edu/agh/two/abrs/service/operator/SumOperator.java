package pl.edu.agh.two.abrs.service.operator;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SumOperator extends NumericOperator {

	@Override
	public String name() {
		return "sum";
	}

	@Override
	protected Object applyInternal(List<Double> args) {
		return args.stream()
				.reduce(Double::sum)
				.get();
	}
}
