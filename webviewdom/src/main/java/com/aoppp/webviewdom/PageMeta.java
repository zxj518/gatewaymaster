package com.aoppp.webviewdom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aoppp.webviewdom.internal.JsFunction;
import com.aoppp.webviewdom.internal.WebViewJs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by guguyanhua on 7/2/15.
 * 指代一个页面
 */
public class PageMeta {



    private String deviceType;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private List<ElementMeta> allElements = new ArrayList<>();

    public List<ElementMeta> getAllElements() {
        return allElements;
    }

    public void setAllElements(List<ElementMeta> allElements) {
        this.allElements = allElements;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
