package com.app.dianti.refactor.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * Created by Mycroft_Wong on 2015/12/30.
 */
public final class Toasts {

    private Toasts() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 可能用于调试，但是默认情况下都是开的
    private static boolean sIsShown = true;

    public static void show(@NonNull Context context, String msg) {
        if (sIsShown) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void show(@NonNull Context context, int resId) {
        if (sIsShown) {
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLong(@NonNull Context context, String msg) {
        if (sIsShown) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static void showLong(@NonNull Context context, int resId) {
        if (sIsShown) {
            Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
        }
    }
}
