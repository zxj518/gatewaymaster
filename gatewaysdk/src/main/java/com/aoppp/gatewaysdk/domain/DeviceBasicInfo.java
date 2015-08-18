package com.aoppp.gatewaysdk.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by snowzou on 15/8/18.
 */
public class DeviceBasicInfo {

    String deviceType;
    String deviceSN;
    String softwareVersion;
    String hardwareVersion;

    //Map<String, String> otherInfos = new HashMap<String, String>();
    public DeviceBasicInfo(String deviceType, String deviceSN ){
        this.deviceType = deviceType;
        this.deviceSN = deviceSN;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }
}
