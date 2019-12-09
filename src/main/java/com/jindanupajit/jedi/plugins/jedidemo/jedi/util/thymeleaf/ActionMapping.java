package com.jindanupajit.jedi.plugins.jedidemo.jedi.util.thymeleaf;

import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ActionMappings.class)
public @interface ActionMapping {
    ActionType[] Action();
    String Url() default "#";
    RequestMethod Method() default RequestMethod.GET;
    String Label() default "Submit";
}
