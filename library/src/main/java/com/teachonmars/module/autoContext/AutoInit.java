package com.teachonmars.module.autoContext;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.teachonmars.module.autoContext.annotation.Constant;

import org.jetbrains.annotations.Nullable;

public class AutoInit extends ContentProvider {
    private static final String TAG = AutoInit.class.getSimpleName();

    @Override
    public boolean onCreate() {
        try {
            Class<?> contextNeedy = Class.forName(Constant.buildedClassName);
            contextNeedy.getMethod(Constant.buildedClassMain, Context.class).invoke(null, getContext());
        } catch (Exception e) {
            Log.e(TAG, "Generated code can't be found or executed, add @NeedContext to desired static method with Context as unique parameter", e);
        }
        return true;
    }

    @Override
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        if (providerInfo == null) {
            throw new NullPointerException("AppLife ProviderInfo cannot be null.");
        }
        if (AutoInit.class.getCanonicalName().equals(providerInfo.authority)) {
            throw new IllegalStateException("Incorrect provider authority : set applicationId in application\'s build.gradle.");
        }
        super.attachInfo(context, providerInfo);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
