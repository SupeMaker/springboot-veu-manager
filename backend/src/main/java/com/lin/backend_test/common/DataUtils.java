package com.lin.backend_test.common;

public class DataUtils {
    public static boolean isTimeFormatInvalid(String time) {
        if (time == null) {
            return true;
        }
        return !time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
    }

    public static String formatString(String s) {
        if ("\"\"".equals(s)) return null;
        if (s.length() > 2) return s.substring(1, s.length()-1);
        return s;
    }
}
