package com.yankuang.equipment.common.util;
/**
 *
 * 项目名称：yankuang_equipment
 * 类名称：Constants
 * 类描述：常量
 * 创建人： liyinpeng
 * 创建时间：2018-8-3 下午04:14:13
 * 修改人： liyinpeng
 * 修改时间：2018-8-3 下午04:14:13
 * 修改备注：
 * @version
 * <p>Copyright: Copyright (c) 2018 Company: KCX</p>
 *
 */
public class Constants {

    /**
     * 响应成功时返回的code
     */
    public static final String RESPONSE_SUCCESS = "200";

    /**
     * 响应失败时返回的code
     */
    public static final String RESPONSE_ERROR = "400";

    /**
     * 响应异常时返回的code
     */
    public static final String RESPONSE_EXCEPTION = "500";

    /**
     * 密码过期需要修改密码时的响应code
     */
    public static final String RESPONSE_PASSWORD_EXPIRED = "201";

    /**
     * 用户被迫离线的响应code(一般是由于其他人使用同一账号登录导致)
     */
    public static final String RESPONSE_FORCED_OFFLINE = "202";

    /**
     * 用户登录超时
     */
    public static final String RESPONSE_OFFLINE = "203";

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * 默认一页显示记录数量
     */
    public static final Integer PAGE_SIZE = 30;

    /**
     * 设备租赁计划状态_未提交
     */
    public static final String PLANSTATUS_UNCOMMITED = "1";
    public static final String PLANSTATUS_UNCOMMITED_VALUE = "未提交";

    /**
     * 设备租赁计划状态_已提交未审核
     */
    public static final String PLANSTATUS_COMMITED = "2";
    public static final String PLANSTATUS_COMMITED_VALUE = "已提交";

    /**
     * 设备租赁计划状态_已审核未通过
     */
    public static final String PLANSTATUS_FAILED = "3";
    public static final String PLANSTATUS_FAILED_VALUE = "已审核未通过";

    /**
     * 设备租赁计划状态_已审核已通过
     */

    public static final String PLANSTATUS_PASSED = "4";
    public static final String PLANSTATUS_PASSED_VALUE = "已审核已通过";
    /**
     * 设备租赁计划状态_其他
     */
    public static final String PLANSTATUS_OTHERS = "5";
    public static final String PLANSTATUS_OTHERS_VALUE = "服务处理中";

    /**
     * 设备租赁计划设备类型_通用设备
     */

    public static final String PLANEQUIPMENTTYPE_GENERIC = "1";
    public static final String PLANEQUIPMENTTYPE_GENERIC_VALUE = "generic";

    /**
     * 设备租赁计划设备类型_综机设备
     */
    public static final String PLANEQUIPMENTTYPE_INTEGRATED = "2";
    public static final String PLANEQUIPMENTTYPE_INTEGRATED_VALUE = "integrated";

    /**
     * 设备租赁计划设备类型_其他设备
     */
    public static final String PLANEQUIPMENTTYPE_OTHERS = "3";
    public static final String PLANEQUIPMENTTYPE_OTHERS_VALUE = "otherEquipment";

    /**
     * 设备租赁计划_计划类型_年度
     */
    public static final String PLANTYPE_YEAR = "1";
    public static final String PLANTYPE_YEAR_VALUE = "year";

    /**
     * 设备租赁计划_计划类型_季度
     */
    public static final String PLANTYPE_QUARTER = "2";
    public static final String PLANTYPE_QUARTER_VALUE = "quarter";

    /**
     * 设备租赁计划_计划类型_月度
     */
    public static final String PLANTYPE_MONTH = "3";
    public static final String PLANTYPE_MONTH_VALUE = "month";

    /**
     * 设备租赁计划_计划类型_应急
     */
    public static final String PLANTYPE_URGENT = "4";
    public static final String PLANTYPE_URGENT_VALUE = "urgent";
}
