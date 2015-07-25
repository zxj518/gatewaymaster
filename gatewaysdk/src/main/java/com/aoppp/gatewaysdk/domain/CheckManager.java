package com.aoppp.gatewaysdk.domain;

import android.content.Context;

import com.aoppp.gatewaysdk.Messageable;
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

    private static CheckResult lastResult;

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


    public CheckManager() {

 //       gateway = selectGateway();

//        CheckItem _1_TR069_R_VID_45_CONNECT_STATE = new CheckItem();
//        _1_TR069_R_VID_45_CONNECT_STATE.setName("1_TR069_R_VID_45 链接状态");
//        Indicator indicator = new Indicator();
//        indicator.setName("1_TR069_R_VID_45 链接状态");
//        indicator.setDefaultValue("断开");
//        indicator.setUrl("http://%s/getpage.gch?pid=1002&nextpage=status_ethwan_if_t.gch");
//        indicator.setPattern("<table id=\"TestContent2\"[\\s\\S]*<td class=\"tdleft_1\">连接状态</td>\n<td class=\"tdright\">(.*)</td>");
//        _1_TR069_R_VID_45_CONNECT_STATE.setIndicators(Lists.newArrayList(indicator));
//        this.checkItemList.add(_1_TR069_R_VID_45_CONNECT_STATE);
//
//
//        CheckItem physicsVesion = new CheckItem();
//        physicsVesion.setName("硬件版本");
//        Indicator physicsVesionIndicator = new Indicator();
//        physicsVesionIndicator.setName("硬件版本");
//        physicsVesionIndicator.setDefaultValue("V5.0");
//        physicsVesionIndicator.setUrl("http://%1$s/template.gch?pid=1002&nextpage=status_dev_info_t.gch");
//        physicsVesionIndicator.setPattern("<td class=\"tdleft\">硬件版本号</td>\n" +
//                "<td id=\"Frm_HardwareVer\" name=\"Frm_HardwareVer\" class=\"tdright\">(.*)</td>");
//        physicsVesion.setIndicators(Lists.newArrayList(physicsVesionIndicator));
//        this.checkItemList.add(physicsVesion);
//
//
//        CheckItem softVersion = new CheckItem();
//        softVersion.setName("软件版本");
//        Indicator softVersionIndicator = new Indicator();
//        softVersionIndicator.setName("软件版本");
//        softVersionIndicator.setDefaultValue("F412_IMS_V5.0.0P1T1_JS1309");
//        softVersionIndicator.setUrl("http://%1$s/template.gch?pid=1002&nextpage=status_dev_info_t.gch");
//        softVersionIndicator.setPattern("<td class=\"tdleft\">软件版本号</td>\n" +
//                "<td id=\"Frm_SoftwareVer\" name=\"Frm_SoftwareVer\" class=\"tdright\">(.*)</td>");
//        softVersion.setIndicators(Lists.newArrayList(softVersionIndicator));
//        this.checkItemList.add(softVersion);
//        this.checkConf = checkConf;
    }

//    private List<CheckItem> createDefaultCheckItems(){
//
//    }

    public void loadCheckConf(Context context){
       checkConf = RouterCheckConf.loadConf(context);
    }

    public void willCheckDevice(DeviceProfile deviceProfile){
        this.deviceProfile = deviceProfile;
        gateway = Gateway.build(deviceProfile);
        lastResult = null;
    }

    public void clear(){
        //TODO manage check thread
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

    public CheckResult check(Messageable context, WebViewJs webViewJs) throws Exception{
        if(gateway==null){
            throw new Exception("Please set device profile first.");
        }
        return check(context,  webViewJs, checkConf.getCheckItems());
    }

    public CheckResult check(Messageable context,WebViewJs webViewJs , CheckItem[] checkItems){

        List<CheckItem> result = Lists.newArrayList();
        long cost = System.currentTimeMillis();
        try {
            //gateway.login();
            for (CheckItem checkItem : checkItems) {
                context.sendOutputMessage(Messageable.CHECKING_OUTPUT, "正在检测:" + checkItem.getName());
                CheckItem item = checkItem.check(gateway, context, webViewJs);
                result.add(item);
            }
           // gateway.logout();
    //        gateway.logout(deviceProfile);

        }finally {
            cost = System.currentTimeMillis() - cost;
            lastResult =  new CheckResult(0,"",result, cost);
            return lastResult;
        }

    }


    public DeviceProfile getDefaultProfile(){

//        DeviceProfile profile = new DeviceProfile("zte","telecomadmin", "telecomadmin12223015", "192.168.1.101");
//        DeviceProfile profile = new DeviceProfile("huawei","telecomadmin", "nE7jA%5m", "192.168.1.102");

        //DeviceProfile profile = new DeviceProfile("telecomadmin", "telecomadmin63135359", "192.168.1.1");
//        DeviceProfile profile = new DeviceProfile("bell","telecomadmin", "telecomadmin57386767", "192.168.1.103");
        DeviceProfile profile = new DeviceProfile("fiber","telecomadmin", "telecomadmin73873188", "192.168.1.104");
        return profile;
    }


//
//    public static void main(String[] args) {
//        //DeviceProfile profile = new DeviceProfile("telecomadmin", "telecomadmin12223015", "192.168.1.101");
//        DeviceProfile profile = new DeviceProfile("telecomadmin", "telecomadmin63135359", "192.168.1.1");
//        CheckResult result = CheckManager.instance().check(profile, CheckManager.instance().getAllCheckItems());
//
//        System.out.println(result);
//    }

}
