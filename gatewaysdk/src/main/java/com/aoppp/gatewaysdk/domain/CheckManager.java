package com.aoppp.gatewaysdk.domain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.aoppp.gatewaysdk.MessageConst;
import com.aoppp.webviewdom.internal.WebViewJs;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by guguyanhua on 6/24/15.
 *
 */
public class CheckManager {


    private DeviceProfile deviceProfile;

    private RouterCheckConf checkConf;

    private static CheckManager instance;

    private static volatile CheckResult lastResult;

    private Gateway gateway;

    public synchronized static CheckManager instance(){
        if(instance == null){
            instance = new CheckManager();
        }
        return instance;
    }

    public static CheckResult getLastCheckResult() {
        return lastResult;
    }

    public static void setLastCheckResult(CheckResult result) {
        lastResult = result;
    }


    public void loadCheckConf(Context context){
       checkConf = RouterCheckConf.loadConf(context);
    }

    public void willCheckDevice(DeviceProfile deviceProfile){
        this.deviceProfile = deviceProfile;
        gateway = Gateway.build(deviceProfile);
        lastResult = null;
    }

    public void clear(Activity activity){
        //TODO manage check thread
        this.gateway.clearCache(activity);
        this.deviceProfile = null;
        this.gateway = null;

    }

    public CheckItem[] getAllCheckItems(){
        return checkConf.getCheckItems();
    }


    public void login() throws Exception {
        if(gateway==null){
            throw new Exception("Please set device profile first.");
        }

        this.gateway.login();

    }

    public void logout() throws Exception {
        if(gateway==null){
            throw new Exception("Please set device profile first.");
        }
        this.gateway.logout();



    }

    public CheckResult check(Activity context,Handler handler, WebViewJs webViewJs) throws Exception{
        if(gateway==null){
            throw new Exception("Please set device profile first.");
        }
        return check(context, handler, webViewJs, checkConf.getCheckItems());
    }

    public CheckResult check(Activity context,Handler handler,WebViewJs webViewJs , CheckItem[] checkItems){

        List<CheckItem> result = Lists.newArrayList();
        long cost = System.currentTimeMillis();
        try {
            //gateway.login();
            int total = checkItems.length;
            //总权重 85%    index / total  * 85
            int index = 1;
            for (CheckItem checkItem : checkItems) {
                //debug FIXME
                Thread.sleep(1000);
                //debug

                double process = (double)index / (double)total;
                int processPercent = (int) (process * 85);
                sendMessageToView(handler,MessageConst.CHECKING_OUTPUT, "正在检测:" + checkItem.getName(),processPercent);
                CheckItem item = null;
                try {
                    item = checkItem.check(gateway, context, webViewJs);
                    result.add(item);
                }catch (Exception ex){
                    item = checkItem.clone();
                    item.setState(-1);
                    result.add(item);
                }finally {
                    index ++;
                }


            }
           // gateway.logout();
    //        gateway.logout(deviceProfile);

        }finally {
            cost = System.currentTimeMillis() - cost;
            lastResult =  new CheckResult(0,"",result, cost);
            return lastResult;
        }

    }

    public void sendMessageToView(Handler handler,int code, String msgInfo,int process){
        Message msg = new Message();
        //msg.set
        msg.what = code;
        Bundle bundle = new Bundle();
        bundle.putCharSequence("msg", msgInfo);
        bundle.putInt("process",process);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }


    public DeviceProfile getDefaultProfile(){

//        DeviceProfile profile = new DeviceProfile("zte","telecomadmin", "telecomadmin12223015", "192.168.1.101");
//        DeviceProfile profile = new DeviceProfile("huawei","telecomadmin", "nE7jA%5m", "192.168.1.102");

        //DeviceProfile profile = new DeviceProfile("telecomadmin", "telecomadmin63135359", "192.168.1.1");
//        DeviceProfile profile = new DeviceProfile("bell","telecomadmin", "telecomadmin57386767", "192.168.1.103");
        DeviceProfile profile = new DeviceProfile("fiber","telecomadmin", "telecomadmin73873188", "192.168.1.104");
        return profile;
    }

}
