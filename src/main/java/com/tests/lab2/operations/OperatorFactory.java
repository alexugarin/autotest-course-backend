package com.tests.lab2.operations;

public class OperatorFactory
{
    public Operator createOperator(String operatorSign)
    {
        switch (operatorSign)
        {
            case "+":
                return new SumOperator();
            case "-":
                return new DiffOperator();
            case "*":
                return new MultOperator();
            case "/":
                return new DivOperator();
            default:
                return null;
        }
    }
}
