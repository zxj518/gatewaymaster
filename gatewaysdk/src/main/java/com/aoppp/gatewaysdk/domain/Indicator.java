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
    int datatype;

    Object value;

    //规则
    String defaultValue;


    String desc;


    public void check(){
        String trimed = value.toString().trim();
        trimed = trimed.replaceAll("[\\s\\u00A0]+$", ""); //去掉 160 的空格// FIXME
        if(defaultValue.equals(trimed)){
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

    public int getDatatype() {
        return datatype;
    }

    public void setDatatype(int datatype) {
        this.datatype = datatype;
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


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Indicator clone(){
        Indicator cp = new Indicator();
        cp.setState(this.state);
        cp.setName(this.name);
        cp.setDefaultValue(this.defaultValue);
        cp.setMessage(this.message);
        cp.setDatatype(this.datatype);
        cp.setValue(this.value);
        cp.setDesc(this.desc);
        return cp;
    }

    @Override
    public String toString() {
        return "Indicator{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", message='" + message + '\'' +
                ", datatype=" + datatype +
                ", value=" + value +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }
}
