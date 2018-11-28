package com.yankuang.equipment.syslog.model;


import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author xhh
 * @date 2018/8/11 16:19
 */
@Data
public class SysLog implements Serializable {

    @Id
    private String _id;//id

    private int user_id;//用户id

    private String create_date;//新建时间

    private String operation;//操作

    private String content;//内容

    private String ip_address;//ip

    private String name;//用户名

    private String role_name;//角色

    private String returned_content;//返回值

    private String exception_name;//异常名

    private String exception_msg;//异常内容

}
