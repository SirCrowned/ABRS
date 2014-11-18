package pl.edu.agh.two.abrs.service;

import java.util.Arrays;

import org.junit.Test;
import pl.edu.agh.two.abrs.service.operator.ConcatOperator;
import pl.edu.agh.two.abrs.service.operator.DiffOperator;
import pl.edu.agh.two.abrs.service.operator.DivOperator;
import pl.edu.agh.two.abrs.service.operator.MulOperator;
import pl.edu.agh.two.abrs.service.operator.SumOperator;

import static org.assertj.core.api.Assertions.assertThat;

public class OperatorsTest {
	
	@Test
	public void sum_operator_should_work() {
		DataTransformingServiceImpl transformingService = new DataTransformingServiceImpl(Arrays.asList(new SumOperator()));
		assertThat(transformingService.transform("sum($1, $2, $3)", Arrays.asList(100, 20, 3)))
				.containsExactly(123.0d);
	}

	@Test
	public void diff_operator_should_work() {
		DataTransformingServiceImpl transformingService = new DataTransformingServiceImpl(Arrays.asList(new DiffOperator()));
		assertThat(transformingService.transform("diff($1, $2, $3)", Arrays.asList(100, 20, 3)))
				.containsExactly(77.0d);
	}


	@Test
	public void mul_operator_should_work() {
		DataTransformingServiceImpl transformingService = new DataTransformingServiceImpl(Arrays.asList(new MulOperator()));
		assertThat(transformingService.transform("mul($1, $2, $3)", Arrays.asList(100, 20, 3)))
				.containsExactly(6000.0d);
	}

	@Test
	public void div_operator_should_work() {
		DataTransformingServiceImpl transformingService = new DataTransformingServiceImpl(Arrays.asList(new DivOperator()));
		assertThat(transformingService.transform("div($1, $2, $3)", Arrays.asList(100, 20, 2)))
				.containsExactly(2.5d);
	}

	@Test
	public void concat_operator_should_work() {
		DataTransformingServiceImpl transformingService = new DataTransformingServiceImpl(Arrays.asList(new ConcatOperator()));
		assertThat(transformingService.transform("concat($1, $2, $3)", Arrays.asList(100, 20, 3)))
				.containsExactly("100203");
	}
	
}
