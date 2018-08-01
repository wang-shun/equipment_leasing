package com.yankuang.equipment.common.util;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouy on 2018/7/27.
 */
public class Utils {

    public static boolean isNull(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNullObject(Object obj) {
        if (obj == null || isNull(obj.toString())) {
            return true;
        } else if (obj instanceof List && ((List)obj).size() <= 0) {
            return true;
        } else if (obj instanceof Map && ((Map)obj).size() <= 0) {
            return true;
        } else {
            return false;
        }
    }

}
