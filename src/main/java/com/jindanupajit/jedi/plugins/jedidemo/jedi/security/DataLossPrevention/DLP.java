package com.jindanupajit.jedi.plugins.jedidemo.jedi.security.DataLossPrevention;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DLP {

    static DataLossPrevention DEFAULT = DLP.getAnnotation(DiodeMode.DENY_ALL, BCryptPasswordEncoder.class);
    static DataLossPrevention UNDEFINED = DLP.getAnnotation(DiodeMode.UNKNOWN, null);

    public static DataLossPrevention getAnnotation(final DiodeMode diodeMode, final Class<? extends PasswordEncoder> encoder) {
        return new DataLossPrevention()
        {
            @Override
            public DiodeMode diode() {
                return diodeMode;
            }

            @Override
            public Class<? extends PasswordEncoder> encoder() {
                return encoder;
            }

            @Override
            public Class<? extends Annotation> annotationType()
            {
                return DataLossPrevention.class;
            }
        };

    }

    public static DataLossPrevention getDataLossPrevention(Method method) {
        if (method == null) return DLP.UNDEFINED;
        if (!method.isAnnotationPresent(DataLossPrevention.class))
            return DLP.UNDEFINED;

        return method.getAnnotation(DataLossPrevention.class);
    }

    public static DataLossPrevention getDataLossPrevention(Field field) {
        if (field == null) return DLP.UNDEFINED;
        if (!field.isAnnotationPresent(DataLossPrevention.class))
            return DLP.UNDEFINED;

        return field.getAnnotation(DataLossPrevention.class);
    }

    public static DiodeMode getDiode(Method method) {

        return getDataLossPrevention(method).diode();
    }

    public static DiodeMode getDiode(Field field) {
        return getDataLossPrevention(field).diode();
    }

    public static Class<? extends PasswordEncoder> getEncoder(Method method) {

        return getDataLossPrevention(method).encoder();
    }

    public static Class<? extends PasswordEncoder> getEncoder(Field field) {
        return getDataLossPrevention(field).encoder();
    }

    public static Class<? extends PasswordEncoder> getEncoder(Method getter, Field field, Method setter) {
        if (getEncoder(getter) != null)
            return getEncoder(getter);
        else if (getEncoder(field) != null)
            return getEncoder(field);
        else
            return getEncoder(setter);
    }

}
