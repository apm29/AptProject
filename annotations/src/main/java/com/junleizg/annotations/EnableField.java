package com.junleizg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableField {

    String NO_NO_NO = "NO_VALUE";
    String value() default NO_NO_NO;//字段名称
    int group() default 0;
}