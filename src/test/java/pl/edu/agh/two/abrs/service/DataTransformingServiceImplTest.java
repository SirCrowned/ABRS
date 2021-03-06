package pl.edu.agh.two.abrs.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import pl.edu.agh.two.abrs.service.operator.IdentityOperator;
import pl.edu.agh.two.abrs.service.operator.Operator;

import static org.assertj.core.api.Assertions.assertThat;

public class DataTransformingServiceImplTest {
    
    @Test
    public void should_return_proper_operators_list() {
        DataTransformingServiceImpl transformingService = new DataTransformingServiceImpl(Arrays.asList(new Sum(), new First(), new Last()));
        assertThat(transformingService.getAvailableOperators())
                .containsExactly("first", "last", "sum");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_on_invalid_index() {
        DataTransformingServiceImpl transformingService = new DataTransformingServiceImpl(Arrays.asList(new First()));
        transformingService.transform("first($0)", Arrays.asList(1, 2));
    }

    @Test
    public void should_transform_row_correctly_for_correct_mapping_expression() {
        DataTransformingServiceImpl transformingService = new DataTransformingServiceImpl(Arrays.asList(new IdentityOperator(), new First(), new Last()));
        assertThat(transformingService.transform("first($1,$2); last($1, $2, $3, $4); $4", Arrays.asList(1, 2, 3, 4)))
                .containsExactly(1, 4, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_on_invalid_operator() {
        DataTransformingServiceImpl transformingService = new DataTransformingServiceImpl(Arrays.asList(new IdentityOperator(), new First(), new Last()));
        transformingService.transform("not_defined($1,$2)", Arrays.asList(1, 2, 3, 4));
    }
	
	@Test
	public void should_properly_handle_numeric_arguments() {
		DataTransformingServiceImpl transformingService = new DataTransformingServiceImpl(Arrays.asList(new Sum()));
		List<Object> result = transformingService.transform("sum($1, $2, 10, 20)", Arrays.asList(1, 2));
		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isEqualTo(Double.valueOf(33));
	}

    static class First implements Operator {

        @Override
        public String name() {
            return "first";
        }

        @Override
        public Object apply(List<Object> objects) {
            return objects.get(0);
        }
    }

    static class Last implements Operator {

        @Override
        public String name() {
            return "last";
        }

        @Override
        public Object apply(List<Object> objects) {
            return objects.get(objects.size() - 1);
        }
    }

    static class Sum implements Operator {

        @Override
        public String name() {
            return "sum";
        }

        @Override
        public Object apply(List<Object> objects) {
            return objects.stream()
                    .reduce((o, o2) -> Double.parseDouble(o.toString()) + Double.parseDouble(o2.toString()))
                    .get();
        }
    }
}