package com.aoppp.gatewaysdk.internal.hw;

import android.util.Log;
import android.webkit.CookieManager;

import com.aoppp.gatewaysdk.domain.DeviceProfile;
import com.aoppp.gatewaysdk.domain.Gateway;
import com.aoppp.webviewdom.IndicatorResult;
import com.aoppp.webviewdom.PageManager;
import com.aoppp.webviewdom.internal.WebViewJs;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.List;

/**
 * Created by guguyanhua on 6/26/15.
 */
public class HwGateway extends Gateway {

    public HwGateway(DeviceProfile deviceProfile){
        super(deviceProfile);
    }

    public Cookie sessionCookie;

    @Override
    public boolean login() {
        try {
            CookieHelper cookieHelper = new CookieHelper(deviceProfile.getIp());
            String c = cookieHelper.getCookie(deviceProfile.getUserName(), deviceProfile.getPassword());

            HttpGet request = new HttpGet("http://" + deviceProfile.getIp() + "/login.cgi");
            request.addHeader("Cookie", c);
            DefaultHttpClient client = new DefaultHttpClient();

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
        } catch (Exception e) {
            Log.e(e.getMessage(), e.getMessage());
        }

        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }



}
