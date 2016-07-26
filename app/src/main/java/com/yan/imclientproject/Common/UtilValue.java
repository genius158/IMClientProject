package com.yan.imclientproject.Common;

/**
 * Created by Administrator on 2016/7/26.
 */
public class UtilValue {

    public static String getValue(CharSequence str, String defaultValue) {
        return (str == null || str.length() == 0) ? defaultValue : str.toString();
    }

    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0) ? true : false;
    }
}
