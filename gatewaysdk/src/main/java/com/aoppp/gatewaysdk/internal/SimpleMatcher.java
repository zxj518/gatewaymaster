package com.aoppp.gatewaysdk.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guguyanhua on 6/24/15.
 */
public class SimpleMatcher {
    String content;
    String pattern;

    public SimpleMatcher(String content, String pattern) {
        this.content = content;
        this.pattern = pattern;
    }





    public String match(){
        Pattern url = Pattern.compile(pattern);
        Matcher matcher = url.matcher(content);
        if(matcher.find()){
            String result = matcher.group(1);
            return result;
        }else {
            return null;
        }
    }
}
