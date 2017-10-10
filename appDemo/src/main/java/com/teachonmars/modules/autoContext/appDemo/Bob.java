package com.teachonmars.modules.autoContext.appDemo;

import android.content.Context;
import android.util.Log;

import com.teachonmars.modules.autoContext.annotation.NeedContext;

public class Bob {

    private static final String TAG = Bob.class.getSimpleName();

    @NeedContext(priority = 2)
    public static void boob(Context context) {
        Log.d(TAG, "Context needer with prio 2");
    }

    @NeedContext(priority = 5)
    public static void baam(Context context) {
        Log.d(TAG, "Context needer with prio 5");
    }

    @NeedContext(priority = 1)
    public static void bim(Context context) {
        Log.d(TAG, "Context needer with prio 1");
    }

    @NeedContext(priority = 1)
    public static void babb(Context context) {
        Log.d(TAG, "Context needer with prio 1");
    }

    @NeedContext(priority = 24)
    public static void bacb(Context context) {
        Log.d(TAG, "Context needer with prio 24");
    }

}
