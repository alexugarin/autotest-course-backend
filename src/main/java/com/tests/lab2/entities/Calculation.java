package com.tests.lab2.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name="calc")

public class Calculation
{
    private
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(name="NUMBER_A")
    private String numberA;
    @Column(name="SYSTEM_A")
    private String systemA;
    @Column(name="NUMBER_B")
    private String numberB;
    @Column(name="SYSTEM_B")
    private String systemB;
    @Column(name="OPERATION")
    private String operation;
    @Column(name="CALC_DATE")
    private Timestamp calcDate;

    public Calculation(long id, String numberA, String systemA, String numberB, String systemB, String operation, Timestamp calcDate){
        this.id = id;
        this.numberA = numberA;
        this.systemA = systemA;
        this.numberB = numberB;
        this.systemB = systemB;
        this.operation = operation;
        this.calcDate = calcDate;
    }

    public Calculation(){

    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setSystemA(String systemA) {
        this.systemA = systemA;
    }

    public void setNumberB(String numberB) {
        this.numberB = numberB;
    }

    public void setSystemB(String systemB) {
        this.systemB = systemB;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setCalcDate(Timestamp calcDate) {
        this.calcDate = calcDate;
    }

    public Long getId() {
        return id;
    }

    public String getSystemA() {
        return systemA;
    }

    public String getNumberB() {
        return numberB;
    }

    public String getSystemB() {
        return systemB;
    }

    public String getOperation() {
        return operation;
    }

    public Timestamp getCalcDate() {
        return calcDate;
    }

    public String getNumberA() {
        return numberA;
    }

    public void setNumberA(String numberA) {
        this.numberA = numberA;
    }
}
