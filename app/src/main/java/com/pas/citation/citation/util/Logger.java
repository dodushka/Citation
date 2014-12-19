package com.pas.citation.citation.util;

import com.pas.citation.citation.BuildConfig;

/**
 * Created by Paskal on 18.12.2014.
 */
public class Logger {

    private static boolean isLogEnable() {
        return BuildConfig.DEBUG;
    }

    public static void i(String tag, String string) {
        if (isLogEnable()) {
            android.util.Log.i(tag, string);
        }
    }
    public static void e(String tag, String string) {
        android.util.Log.e(tag, string);
    }
    public static void e(String tag, String string,Exception exp) {
        android.util.Log.e(tag, string,exp);
    }
    public static void d(String tag, String string) {
        if (isLogEnable()) {
            android.util.Log.d(tag, string);
        }
    }
    public static void v(String tag, String string) {
        if (isLogEnable()) {
            android.util.Log.v(tag, string);
        }
    }
    public static void w(String tag, String string) {
        android.util.Log.w(tag, string);
    }

}
