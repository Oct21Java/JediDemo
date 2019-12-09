package com.jindanupajit.jedi.plugins.jedidemo.jedi.util;

public class Verbose {
    private static boolean verbose = true;

    public static boolean isVerbose() {
        return verbose;
    }

    public static void setVerbose(boolean verbose) {
        Verbose.verbose = verbose;
    }

    public static void printlnf(String format, Object... o) {
        if (!isVerbose()) return;
        String callerClass = Thread.currentThread().getStackTrace()[2].getClassName();
        try {
            Class<?> caller = Class.forName(callerClass);
            callerClass = caller.getSimpleName();
        } catch (ClassNotFoundException ignored) { }

        String callerMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
        int line = Thread.currentThread().getStackTrace()[2].getLineNumber();

        System.out.printf(" * %s::%s(%d): %s\n", callerClass, callerMethod, line, String.format(format, o));
    }
}
