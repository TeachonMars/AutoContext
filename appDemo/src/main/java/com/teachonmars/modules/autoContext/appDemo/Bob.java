package com.teachonmars.modules.autoContext.appDemo;

import android.content.Context;
import android.util.Log;

public class Bob {

    private static final String TAG = Bob.class.getSimpleName();

    @com.teachonmars.modules.autoContext.annotation.NeedContext(priority = 2)
    public static void boob(Context context) {
        Log.d(TAG, "Context needer with prio 2");

    }

    @com.teachonmars.modules.autoContext.annotation.NeedContext(priority = 1)
    public static void bim(Context context) {
        Log.d(TAG, "Context needer with prio 1");
    }
}