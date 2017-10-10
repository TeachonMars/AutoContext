package com.teachonmars.modules.autoContext;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.teachonmars.modules.autoContext.annotation.Constant;

public class AutoContext extends ContentProvider {
    private static final String TAG = AutoContext.class.getSimpleName();

    @Override
    public boolean onCreate() {
        try {
            Class<?> contextNeedy = Class.forName(Constant.builtClassName);
            contextNeedy.getMethod(Constant.builtClassMain, Context.class).invoke(null, getContext().getApplicationContext());
        } catch (Exception e) {
            Log.e(TAG, getContext().getString(R.string.autoContext_error_generatedCode), e);
        }
        return true;
    }

    @Override
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        if (providerInfo == null) {
            throw new NullPointerException(context.getString(R.string.autoContext_error_NoProvider));
        }
        if (AutoContext.class.getCanonicalName().equals(providerInfo.authority)) {
            throw new IllegalStateException(context.getString(R.string.autoContext_error_renameAuthority));
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
