package com.teachonmars.module.autoContext.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface NeedContext {
    String TAG = NeedContext.class.getCanonicalName();

    /**
     * @return Priority used to call the method which need context, from 1 (priority maximum) to {@link Integer#MAX_VALUE} (priority minimum before 0);
     * 0 is used as default and with less priority (call after all other priority)
     */
    int priority() default 0;
}