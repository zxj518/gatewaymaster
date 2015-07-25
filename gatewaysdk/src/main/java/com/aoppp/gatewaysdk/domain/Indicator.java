package com.aoppp.gatewaysdk.domain;

/**
 * Created by guguyanhua on 6/24/15.
 * 指标
 */
public class Indicator implements Cloneable{

    String name;
    // -1 unknow ,0 fail ,1 success
    int state;
    //额外信息
    String message;
    //指标类型
    // 0 string , 1 int
    int dataType;

    Object value;

    //规则
    String defaultValue;



    public void check(){
        if(defaultValue.equals(value)){
            this.setState(1);
        }else{
            this.setState(0);
        }
    }



    public void setValue(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public Object getValue() {
        return value;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }


    public Indicator clone(){
        Indicator cp = new Indicator();
        cp.setState(this.state);
        cp.setName(this.name);
        cp.setDefaultValue(this.defaultValue);
        cp.setMessage(this.message);
        cp.setDataType(this.dataType);
        cp.setValue(this.value);
        return cp;
    }

    @Override
    public String toString() {
        return "Indicator{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", message='" + message + '\'' +
                ", dataType=" + dataType +
                ", value=" + value +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }
}
