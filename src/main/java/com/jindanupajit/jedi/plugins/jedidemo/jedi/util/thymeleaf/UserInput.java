package com.jindanupajit.jedi.plugins.jedidemo.jedi.util.thymeleaf;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInput {
    int Ordinal() default Integer.MAX_VALUE;
    String Label();
    String PlaceHolder() default "";
    boolean Secret() default false;
}
