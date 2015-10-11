package com.aoppp.webviewdom;

import android.app.Activity;
import android.util.Log;

import com.aoppp.webviewdom.internal.WebViewJs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by guguyanhua on 7/22/15.
 * 加载构建page list
 * 倒排 element --> page
 * 提供 indicator 查找接口
 */
public class PageManager {

    private static PageManager pageManager;
    private Map<String, Map<String, Page>> pageMetaByIndicator = new Hashtable<>();

    private Map<String, String> cache = new Hashtable<>();

    private String[] supportProviders = new String[]{"HUAWEI","ZTE","BELL","FIBER"};

    private PageManager(Activity activity) {
        Gson gson = new Gson();
        Type mapType = new TypeToken<ArrayList<PageMeta>>() {
        }.getType();
        try {
            for (String provider : supportProviders) {
                InputStream inputStream = activity.getAssets().open(provider + ".conf");
                ArrayList<PageMeta> config = gson.fromJson(new BufferedReader(
                        new InputStreamReader(inputStream)), mapType);

                //Map<String, Page> pageMap = new HashMap<>();
                for (PageMeta pageMeta : config) {
                    Page page = new Page();
                    page.setPageMeta(pageMeta);
                    page.setUrl(pageMeta.getUrl());
                    page.setAllElements(pageMeta.getAllElements());


                    String deviceTypeKey =
                            pageMeta.getDeviceType()==null?provider:(provider + "|" + pageMeta.getDeviceType());

                    Map<String, Page> pageMap = pageMetaByIndicator.get(deviceTypeKey);
                    if(pageMap==null){
                        pageMap = new HashMap<>();
                        pageMetaByIndicator.put(deviceTypeKey, pageMap);
                    }

                    for (ElementMeta elementMeta : pageMeta.getAllElements()) {
                        pageMap.put(elementMeta.getIndicator(), page);
                    }
                }


            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public static synchronized PageManager getInstance(Activity activity) {
        if (pageManager == null) {
            pageManager = new PageManager(activity);
        }
        return pageManager;
    }

    public IndicatorResult fetchDeviceType(Activity activity,
                                          WebViewJs webViewJs,
                                          String provider,
                                          String ip,

                                          long timeout,
                                          TimeUnit timeUnit) {
        String indicatorId = "device_type";
        List<Page> pages = getAllPageMetaByIndicator(activity, webViewJs, provider, indicatorId);

        for(Page page:pages) {
            page.setIp(ip);
            try {
                String json = page.syncFetch(timeout, timeUnit);
                if (json == null) {
                    throw new RuntimeException("indicator " + indicatorId + " fetch fail");
                } else {
                    Map<String, String> result = toMap(json);

                    String errorInfo = result.get("error");
                    String deviceType =result.get(indicatorId);
                    if(deviceType==null || deviceType.equals("null")){
                        throw new RuntimeException("indicator " + indicatorId + " fetch fail");
                    }
                    return new IndicatorResult(0, errorInfo, result);
                }

            } catch (Throwable e) {
                Log.e(this.getClass().getCanonicalName(), "indicator " + indicatorId + " fetch fail", e);

            }

        }
        throw new RuntimeException("indicator " + indicatorId + " fetch fail");
    }

    public IndicatorResult fetchIndicator(Activity activity,
                                          WebViewJs webViewJs,
                                          String provider,
                                          String deviceType,
                                          String ip,
                                          String indicatorId,
                                          long timeout,
                                          TimeUnit timeUnit,
                                          boolean useCache) {
        if (useCache) {
            String value = cache.get(indicatorId);
            if (value == null) {
                IndicatorResult result = fetchIndicator(activity, webViewJs, provider, deviceType, ip, indicatorId, timeout, timeUnit);
                //add to cache
                cache.putAll(result.getResult());
                return result;
            } else {
                Map<String, String> result = new Hashtable<>();
                result.put(indicatorId, value);
                return new IndicatorResult(0, "", result);
            }
        } else {
            IndicatorResult result = fetchIndicator(activity, webViewJs, provider, deviceType, ip, indicatorId, timeout, timeUnit);
            return result;
        }


    }

    public IndicatorResult fetchIndicator(Activity activity,
                                          WebViewJs webViewJs,
                                          String provider,
                                          String deviceType,
                                          String ip,
                                          String indicatorId,
                                          long timeout,
                                          TimeUnit timeUnit) {
        Page page = getPageMeta(activity, webViewJs, provider, deviceType, indicatorId);
        page.setIp(ip);
        try {
            String json = page.syncFetch(timeout, timeUnit);
            if (json == null) {
                throw new RuntimeException("indicator " + indicatorId + " fetch fail");
            } else {
                Map<String, String> result = toMap(json);

                String errorInfo = result.get("error");
                return new IndicatorResult(0, errorInfo, result);
            }

        } catch (Throwable e) {
            Log.e(this.getClass().getCanonicalName(),"indicator " + indicatorId + " fetch fail",e);
            throw new RuntimeException("indicator " + indicatorId + " fetch fail", e);
        }

    }

    private Map<String, String> toMap(String json) {
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> result = gson.fromJson(json, mapType);
        return result;
    }
    public void clearCache(){
        cache.clear();
    }

    private List<Page> getAllPageMetaByIndicator(Activity activity, WebViewJs webViewJs, String provider,  String indicatorId){

        List<Page> pages = new ArrayList<Page>();
        for(String key:pageMetaByIndicator.keySet()) {
            if(key.startsWith(provider)) {
                Page page = pageMetaByIndicator.get(key).get(indicatorId);
                if (page != null) {
                    page.setActivity(activity);
                    page.setWebViewJs(webViewJs);
                    pages.add(page);
                }
            }
        }
        return pages;
    }

    private Page getPageMeta(Activity activity, WebViewJs webViewJs, String provider, String deviceType, String indicatorId) {
        if(deviceType!=null) {
            String deviceTypeKey =  provider + "|" + deviceType;
            Map<String, Page> pageMap = pageMetaByIndicator.get(deviceTypeKey);
            if (pageMap != null) {
                Page page = pageMap.get(indicatorId);
                if(page!=null){
                    page.setActivity(activity);
                    page.setWebViewJs(webViewJs);
                    return page;
                }
            }
        }

        Page page = pageMetaByIndicator.get(provider).get(indicatorId);
        if (page != null) {
            page.setActivity(activity);
            page.setWebViewJs(webViewJs);
            return page;
        }
        throw new RuntimeException("indicator " + indicatorId + " not found");
    }
}
