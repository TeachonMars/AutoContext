package com.teachonmars.modules.autoContext.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface NeedContext {
    String TAG = NeedContext.class.getCanonicalName();

    /**
     * Annotation used on methods to indicate which need to be called statically with Application Context as parameter
     *
     * @return Priority used to call the method which need context, from 1 (priority maximum) to {@link Integer#MAX_VALUE} (priority minimum before 0);
     * 0 is used as default and with less priority (call after all other priority)
     */
    int priority() default 0;
}