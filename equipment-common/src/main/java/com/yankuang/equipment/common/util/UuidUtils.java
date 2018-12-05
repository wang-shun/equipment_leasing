package com.yankuang.equipment.common.util;

import java.util.UUID;

/**
 * Created by zhouy on 2018/8/1.
 */
public class UuidUtils {

    public static String newUuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}
