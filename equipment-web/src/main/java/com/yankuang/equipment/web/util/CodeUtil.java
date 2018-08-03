package com.yankuang.equipment.web.util;

import java.util.UUID;

public class CodeUtil {

    public static String getCode(){
        String code = UUID.randomUUID().
                toString().replace("-", "");
        return code;
    }
}
