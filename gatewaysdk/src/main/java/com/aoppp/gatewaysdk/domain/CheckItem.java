package com.aoppp.gatewaysdk.domain;

import android.app.Activity;
import android.content.Context;

import com.aoppp.webviewdom.internal.WebViewJs;
import com.google.common.collect.Lists;

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
        return cp;
    }

    public CheckItem check(Gateway gateway, Context context,WebViewJs webView){
        CheckItem checkItem = this.clone();
        List<Indicator> indicatorList = Lists.newArrayList();
        for(Indicator indicator : indicators){
            Indicator result = gateway.checkIndicator((Activity)context,webView, indicator, 30L, TimeUnit.SECONDS);
            indicatorList.add(result);
            result.check();
            if(result.getState() != 1) {
                checkItem.setState(result.getState());
            }
        }
        checkItem.setIndicators(indicatorList);
        return checkItem;
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
