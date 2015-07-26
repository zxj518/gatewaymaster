package com.aoppp.gatewaysdk.internal.fiber;

import android.util.Log;
import android.webkit.CookieManager;

import com.aoppp.gatewaysdk.domain.DeviceProfile;
import com.aoppp.gatewaysdk.domain.Gateway;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guguyanhua on 7/6/15.
 */
public class FiberGateway extends Gateway {

    public FiberGateway(DeviceProfile deviceProfile) {
        super(deviceProfile);
    }

    public Cookie sessionCookie;

    private DefaultHttpClient client;

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
        }
    }

    @Override
    public boolean logout() {
        try {
            HttpGet request = new HttpGet("http://" + deviceProfile.getIp() + "/ctlogout.cmd");
            request.addHeader("Cookie", sessionCookie.getValue());
            HttpResponse res = client.execute(request);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(this.deviceProfile.getProvider() + " logout fail.", e);
        }
    }
}
