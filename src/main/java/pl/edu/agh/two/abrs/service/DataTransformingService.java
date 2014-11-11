package pl.edu.agh.two.abrs.service;

import java.util.List;

public interface DataTransformingService {

    List<Object> transform(String function, List<Object> inputRow);
}
