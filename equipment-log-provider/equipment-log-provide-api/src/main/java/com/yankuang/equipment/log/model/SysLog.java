package com.yankuang.equipment.log.model;


import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author xhh
 * @date 2018/8/9 18:06
 */
@Table(name = "el_sys_log")
public class SysLog implements Serializable {

    private int id;//id

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

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getCreate_date() {
        return create_date;
    }
    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRole_name() {
        return role_name;
    }
    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
    public String getIp_address() {
        return ip_address;
    }
    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getReturned_content() {
        return returned_content;
    }
    public void setReturned_content(String returned_content) {
        this.returned_content = returned_content;
    }

    public String getException_name() {
        return exception_name;
    }

    public void setException_name(String exception_name) {
        this.exception_name = exception_name;
    }

    public String getException_msg() {
        return exception_msg;
    }

    public void setException_msg(String exception_msg) {
        this.exception_msg = exception_msg;
    }
}
