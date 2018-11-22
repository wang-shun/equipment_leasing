package com.yankuang.equipment.common.util;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouy on 2018/7/27.
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNullObject(Object obj) {
        if (obj == null || isEmpty(obj.toString())) {
            return true;
        } else if (obj instanceof List && ((List)obj).size() <= 0) {
            return true;
        } else if (obj instanceof Map && ((Map)obj).size() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String getRandomData () {
        return (int)(Math.random()*900)+100 + System.currentTimeMillis() + "";
    }
}
