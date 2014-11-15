package pl.edu.agh.two.abrs.service.operator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

@Component
public class DiffOperator extends NumericOperator {
	
	@Override
	public String name() {
		return "diff";
	}

	@Override
	protected Object applyInternal(List<Double> args) {
		return args.stream()
				.reduce((a, b) -> a - b)
				.get();
	}
}
