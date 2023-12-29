package com.tests.lab2.operations;

public enum OperatorSign
{
    SUM ("+"),
    DIFF ("-"),
    MULT ("*"),
    DIV("/");

    private String title;

    OperatorSign(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
}
