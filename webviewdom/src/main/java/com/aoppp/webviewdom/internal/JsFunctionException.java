package com.aoppp.webviewdom.internal;

/**
 * Created by guguyanhua on 7/23/15.
 */
public class JsFunctionException extends Exception {

    public JsFunctionException() {
    }

    public JsFunctionException(String detailMessage) {
        super(detailMessage);
    }

    public JsFunctionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public JsFunctionException(Throwable throwable) {
        super(throwable);
    }
}
