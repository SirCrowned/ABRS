package pl.edu.agh.two.abrs.service;

import java.util.List;

import pl.edu.agh.two.abrs.Row;

public interface DataTransformingService {
	
	List<Object> transform(String function, List<Object> inputRow);
	
}
