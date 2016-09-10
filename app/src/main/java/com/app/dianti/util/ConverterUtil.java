package com.app.dianti.util;

/**
 * Created by Mycroft_Wong on 2015/12/30.
 */
public final class ConverterUtil {

    public static int convert2Int(Object value, int defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }
}
