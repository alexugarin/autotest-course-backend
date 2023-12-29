package com.tests.lab2.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Converter
{
    public static int convertToInt(String numberString, int numberSystem) throws IllegalArgumentException
    {
        return Integer.parseInt(numberString, numberSystem);
    }

    public static String convertToString(double number, int numberSystem){
        return Integer.toString((int)number, numberSystem).toUpperCase();
    }

    public static String convertToString(int number, int numberSystem) throws IllegalArgumentException
    {
        return Integer.toString(number, numberSystem).toUpperCase();
    }
}
