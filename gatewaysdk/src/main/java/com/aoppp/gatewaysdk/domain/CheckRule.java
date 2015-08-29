package com.aoppp.gatewaysdk.domain;

/**
 * Created by snowzou on 15/8/28.
 */
public class CheckRule implements Cloneable{

    public static final  int PASSED = 1;
    public static final  int FAIL = -1;
    public static final  int NONE = 0;

    private String id;
    private String indicator;
    private String operator;//=, >, <, >=, <=, in
    private String value;
    private String error;

    private int result;//1 passed -1, failed, 0, 无法判定

    public CheckRule(String id, String indicator, String operator, String value, String error) {
        this.id = id;
        this.indicator = indicator;
        this.operator = operator;
        this.value = value;
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String toString(){
        return "rule[" + indicator + operator + value + "]";
    }
}
