package com.yankuang.equipment.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 项目名称：yankuang-equipment
 * 类名称：DateUtil
 * 类描述：日期工具类
 * 创建人： liyp
 * 创建时间：2018-8-24 10:18:20
 * 修改人： liyp
 * 修改时间：2018-8-24 10:18:20
 * 修改备注：
 * @version V1.0
 * <p>Copyright: Copyright (c) 2018 Company: qdits</p>
 *
 */
public class DateUtils {

    public final static String DATE_FORMAT = "yyyy-MM-dd";

    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String TIME_FORMAT = "HH:mm:ss";

    /**
     * @category 当前时间（毫秒）
     */
    public static long current() {
        return System.currentTimeMillis();
    }

    /**
     *@category  格式化日期
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     *@category  格式化日期
     */
    public static String formatDateTime(String date) {
        String sdate = date;

        if (date == null) {
            sdate = "";
        } else if (date.equals("0000-00-00 00:00:00")) {
            sdate = "";
        } else {
            int pos = date.lastIndexOf(".");
            if (pos > -1) {
                sdate = date.substring(0, pos);
            }
        }

        return sdate;
    }

    /**
     *@category  格式化日期
     */
    public static String formatDateTime(Date date) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_TIME_FORMAT);
        if (date == null) {
            return "";
        } else {
            return sdf.format(date);
        }
    }

    /**
     *@category  格式化当前时间
     */
    public static String formatNow() {
        java.text.DateFormat sdf = new java.text.SimpleDateFormat(DATE_TIME_FORMAT);
        return sdf.format(new Date());
    }

    /**
     * 获取当前年份
     * @return
     */
    public static String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

}
