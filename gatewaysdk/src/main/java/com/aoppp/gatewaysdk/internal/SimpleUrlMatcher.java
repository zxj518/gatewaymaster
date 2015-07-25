package com.aoppp.gatewaysdk.internal;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guguyanhua on 6/24/15.
 */
public class SimpleUrlMatcher {
    String url;
    String pattern;

    public SimpleUrlMatcher(String url, String pattern) {
        this.url = url;
        this.pattern = pattern;
    }





    public String match(){
//        if(HttpConnectManager.instance().cache.containsKey(this.url)){
//            return HttpConnectManager.instance().cache.get(this.url);
//        }else {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            try {
                Response res = HttpConnectManager.instance().getClient().newCall(request).execute();
                String content = res.body().string();
                Pattern url = Pattern.compile(pattern);
                Matcher matcher = url.matcher(content);
                if (matcher.find()) {
                    String result = matcher.group(1);
//                    HttpConnectManager.instance().cache.put(this.url,result);
                    return result;
                } else {
                    return null;
                }
            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            }
//        }


    }

    public static void main(String[] args) {
        SimpleUrlMatcher simpleUrlMatcher = new SimpleUrlMatcher("http://192.168.1.101","getObj\\(\"Frm_Logintoken\"\\)\\.value = \"(.*)\";");
        String s = simpleUrlMatcher.match();
        System.out.println(s);
    }
}
