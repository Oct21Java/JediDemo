package com.jindanupajit.jedi.plugins.jedidemo.jedi.util.thymeleaf;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionMappings {
    ActionMapping[] value();
}
