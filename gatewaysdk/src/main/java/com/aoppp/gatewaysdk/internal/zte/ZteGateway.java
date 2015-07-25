package com.aoppp.gatewaysdk.internal.zte;

import com.aoppp.gatewaysdk.domain.DeviceProfile;
import com.aoppp.gatewaysdk.domain.Gateway;
import com.aoppp.gatewaysdk.internal.HttpConnectManager;
import com.aoppp.gatewaysdk.internal.SimpleUrlMatcher;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by guguyanhua on 6/24/15.
 */
public class ZteGateway extends Gateway {

    public ZteGateway(DeviceProfile deviceProfile) {
        super(deviceProfile);
    }

    @Override
    public boolean login() {
        OkHttpClient client = HttpConnectManager.instance().getClient();
        SimpleUrlMatcher token = new SimpleUrlMatcher("http://" + deviceProfile.getIp(),"getObj\\(\"Frm_Logintoken\"\\)\\.value = \"(.*)\";");
        RequestBody formBody = new FormEncodingBuilder()
                .add("Frm_Logintoken", token.match())
                .add("Username", deviceProfile.getUserName())
                .add("Password", deviceProfile.getPassword())
                .add("action", "login")
                .add("frashnum", "")
                .build();
        Request request = new Request.Builder()
                .url("http://"+ deviceProfile.getIp() +"/")
                .post(formBody)
                .build();
        try {
            Response res = client.newCall(request).execute();
            String string = res.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean logout() {
        OkHttpClient client = HttpConnectManager.instance().getClient();
        RequestBody formBody = new FormEncodingBuilder()
                .add("logout", "1")
                .build();
        Request request = new Request.Builder()
                .url("http://"+ deviceProfile.getIp() +"/")
                .post(formBody)
                .build();
        try {
            Response res = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
