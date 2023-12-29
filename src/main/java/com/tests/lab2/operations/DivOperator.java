package com.tests.lab2.operations;

public class DivOperator extends Operator
{
    @Override
    public double getResult(int a, int b) throws IllegalArgumentException
    {
        if (b == 0)
        {
            throw new IllegalArgumentException("На ноль делить нельзя!");
        }
        return (double) a / (double)b;
    }
}
