package com.aoppp.gatewaysdk.internal.bell;

import com.aoppp.gatewaysdk.domain.DeviceProfile;
import com.aoppp.gatewaysdk.domain.Gateway;
import com.aoppp.gatewaysdk.internal.HttpConnectManager;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by guguyanhua on 7/6/15.
 */
public class BellGateway extends Gateway {

    public BellGateway(DeviceProfile deviceProfile) {
        super(deviceProfile);
    }

    @Override
    public boolean login() {
        OkHttpClient client = HttpConnectManager.instance().getClient();

        Request request = new Request.Builder()
                .url("http://" + deviceProfile.getIp()
                        + "/login.cgi?username=" + deviceProfile.getUserName()
                        + "&psd=" + deviceProfile.getPassword())
                .get()
                .build();
        try {
            Response res = client.newCall(request).execute();
            String string = res.body().string();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(this.deviceProfile.getProvider()+" login fail.",e);
        }

    }

    @Override
    public boolean logout() {
        //logout.cgi
        OkHttpClient client = HttpConnectManager.instance().getClient();

        Request request = new Request.Builder()
                .url("http://" + deviceProfile.getIp()
                        + "/logout.cgi")
                .get()
                .build();
        try {
            Response res = client.newCall(request).execute();
            String string = res.body().string();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(this.deviceProfile.getProvider()+" logout fail.",e);
        }
    }
}
