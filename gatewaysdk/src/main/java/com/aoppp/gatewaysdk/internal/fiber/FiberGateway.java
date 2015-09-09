package com.aoppp.gatewaysdk.internal.fiber;

import android.util.Log;
import android.webkit.CookieManager;

import com.aoppp.gatewaysdk.domain.DeviceProfile;
import com.aoppp.gatewaysdk.domain.Gateway;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guguyanhua on 7/6/15.
 */
public class FiberGateway extends Gateway {

    public FiberGateway(DeviceProfile deviceProfile) {
        super(deviceProfile);
    }

    public volatile Cookie sessionCookie;

    private DefaultHttpClient client;

//    public boolean loginWithGet(){
//        try {
//            client = new DefaultHttpClient();
//            HttpGet httpget = new HttpGet("http://" + deviceProfile.getIp() + "/login.cgi?username=" + deviceProfile.getUserName() + "&psd=" + deviceProfile.getPassword());
//            HttpContext context = new BasicHttpContext();
//            try {
//                client.execute(httpget, context);
//            }catch (Exception ex){
//
//            }
//            HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(
//                    ExecutionContext.HTTP_REQUEST);
//            HttpHost currentHost = (HttpHost)  context.getAttribute(
//                    ExecutionContext.HTTP_TARGET_HOST);
//            String currentUrl = (currentReq.getURI().isAbsolute()) ? currentReq.getURI().toString() : (currentHost.toURI() + currentReq.getURI());
//
//
//
//            HttpGet newUrlReq = new  HttpGet("http://" + deviceProfile.getIp());
//            HttpResponse res = client.execute(newUrlReq);
//            if(res.getStatusLine().getStatusCode() != 200){
//                throw new RuntimeException(this.deviceProfile.getProvider() + " login fail. status code is " + res.getStatusLine().getStatusCode());
//            }
//
//            //    c = new SimpleMatcher(c,"(.*)path=/").match();
//            List<Cookie> cookies = client.getCookieStore().getCookies();
//            for (int i = 0; i < cookies.size(); i++) {
//                sessionCookie = cookies.get(i);
//            }
//
//
//            CookieManager cookieManager = CookieManager.getInstance();
//            if (sessionCookie != null) {
//                cookieManager.removeSessionCookie();
//                String cookieString =
//                        sessionCookie.getName()
//                                + "="
//                                + sessionCookie.getValue()
//                                + "; domain=" + sessionCookie.getDomain();
//                cookieManager.setCookie(sessionCookie.getDomain(), cookieString);
////                        cookieManager.flush();
//            }
//            return true;
//        }catch(Exception ex){
//            Log.e(this.getClass().getCanonicalName(), "Login with Get:" + ex.getMessage(), ex);
//            throw new RuntimeException(this.deviceProfile.getProvider() + " login fail.", ex);
//        }
//
//
//    }

    @Override
    public boolean login() {
        try {
            HttpPost request = new HttpPost("http://" + deviceProfile.getIp() + "/ctlogin.cmd");
            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("username", deviceProfile.getUserName()));
            parameters.add(new BasicNameValuePair("password", deviceProfile.getPassword()));
            HttpEntity entity = new UrlEncodedFormEntity(parameters);
            request.setEntity(entity);
            client = new DefaultHttpClient();

            HttpResponse res = client.execute(request);
            if(res.getStatusLine().getStatusCode() != 200){
                throw new RuntimeException(this.deviceProfile.getProvider() + " login fail. status code is " + res.getStatusLine().getStatusCode());
            }
            //    c = new SimpleMatcher(c,"(.*)path=/").match();
            List<Cookie> cookies = client.getCookieStore().getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                sessionCookie = cookies.get(i);
            }


            CookieManager cookieManager = CookieManager.getInstance();
            if (sessionCookie != null) {
                cookieManager.removeSessionCookie();
                String cookieString =
                        sessionCookie.getName()
                                + "="
                                + sessionCookie.getValue()
                                + "; domain=" + sessionCookie.getDomain();
                cookieManager.setCookie(sessionCookie.getDomain(), cookieString);
//                        cookieManager.flush();
            }
            return true;
        } catch (Exception e) {
            Log.e(this.getClass().getCanonicalName(), e.getMessage(), e);
            throw new RuntimeException(this.deviceProfile.getProvider() + " login fail.", e);
            //return loginWithGet();
        }
    }

    @Override
    public boolean logout() {
//        try {
//            HttpGet request = new HttpGet("http://" + deviceProfile.getIp() + "/ctlogout.cmd");
//            List<Cookie> cookies = client.getCookieStore().getCookies();
//            for (int i = 0; i < cookies.size(); i++) {
//                sessionCookie = cookies.get(i);
//            }
//            if(sessionCookie != null) {
//                request.addHeader("Cookie", sessionCookie.getValue());
//            }
//            HttpResponse res = client.execute(request);
//            return true;
//        } catch (IOException e) {
//            throw new RuntimeException(this.deviceProfile.getProvider() + " logout fail.", e);
//        }
        return true;
   }
}
