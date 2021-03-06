package com.aoppp.gatewaysdk.domain;

import android.app.Activity;

import com.aoppp.gatewaysdk.internal.bell.BellGateway;
import com.aoppp.gatewaysdk.internal.fiber.FiberGateway;
import com.aoppp.gatewaysdk.internal.hw.HwGateway;
import com.aoppp.gatewaysdk.internal.zte.ZteGateway;
import com.aoppp.webviewdom.IndicatorResult;
import com.aoppp.webviewdom.PageManager;
import com.aoppp.webviewdom.internal.WebViewJs;

import java.util.concurrent.TimeUnit;

/**
 * Created by guguyanhua on 6/24/15.
 * 指代一个网关
 */
public abstract class Gateway {




    protected DeviceProfile deviceProfile;

    protected DeviceBasicInfo basicInfo;

    public Gateway(DeviceProfile deviceProfile) {

        this.deviceProfile = deviceProfile;
    }




    public abstract boolean login();

    public abstract boolean logout();

    public Indicator checkIndicator(Activity activity,
                                    WebViewJs webViewJs,
                                    String deviceType,
                                    Indicator indicator,
                                    long timeout,
                                    TimeUnit timeUnit) {

        IndicatorResult result = PageManager.getInstance(activity).fetchIndicator(
                activity,webViewJs,
                deviceProfile.getProvider(),
                deviceType,
                deviceProfile.getIp(),
                indicator.getName(),
                timeout,timeUnit,
                true);

        indicator.setValue(result.getResult().get(indicator.getName()));

        return indicator;
    }

    public void clearCache(Activity activity){
        PageManager.getInstance(activity).clearCache();
    }


    public DeviceBasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(DeviceBasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public static Gateway build(DeviceProfile deviceProfile){
        if(deviceProfile.getProvider().equals(DeviceProvider.ZTE.getSimpleName())){
            return new ZteGateway(deviceProfile);
        }else if(deviceProfile.getProvider().equals(DeviceProvider.HUAWEI.getSimpleName())){
            return new HwGateway(deviceProfile);
        }else if(deviceProfile.getProvider().equals(DeviceProvider.BELL.getSimpleName())){
            return new BellGateway(deviceProfile);
        }else if(deviceProfile.getProvider().equals(DeviceProvider.FIBER.getSimpleName())){
            return new FiberGateway(deviceProfile);
        }else{
            return null;
        }
    }
}
