package com.aoppp.gatewaysdk.domain;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by snowzou on 15/7/22.
 */
public class DeviceCheckConf {



    private CheckItem[] checkItems;

    private String deviceType;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public CheckItem[] getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(CheckItem[] checkItems) {
        this.checkItems = checkItems;
    }


    public static DeviceCheckConf loadCheckConf(Context context){
        InputStream is = null;
        try {
            is = context.getAssets().open("gateway_check.conf");
            InputStreamReader reader = new InputStreamReader(is, "utf-8");
            Gson gson = new Gson();
            DeviceCheckConf fromJson = gson.fromJson(reader,
                    DeviceCheckConf.class);
            return fromJson;
        }catch (IOException ex){

            Log.e("gatewaymaster", "load check conf failed cause by:" + ex.getMessage(), ex);
            return null;
        }finally {
            if(is!=null) {
                try {
                    is.close();
                } catch (Exception ex) {
                }
            }
        }

    }

    public static DeviceCheckConf loadCollectConf(Context context){
        InputStream is = null;
        try {
            is = context.getAssets().open("gateway_basicinfo.conf");
            InputStreamReader reader = new InputStreamReader(is, "utf-8");
            Gson gson = new Gson();
            DeviceCheckConf fromJson = gson.fromJson(reader,
                    DeviceCheckConf.class);
            return fromJson;
        }catch (IOException ex){

            Log.e("gatewaymaster", "load collect conf failed cause by:" + ex.getMessage(), ex);
            return null;
        }finally {
            if(is!=null) {
                try {
                    is.close();
                } catch (Exception ex) {
                }
            }
        }

    }

}
