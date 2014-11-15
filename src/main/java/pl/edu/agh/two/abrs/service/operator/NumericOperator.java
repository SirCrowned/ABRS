package pl.edu.agh.two.abrs.service.operator;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public abstract class NumericOperator implements Operator {

	@Override
	public final Object apply(List<Object> objects) {
		for (int i = 0; i < objects.size(); i++) {
			Object arg = objects.get(i);
			Validate.isTrue(StringUtils.isNumeric(arg.toString()), "Argument: " + i + " is not of numeric type");
		}

		return applyInternal(objects.stream()
				.map(x -> Double.valueOf(x.toString()))
				.collect(Collectors.toList()));
	}

	protected abstract Object applyInternal(List<Double> args);
}
