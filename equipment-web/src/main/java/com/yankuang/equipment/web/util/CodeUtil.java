package com.yankuang.equipment.web.util;

import com.yankuang.equipment.common.util.StringUtils;

import java.util.UUID;

public class CodeUtil {

    public static String getCode(){
        String code = UUID.randomUUID().
                toString().replace("-", "");
        return code;
    }
    /**
     * 获取固定长度的字符串，长度不够在前面补充0
     *
     * @param str 字符串
     * @param length code长度
     * @return
     */
    public static String getFixedLengthCode(String str, int length) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        if (str.length() < length) {
            for (int i = 0; i < length; i++) {
                str = "0" + str;
                if (str.length() == length) {
                    break;
                }
            }
        }
        return str;
    }




}
