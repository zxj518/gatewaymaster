package com.aoppp.gatewaysdk.domain;


/**
 * Created by snowzou on 15/7/29.
 */
public enum DeviceProvider {

    ZTE("ZTE", "中兴"),HUAWEI("HUAWEI", "华为"),BELL("BELL", "贝尔"), FIBER("FIBER", "烽火");

    private String simpleName;

    private String cnName;

    private DeviceProvider(String simpleName, String cnName){
        this.simpleName = simpleName;
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public static DeviceProvider toProvider(String simpleName) {
        for (DeviceProvider c : DeviceProvider.values()) {
            if (c.getSimpleName().equalsIgnoreCase(simpleName)) {
                return c;
            }
        }
        return null;
    }

    public static DeviceProvider toProviderByCnName(String cnName) {
        for (DeviceProvider c : DeviceProvider.values()) {
            if (c.getCnName().equalsIgnoreCase(cnName)) {
                return c;
            }
        }
        return null;
    }

    public String toString(){
        return simpleName;
    }
}
