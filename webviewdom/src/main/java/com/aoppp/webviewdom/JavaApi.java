package com.aoppp.webviewdom;

import android.webkit.JavascriptInterface;

/**
 * Created by guguyanhua on 7/2/15.
 *
 */
public class JavaApi {

    private Page.Callback callback;

    public JavaApi(Page.Callback callback) {
        this.callback = callback;
    }

    @JavascriptInterface
    public void onFinishMap(Object result){
        System.out.println(result);
    }

    @JavascriptInterface
    public void onFinishJson(String result){
        callback.callback(result);
    }

    @JavascriptInterface
    public void onFailJson(String result){
        callback.fail(result);
    }
}
