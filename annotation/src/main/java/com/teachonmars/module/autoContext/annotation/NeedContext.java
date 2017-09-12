package com.teachonmars.module.autoContext.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface NeedContext {
    String TAG = NeedContext.class.getCanonicalName();

    int priority() default Integer.MAX_VALUE - 10;
}