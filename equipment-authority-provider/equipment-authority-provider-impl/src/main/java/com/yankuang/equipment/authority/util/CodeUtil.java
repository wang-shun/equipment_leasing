package com.yankuang.equipment.authority.util;

import java.util.UUID;

public class CodeUtil {

    private static String getCode(){
        String code = UUID.randomUUID().
                toString().replace("-", "");
        return code;
    }
}
