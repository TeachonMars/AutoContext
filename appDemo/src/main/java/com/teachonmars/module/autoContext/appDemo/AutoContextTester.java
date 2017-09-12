package com.teachonmars.module.autoContext.appDemo;

import android.content.Context;

import com.teachonmars.module.autoContext.annotation.NeedContext;

public class AutoContextTester {
    public static Context appContext;

    @NeedContext
    public static void saveContext(Context appContext) {
        AutoContextTester.appContext = appContext;
    }

    @NeedContext(priority = 2)
    public static void saveBob(Context appContext) {
        AutoContextTester.appContext = appContext;
    }
}
