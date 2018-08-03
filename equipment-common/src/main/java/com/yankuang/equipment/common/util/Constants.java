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
    public static final Integer PAGE_SIZE = 20;

}
