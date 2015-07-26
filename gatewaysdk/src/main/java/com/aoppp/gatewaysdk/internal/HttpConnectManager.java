package com.aoppp.gatewaysdk.internal;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by guguyanhua on 6/24/15.
 */
public class HttpConnectManager {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    private static HttpConnectManager instance;

    public Map<String,String> cache = new WeakHashMap<String, String>();

    public HttpConnectManager() {
    }

    public static synchronized HttpConnectManager instance(){
        if(instance == null){
            instance = new HttpConnectManager();
        }
        return instance;
    }


    public OkHttpClient getClient() {


//        client.setProxy(new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1",8888)));

        return client;
    }
}
