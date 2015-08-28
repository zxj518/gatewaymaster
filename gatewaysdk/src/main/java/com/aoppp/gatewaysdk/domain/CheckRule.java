package com.aoppp.gatewaysdk.domain;

/**
 * Created by snowzou on 15/8/28.
 */
public class CheckRule implements Cloneable{

    private String id;
    private String indicator;
    private String opeartor;//=, >, <, >=, <=, in
    private String value;
    private String error;

    private int result;//1 passed -1, failed, 0, 无法判定

    public CheckRule(String id, String indicator, String opeartor, String value, String error) {
        this.id = id;
        this.indicator = indicator;
        this.opeartor = opeartor;
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

    public String getOpeartor() {
        return opeartor;
    }

    public void setOpeartor(String opeartor) {
        this.opeartor = opeartor;
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
        return "rule[" + indicator + opeartor + value + "]";
    }
}
