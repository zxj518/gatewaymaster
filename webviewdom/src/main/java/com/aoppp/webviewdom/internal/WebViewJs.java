package com.aoppp.webviewdom.internal;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by guguyanhua on 7/2/15.
 */
public class WebViewJs extends WebView{

    public WebViewJs(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WebViewJs(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public WebViewJs(Context context) {
        super(context);
        init();
    }


    private void init() {
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        this.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        //这句必须加上
        this.setWebChromeClient(new WebChromeClient());
    }
}
