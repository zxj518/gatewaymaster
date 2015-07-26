package com.aoppp.webviewdom;

import java.util.Map;

/**
 * Created by guguyanhua on 7/22/15.
 *
 */
public class IndicatorResult {
    private int errorCode;
    private String errorMsg;
    private Map<String,String> result;

    public IndicatorResult(int errorCode, String errorMsg, Map<String, String> result) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Map<String, String> getResult() {
        return result;
    }

    public void setResult(Map<String, String> result) {
        this.result = result;
    }
}
