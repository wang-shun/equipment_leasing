package com.yankuang.equipment.excel.service;

import java.lang.annotation.*;

/**
 * @author xhh
 * @date 2018/8/15
 *  功能：excel模板设置
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelAnnotation {

    //Excel列ID(Excel列排序序号)
    int id();
    //Excel列名
    String[] name();
    //Excel列宽
    int width() default 5000;
}
