package com.aoppp.gatewaysdk.domain;

import java.util.Arrays;
import java.util.List;

/**
 * Created by guguyanhua on 6/24/15.
 */
public class CheckResult {
    int errorCode;
    String errorMessage;
    List<CheckItem> checkItemList ;
    long cost;



    public CheckResult(int errorCode, String errorMessage, List<CheckItem> checkItemList,long cost) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.checkItemList = checkItemList;
        this.cost = cost;
    }

    public CheckResult(int errorCode, String errorMessage, CheckItem[] checkItems,long cost) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.checkItemList = Arrays.asList(checkItems);
        this.cost = cost;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<CheckItem> getCheckItemList() {
        return checkItemList;
    }

    public void setCheckItemList(List<CheckItem> checkItemList) {
        this.checkItemList = checkItemList;
    }

    @Override
    public String toString() {
        return "CheckResult{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", checkItemList=" + checkItemList +
                ", cost=" + cost +
                '}';
    }
}
