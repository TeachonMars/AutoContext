package com.teachonmars.autoinit.appDemo;

import android.content.Context;

import com.teachonmars.autoinit.ContextNeedy;

public class AutoInitTester implements ContextNeedy {
    public static Context appContext;

    @Override
    public void init(Context appContext) {
        AutoInitTester.saveContext(appContext);
    }

    private static void saveContext(Context appContext) {
        AutoInitTester.appContext = appContext;
    }
}
