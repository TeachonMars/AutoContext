package com.teachonmars.modules.autoContext.appDemo;

import android.content.Context;
import android.util.Log;

public class AutoContextTester {
    private static final String TAG = AutoContextTester.class.getSimpleName();
    public static Context appContext;

    @com.teachonmars.modules.autoContext.annotation.NeedContext
    public static void saveContext(Context appContext) {
        AutoContextTester.appContext = appContext;
        Log.d(TAG, "Context needer with prio default");
    }

    @com.teachonmars.modules.autoContext.annotation.NeedContext(priority = 2)
    public static void saveBob(Context appContext) {
        AutoContextTester.appContext = appContext;
        Log.d(TAG, "Context needer with prio 2");
    }
}
