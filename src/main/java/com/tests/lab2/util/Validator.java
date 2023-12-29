package com.tests.lab2.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Validator
{
    private static final List<String> ALLOWED_NUMBER_SYSTEM = Arrays.asList("2", "8", "10", "16");
    private static final List<String> ALLOWED_OPERATIONS = Arrays.asList("+", "-", "*", "/", "+");

    public static boolean validateNumberSystem(String numberSystem)
    {
        return ALLOWED_NUMBER_SYSTEM.contains(numberSystem);
    }

    public static boolean validateOperation(String operation)
    {
        return ALLOWED_OPERATIONS.contains(operation);
    }

    public static boolean validateNumber(String number, int numberSystem)
    {
        try
        {
            Integer.parseInt(number, numberSystem);
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
        return true;
    }

    public static List<String> validate(String a, String b, String operation, String numberSystem)
    {
        List<String> errors = new ArrayList<>();
        if (!validateNumberSystem(numberSystem))
            errors.add("Неправильная система счисления. Должна быть указана одна из предложенных: 2, 8, 10, 16");
        if (!validateOperation(operation))
            errors.add("Неправильная операция. Должна быть указана одна из предложенных: +, -, *, /");
        else {
            Integer numberSystemInt = Integer.parseInt(numberSystem);
            if (!validateNumber(a, numberSystemInt) || !validateNumber(b, numberSystemInt))
                errors.add("Невозможно преобразовать число в нужную систему счисления");
        }
        return errors;
    }
}
