package com.aoppp.webviewdom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aoppp.webviewdom.internal.JsFunction;
import com.aoppp.webviewdom.internal.JsFunctionException;
import com.aoppp.webviewdom.internal.WebViewJs;

import org.apache.http.util.EncodingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by guguyanhua on 7/2/15.
 * 指代一个页面
 */
public class Page {

    private ReentrantLock locker = new ReentrantLock();

    private String result;

    private Throwable e;


    private WebViewJs webViewJs;

    private Activity activity;

//    private String cookie;

    private String url;

    private String ip;


    private PageMeta pageMeta;


    private List<ElementMeta> allElements = new ArrayList<>();

    public List<ElementMeta> getAllElements() {
        return allElements;
    }

    public WebViewJs getWebViewJs() {
        return webViewJs;
    }

    public void setWebViewJs(WebViewJs webViewJs) {
        this.webViewJs = webViewJs;
    }

    public void setAllElements(List<ElementMeta> allElements) {
        this.allElements = allElements;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public PageMeta getPageMeta() {
        return pageMeta;
    }

    public void setPageMeta(PageMeta pageMeta) {
        this.pageMeta = pageMeta;
    }

    class Handler {

         public void show(String data) {
            //这里的data就webview加载的内容，即使页面跳转页都可以获取到，这样就可以做自己的处理了
             Log.w(Page.class.getName(), "web content:\n" + data );
            new AlertDialog.Builder(webViewJs.getContext()).setMessage(data).create().show();
         }
    }

    @SuppressLint("AddJavascriptInterface")
    public void asyncFetch(final Callback callback){
        final String appendUrl = "http://"+ip+url; //ip 和 path 结合
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(pageMeta.getReqType().equals("post")){
                    webViewJs.postUrl(appendUrl, EncodingUtils.getBytes(pageMeta.getPostData(), "BASE64"));
                }else {
                    webViewJs.loadUrl(appendUrl);
                }
                injectCookie();
                webViewJs.addJavascriptInterface(new JavaApi(callback), "javaapi");
                //webViewJs.addJavascriptInterface(new Handler(),"handler");
                webViewJs.setWebViewClient(new WebViewClient(){
                    //界面加载后采集数据,都采集好了继续下一个页面


                    @Override
                    public void onPageFinished(WebView view, String url) {

                        super.onPageFinished(view, url);

                        JsFunction jsFunction = new JsFunction(Page.this);
                        //输出function
                        Log.d("com.aoppp.jsFuncion", jsFunction.gen(false));
                        //加载获取脚本
                        webViewJs.loadUrl(jsFunction.gen(false) + ";fetch()");
                        //执行
                        //webViewJs.loadUrl("javascript:fetch()");
                        //Log.w("com.aoppp.jsFuncion", jsFunction.)
                    }
                });
            }
        });

    }

    public String syncFetch(long timeout,TimeUnit timeUnit) throws Throwable {
        locker.tryLock();
        try{
            final Condition con = locker.newCondition();
            asyncFetch(new Callback() {
                @Override
                public void callback(String json) {
                    try {
                        locker.tryLock();
                        result = json;
                        con.signalAll();
                    }catch (Throwable e){
                        Page.this.e = e;
                        Log.e(this.getClass().getCanonicalName(), "Page " + url + " fetch fail on callback method", e);
                        con.signalAll();
                    }finally {
                        locker.unlock();
                    }
                }

                @Override
                public void fail(String json) {
                    try {
                        locker.tryLock();
                        Page.this.e = new JsFunctionException(json);
                        con.signalAll();
                    }catch (Throwable e){
                        Page.this.e = e;
                        Log.e(this.getClass().getCanonicalName(), "Page " + url + " fetch fail on fail method", e);
                        con.signalAll();
                    }finally {
                        locker.unlock();
                    }
                }
            });
            con.await(timeout,timeUnit);
            if(e != null){
                throw e;
            }
            return result;
        } catch (InterruptedException e) {
            throw e;
        } finally {
            locker.unlock();
        }

    }

    private void injectCookie() {

    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public interface Callback{
        void callback(String json);

        void fail(String json);
    }
}
