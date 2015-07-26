package com.aoppp.gatewaysdk.domain;

/**
 * Created by guguyanhua on 6/24/15.
 */
public class DeviceProfile {


    String provider;
    String version;
    String userName;
    String password;
    String ip;


    public DeviceProfile(String provider, String userName, String password, String ip) {
        this.provider = provider;//e. huawei,zte
        this.userName = userName;
        this.password = password;
        this.ip = ip;
        this.version = "XXXX";
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
