package com.aoppp.gatewaysdk.internal.hw;

import com.aoppp.gatewaysdk.internal.SimpleUrlMatcher;


/**
 * Created by guguyanhua on 6/24/15.
 */
public class CookieHelper {


    String ip;

    public CookieHelper(String ip) {
        this.ip = ip;
    }

    //"Cookie=" + "rid=" + SHA256("" + cnt) + SHA256(Username.value + cnt ) + SHA256(SHA256(MD5(Password.value)) + cnt) + ":" + "Language:" + Language  + ":SubmitType=SetRoute"+":" +"id=-1;path=/";


    //Cookie=rid=b4163ccccc8b14752aae99444822010a08e92b3e1c0ca7da5c9e9eca1254f9f1271fefbaef0530c595d74ab9b6f7c08ce406dae5e330c4862816f08b804d84d7ba072a3185ce26ef2602e62c041489624d5a2423d1be8272714d4abeefe76a8c:Language:chinese:SubmitType=Login:id=-1


    //Cookie=rid=5405ac0ca336380074d4146d466449ed57a9be815cff061b917129233731e440b92c5af2dc5078b6b7967c25a85c360fc5f64feb9616d745027b08b62008867445e2f2baf53fef2b08974aa2f176a1a5da68859af758908d80fd084d058bd7bc:Language:chinese:SubmitType=Login:id=-1

//    rid=ca36c5e931dedbae639cb7429d432862fdc891e17b51913dc127b4e32042801d59cc4d506f197d7a286de753815e253c03896a21f98abf144166eba75cab752823ed474661a03c73b6b85abbf20c6dbb3225c97b6ff1391e434cbac216f76fd7af10c153d9cd1a2b8e266db1f9720616:Language:chinese:SubmitType=Login:id=-1
//    String cnt = "bfaabacb34fc032d28b4060d6ab89aed"; //变化的


    public String getCnt(){
        SimpleUrlMatcher simpleUrlMatcher = new SimpleUrlMatcher("http://"+ ip,"function GetRandCnt\\(\\) \\{ return '(.*)'; \\}");
        return simpleUrlMatcher.match();
    }


    public String getCookie(String userName,String password){
        try {
            String cnt = getCnt();
            StringBuilder builder = new StringBuilder("rid=");
            builder.append(DigestUtils2.sha256Hex(cnt))
                    .append(DigestUtils2.sha256Hex(userName + cnt))
                    .append(DigestUtils2.sha256Hex(DigestUtils2.sha256Hex(DigestUtils2.md5Hex(password)) + cnt))
                    .append(":Language:chinese:SubmitType=Login:id=-1");
            return builder.toString();
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }


    }


    public static void main(String[] args) {
        CookieHelper cookie = new CookieHelper("192.168.1.102");
        String c = cookie.getCookie("telecomadmin","nE7jA%5m");
        System.out.println(c);

//        System.out.println(DigestUtils.sha256Hex("abc"));
    }
}
