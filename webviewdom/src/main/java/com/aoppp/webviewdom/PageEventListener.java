package com.aoppp.webviewdom;

/**
 * Created by guguyanhua on 7/2/15.
 */
public interface PageEventListener {
    //页面开始加载
    void pageOnStart(Page page);

    //页面加载完毕开始解析
    void pageOnLoaded(Page page);

    //页面结束解析
    void pageOnFinish(Page page);

    //某个元素开始解析
    void elementOnStart(Page page,ElementMeta element);
    //某个元素开始解析
    void elementOnFinish(Page page,ElementMeta element);

}
