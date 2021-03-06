package com.teachonmars.modules.autoContext.appDemo;

import android.content.Context;
import android.util.Log;

import com.teachonmars.modules.autoContext.annotation.NeedContext;

public class AutoContextTester {
    private static final String TAG = AutoContextTester.class.getSimpleName();
    public static Context appContext;

    @NeedContext
    public static void saveContext(Context appContext) {
        AutoContextTester.appContext = appContext;
        Log.d(TAG, "Context needer with prio default");
    }

    @NeedContext(priority = 2)
    public static void saveBob(Context appContext) {
        AutoContextTester.appContext = appContext;
        Log.d(TAG, "Context needer with prio 2");
    }

    @NeedContext
    public static void boob(Context appContext) {
        AutoContextTester.appContext = appContext;
        Log.d(TAG, "Context needer with prio default");
    }

}
