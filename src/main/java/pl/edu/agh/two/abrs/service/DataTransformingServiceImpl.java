package pl.edu.agh.two.abrs.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.Validate;
import pl.edu.agh.two.abrs.service.operator.IdentityOperator;
import pl.edu.agh.two.abrs.service.operator.Operator;

@Service
public class DataTransformingServiceImpl implements DataTransformingService {

    private final Map<String, Operator> operatorsMap;

    private final Map<Pattern, Function<String, ParseResult>> parsers;

    @Autowired
    public DataTransformingServiceImpl(List<Operator> operators) {
        operatorsMap = toOperatorsMap(operators);
        parsers = setupParsers();
    }

    private Map<String, Operator> toOperatorsMap(List<Operator> operators) {
        Map<String, Operator> operatorsMap = new HashMap<>();
        for (Operator operator : operators) {
            operatorsMap.put(operator.name(), operator);
        }
        return operatorsMap;
    }

    private Map<Pattern, Function<String, ParseResult>> setupParsers() {
        Map<Pattern, Function<String, ParseResult>> parsers = new LinkedHashMap<>();
        Pattern operatorPattern = Pattern.compile("(\\w+)\\(((?:\\s*\\$?\\d\\s*,?)+)\\)");
        parsers.put(operatorPattern, (mapping) -> {
            Matcher matcher = operatorPattern.matcher(mapping);
            if (matcher.matches()) {
                String operatorName = matcher.group(1);
                Operator operator = operatorsMap.get(operatorName);
                if (operator == null) {
                    throw new IllegalArgumentException("Invalid operator: " + operatorName);
                }
                List<String> args = Arrays.asList(matcher.group(2).replaceAll("\\s+", "").split(","))
                        .stream()
                        .collect(Collectors.toList());
                return new ParseResult(operator, args);
            }
            throw new IllegalArgumentException(mapping);
        });

        Pattern identityOperatorPattern = Pattern.compile("\\$\\d+");
        parsers.put(identityOperatorPattern, (mapping) -> new ParseResult(
                operatorsMap.get(IdentityOperator.NAME),
                Collections.singletonList(mapping.trim())));
        return Collections.unmodifiableMap(parsers);
    }

    @Override
    public List<Object> transform(String function, List<Object> inputRow) {
        List<String> columnsMappings = Arrays.asList(function.split(";"))
                .stream()
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .collect(Collectors.toList());
        List<Object> outputRow = new ArrayList<>();
        for (String mapping : columnsMappings) {
            ParseResult parseResult = parse(mapping);
            Object output = parseResult.operator.apply(toRealArguments(inputRow, parseResult));
            outputRow.add(output);
        }

        return Collections.unmodifiableList(outputRow);
    }

    @Override
    public List<String> getAvailableOperators() {
        ArrayList<String> operators = new ArrayList<>(operatorsMap.keySet());
        operators.sort(String::compareTo);
        return Collections.unmodifiableList(operators);
    }

    private List<Object> toRealArguments(List<Object> inputRow, ParseResult parseResult) {
        return parseResult.arguments.stream()
                .map(arg -> {
                    if (arg.trim().startsWith("$")) {
                        String argIndexAsString = arg.substring(1);
                        Validate.isTrue(StringUtils.isNumeric(argIndexAsString), "Invalid argument: " + argIndexAsString);
                        int argIndex = Integer.valueOf(argIndexAsString);
                        if (argIndex < 1 || argIndex > inputRow.size()) {
                            throw new IllegalArgumentException("Index out of range. Index should be a number between: 1 and " + inputRow.size());
                        }
                        return inputRow.get(argIndex - 1);
                    } else {
                        Validate.isTrue(StringUtils.isNumeric(arg), "Invalid argument: " + arg);
                        return Integer.valueOf(arg);
                    }
                })
                .collect(Collectors.toList());
    }

    private ParseResult parse(String mapping) {
        for (Map.Entry<Pattern, Function<String, ParseResult>> parser : parsers.entrySet()) {
            if (parser.getKey().matcher(mapping).matches()) {
                return parser.getValue().apply(mapping);
            }
        }
        throw new IllegalArgumentException("Invalid definition: " + mapping);
    }

    private static class ParseResult {
        final Operator operator;

        final List<String> arguments;

        private ParseResult(Operator operator, List<String> arguments) {
            this.operator = operator;
            this.arguments = arguments;
        }
    }
}
