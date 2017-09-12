package com.teachonmars.module.autoContext.appDemo;

import android.content.Context;

import com.teachonmars.module.autoContext.annotation.NeedContext;

public class Bob {

    @NeedContext(priority = 2)
    public static void boob(Context context) {

    }

    @NeedContext(priority = 1)
    public static void bim(Context context) {

    }
}
