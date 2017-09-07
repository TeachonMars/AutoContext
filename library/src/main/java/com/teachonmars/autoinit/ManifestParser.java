package com.teachonmars.autoinit;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Set;

class ManifestParser {
    private static final String TAG = ManifestParser.class.getSimpleName();

    public static <T> ArrayList<Class<? extends T>> parse(Context context, Class<T> interfaceToFind) {
        ArrayList<String> classesList = findClassesInMetadata(context, interfaceToFind.getSimpleName());
        return validateAllFoundClasses(classesList, interfaceToFind);
    }

    private static ArrayList<String> findClassesInMetadata(Context context, String toFind) {
        ArrayList<String> metaDataKeys = new ArrayList<>();
        Bundle metaData = retrieveMetadataAppInfo(context);
        Set<String> allKeys = metaData.keySet();
        if (allKeys != null) {
            for (String metaDataKey : allKeys) {
                if (!TextUtils.isEmpty(metaDataKey) && TextUtils.equals(metaDataKey, toFind)) {
                    String className = metaData.getString(metaDataKey);
                    if (!TextUtils.isEmpty(className)) {
                        metaDataKeys.add(className);
                    }
                }
            }
        }
        return metaDataKeys;
    }

    private static <T> ArrayList<Class<? extends T>> validateAllFoundClasses(ArrayList<String> classeNameList, Class<T> interfaceToFind) {
        ArrayList<Class<? extends T>> result = new ArrayList<>();
        for (String candidateClassName : classeNameList) {
            Class<? extends T> validClass = validateClass(candidateClassName, interfaceToFind);
            if (validClass != null) {
                result.add(validClass);
            }
        }
        return result;
    }

    private static <T> Class<? extends T> validateClass(String candidateClassName, Class<T> interfaceToFind) {
        Class<?> candidateClass;
        try {
            candidateClass = Class.forName(candidateClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Can't find class " + candidateClassName, e);
        }
        if (interfaceToFind.isAssignableFrom(candidateClass)) {
            return (Class<? extends T>) candidateClass;
        }
        return null;
    }

    private static Bundle retrieveMetadataAppInfo(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                return appInfo.metaData;
            } else {
                Log.d(TAG, "No Metadata in package infos");
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, "Can't find package infos");
        }
        return new Bundle();
    }
}
