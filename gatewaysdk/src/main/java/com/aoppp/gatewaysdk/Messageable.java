package com.aoppp.gatewaysdk;

import android.app.Activity;

/**
 * Created by guguyanhua on 7/24/15.
 */
public abstract class Messageable extends Activity{
    public static final int CHECK_DONE = 10, CHECKING_OUTPUT = 100;
    public abstract void sendOutputMessage(int code, String msgInfo);
}
