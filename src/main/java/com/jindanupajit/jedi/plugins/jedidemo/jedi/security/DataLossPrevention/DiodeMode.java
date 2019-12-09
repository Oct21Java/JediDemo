package com.jindanupajit.jedi.plugins.jedidemo.jedi.security.DataLossPrevention;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public enum DiodeMode {
    UNKNOWN,
    DENY_ALL,
    WRITE,
    WRITE_ONCE,
    WRITE_ENCODED,
    READ,
    READ_ENCODED;

    private final String label;

    private DiodeMode() {
        this.label = this.toString();
    }

    private DiodeMode(String label) {
        this.label = label;
    }


    public static boolean canWriteOnce(Method method) {

        if (!method.isAnnotationPresent(DataLossPrevention.class))
            return true;

        DataLossPrevention dlp = method.getAnnotation(DataLossPrevention.class);

        return canWriteOnce(dlp);
    }

    public static boolean canWrite(Method method) {

        if (!method.isAnnotationPresent(DataLossPrevention.class))
            return true;

        DataLossPrevention dlp = method.getAnnotation(DataLossPrevention.class);

        return canWrite(dlp);
    }
    public static boolean canRead(Method method) {

        if (!method.isAnnotationPresent(DataLossPrevention.class))
            return true;

        DataLossPrevention dlp = method.getAnnotation(DataLossPrevention.class);

        return canRead(dlp);
    }
    public static boolean canWriteOnce(Field field) {
        if (!field.isAnnotationPresent(DataLossPrevention.class))
            return true;

        DataLossPrevention dlp = field.getAnnotation(DataLossPrevention.class);

        return canWriteOnce(dlp);
    }

    public static boolean canWrite(Field field) {
        if (!field.isAnnotationPresent(DataLossPrevention.class))
            return true;

        DataLossPrevention dlp = field.getAnnotation(DataLossPrevention.class);

        return canWrite(dlp);
    }
    public static boolean canRead(Field field) {
        if (!field.isAnnotationPresent(DataLossPrevention.class))
            return true;

        DataLossPrevention dlp = field.getAnnotation(DataLossPrevention.class);
        System.out.printf("DLP %s\n", field.getName());
        return canRead(dlp);
    }
    public static boolean canWriteOnce(DataLossPrevention dlp) {
        return (dlp.diode().label.equals(DiodeMode.WRITE_ONCE.label)
                || canWrite(dlp));
    }

    public static boolean canWrite(DataLossPrevention dlp) {
        return (dlp.diode().label.equals(DiodeMode.WRITE.label)
                || dlp.diode().label.equals(DiodeMode.WRITE_ENCODED.label));
    }

    public static boolean canRead(DataLossPrevention dlp) {
        return (dlp.diode().label.equals(DiodeMode.READ.label)
                || dlp.diode().label.equals(DiodeMode.READ_ENCODED.label));
    }
}
