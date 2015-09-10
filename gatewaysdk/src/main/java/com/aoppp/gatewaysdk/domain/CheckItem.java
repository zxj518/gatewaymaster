package com.aoppp.gatewaysdk.domain;

import android.app.Activity;
import android.content.Context;

import com.aoppp.webviewdom.internal.WebViewJs;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by guguyanhua on 6/24/15.
 */
public class CheckItem implements Cloneable{

    String name;
    // -1 unknow ,0 fail ,1 success
    int state = 1;

    String desc;

    List<CheckRule> rules;

    //检查项包含的指标
    List<Indicator> indicators ;



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

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public CheckItem clone(){
        CheckItem cp = new CheckItem();
        cp.setName(this.name);
        cp.setState(this.state);
        cp.setIndicators(this.indicators);
        cp.setDesc(this.desc);
        cp.setRules(this.rules);
        return cp;
    }

    public CheckItem collect(Gateway gateway, Context context,WebViewJs webView){
        CheckItem checkItem = this.clone();
        List<Indicator> indicatorList = Lists.newArrayList();
        for(Indicator indicator : indicators){
            Indicator result = gateway.checkIndicator((Activity)context,webView, gateway.getBasicInfo().deviceType, indicator, 30L, TimeUnit.SECONDS);
            indicatorList.add(result);
//            result.check();
//            if(result.getState() != 1) {
//                checkItem.setState(result.getState());
//            }
        }
        checkItem.setIndicators(indicatorList);
        return checkItem;
    }

    public void check(){
        doCheck();
    }

    public Indicator getIndicatorByName(String indiName){
        if(indicators==null){
            return null;

        }
        for(Indicator indi :indicators){
            if(indi.getName().equals(indiName)){
                return indi;
            }
        }
        return null;
    }

    private void doCheck(){
        if(this.rules==null || this.rules.size()==0){
            return;
        }

        for (CheckRule rule:rules){
            Indicator indi = getIndicatorByName(rule.getIndicator());
            if(indi==null){
                rule.setResult(0);//无法判定
                continue;
            }



            if(rule.getOperator().equals("=")){
                boolean ruleResult = equalsWith(indi.getDatatype(), indi.getValue(), rule.getValue());
                rule.setResult(ruleResult?1:-1);

            }

            if (rule.getOperator().equals("in")) {
                boolean ruleResult = inWith(indi.getValue(), rule.getValue());
                rule.setResult(ruleResult?1:-1);
            }

            if(indi.getDatatype()==CheckUtils.NUMBERIC) {

                if(indi.getValue()==null){
                    rule.setResult(0);//无法判定
                    continue;
                }
                double indiDoubleValue = Double.parseDouble(indi.getValue().toString());
                double ruleDoubleValue =  Double.parseDouble(rule.getValue().toString());
                if (rule.getOperator().equals(">=")) {
                    rule.setResult(indiDoubleValue >= ruleDoubleValue?1:-1);
                } else if (rule.getOperator().equals("<=")) {
                    rule.setResult(indiDoubleValue <= ruleDoubleValue?1:-1);
                } else if (rule.getOperator().equals(">")) {
                    rule.setResult(indiDoubleValue > ruleDoubleValue?1:-1);
                } else if (rule.getOperator().equals("<")) {
                    rule.setResult(indiDoubleValue < ruleDoubleValue?1:-1);
                }
            }



        }
    }

    private boolean equalsWith(int dataType, Object indiValue, Object ruleValue){

        if(ruleValue==null && indiValue==null){
            return true;
        }

        if(dataType==CheckUtils.STRING){
            return ruleValue.equals(indiValue);
        }else if(dataType==CheckUtils.NUMBERIC){

            double indiDoubleValue = ((Double)indiValue).doubleValue();
            double ruleDoubleValue = ((Double)ruleValue).doubleValue();
            return indiDoubleValue==ruleDoubleValue;
        }
        throw new RuntimeException("datatype does not suppport:" + dataType);
    }

    private boolean inWith(Object indiValue, Object ruleValue){
        if(indiValue==null || ruleValue==null){
            return false;
        }
        String ruleStr = ruleValue.toString();
        String[] ruleValues = ruleStr.split("|");
        if(ruleValues==null){
            return false;
        }
        String indiValueStr = String.valueOf(indiValue);
        for(String rv:ruleValues){
            if(indiValueStr.equals(rv)){
                return true;
            }
        }
        return false;
    }


    public List<CheckRule> getRules() {
        return rules;
    }

    public void setRules(List<CheckRule> rules) {
        this.rules = rules;
    }

    public boolean hasError(){
        if(this.rules==null){
            return false;
        }
        for (CheckRule rule:rules){
            if(rule.getResult()==CheckRule.FAIL){
                return true;
            }
        }
        return false;
    }

    public List<CheckRule>  findFailedRulesByIndicator(String indiName){

        if(this.rules==null){
            return null;
        }
        List<CheckRule> failedRules = new ArrayList<CheckRule>();
        for (CheckRule rule:rules){
            if(rule.getIndicator().equals(indiName) && rule.getResult()==CheckRule.FAIL){
                failedRules.add(rule);
            }
        }
        return failedRules;
    }

    @Override
    public String toString() {
        return "CheckItem{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", indicators=" + indicators +
                '}';
    }
}
