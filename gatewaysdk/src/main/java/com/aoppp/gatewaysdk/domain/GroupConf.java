package com.aoppp.gatewaysdk.domain;

import android.content.Context;
import android.util.Log;

import com.aoppp.webviewdom.PageMeta;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by guguyanhua on 7/30/15.
 */
public class GroupConf {

    public Map<String,List<String>> groups = Maps.newHashMap();

    public static GroupConf loadConf(Context context){
        InputStream is = null;
        try {
            is = context.getAssets().open("group.conf");
            InputStreamReader reader = new InputStreamReader(is, "utf-8");
            Gson gson = new Gson();

            Type mapType = new TypeToken< Map<String,List<String>>>() {
            }.getType();
            GroupConf groupConf = new GroupConf();
            groupConf.groups = gson.fromJson(reader,
                    mapType);

            return groupConf;
        }catch (IOException ex){

            Log.e("group", ex.getMessage(), ex);
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
