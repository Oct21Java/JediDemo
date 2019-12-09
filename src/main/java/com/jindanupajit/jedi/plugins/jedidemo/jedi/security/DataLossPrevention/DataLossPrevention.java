package com.jindanupajit.jedi.plugins.jedidemo.jedi.security.DataLossPrevention;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DataLossPrevention {
    DiodeMode diode() default DiodeMode.DENY_ALL;
    Class<? extends PasswordEncoder> encoder() default BCryptPasswordEncoder.class;
}
