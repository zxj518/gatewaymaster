package com.aoppp.webviewdom.internal;

import com.aoppp.webviewdom.ElementMeta;
import com.aoppp.webviewdom.Page;

/**
 * Created by guguyanhua on 7/2/15.
 * //生成一长的js函数,把所有的值传回来
 */
public class JsFunction {

    private Page page;

    public JsFunction(Page page) {
        this.page = page;
    }

    public String gen(boolean debug) {
        StringBuilder js = new StringBuilder("javascript:function fetch(){\n");
        js.append("try{\n");
        js.append("var result = new Object();result.error='';");
        for (ElementMeta elementMeta : page.getAllElements()) {
            if(elementMeta.getXpath().trim().isEmpty() && elementMeta.getJs().trim().isEmpty()){
                continue;
            }
            js.append("try{\n")
                    .append("var ")
                    .append(elementMeta.getIndicator())
                    .append(" = ");
            if (elementMeta.getJs() != null && !elementMeta.getJs().trim().isEmpty()) {
                js.append(elementMeta.getJs());
            } else if (elementMeta.getXpath() != null && !elementMeta.getXpath().trim().isEmpty()) {
                js.append("document.evaluate('");
                js.append(elementMeta.getXpath());
                js.append("',document,null,XPathResult.FIRST_ORDERED_NODE_TYPE,null).singleNodeValue.innerText;");
            }

            js.append(";\n");

            js.append("result.")
                    .append(elementMeta.getIndicator())
                    .append(" = ")
                    .append(elementMeta.getIndicator())
                    .append(";\n");
            js.append("}catch(e){\n");
            js.append("result.error += e.toString() + ';'");
            js.append("}\n");
        }


        js.append("var toJson = JSON.stringify(result);\n");
//        js.append("tttt.abc();\n");
        if(debug) {
            js.append("window.alert(toJson);\n");
        }
        js.append("window.javaapi.onFinishJson(toJson);\n");
        js.append("}catch(e){\n");//try block end
        js.append("window.javaapi.onFailJson(e.toString());\n");
        js.append("}\n");
        js.append("}");
        //test 结束

        return js.toString();

    }

    public static void main(String[] args) {

    }
}
