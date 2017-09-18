package com.teachonmars.module.autoContext.appDemo;

import android.content.Context;
import android.util.Log;

import com.teachonmars.module.autoContext.annotation.NeedContext;

public class Bob {

    private static final String TAG = Bob.class.getSimpleName();

    @NeedContext(priority = 2)
    public static void boob(Context context) {
        Log.d(TAG, "Context needer with prio 2");

    }

    @NeedContext(priority = 1)
    public static void bim(Context context) {
        Log.d(TAG, "Context needer with prio 1");
    }
}
