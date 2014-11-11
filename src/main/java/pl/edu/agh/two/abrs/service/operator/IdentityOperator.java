package pl.edu.agh.two.abrs.service.operator;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IdentityOperator implements Operator {

    public static final String NAME = "identity";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Object apply(List<Object> objects) {
        return objects.get(0);
    }
}
