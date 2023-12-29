package com.tests.lab2;

import com.tests.lab2.operations.Operator;
import com.tests.lab2.operations.OperatorFactory;
import com.tests.lab2.util.Converter;
import com.tests.lab2.util.Validator;
import javafx.util.Pair;

import java.util.List;

public class Calculator
{
    public static Pair<String, List<String>> calculate(String a, String b, String operation, String numberSystemString) throws IllegalArgumentException
    {
        List<String> errorCollection = Validator.validate(a, b, operation, numberSystemString);

        if (!errorCollection.isEmpty())
            return new Pair<>(null, errorCollection);

        int numberSystem = Integer.parseInt(numberSystemString);

        OperatorFactory factory = new OperatorFactory();
        Operator operator = factory.createOperator(operation);

        int numberA = Converter.convertToInt(a, numberSystem);;
        int numberB = Converter.convertToInt(b, numberSystem);

        double result = operator.getResult(numberA, numberB);

        return new Pair<>(Converter.convertToString(result, numberSystem), errorCollection);
    }
}
