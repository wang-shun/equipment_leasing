package com.yankuang.equipment.web.dto;

import java.io.Serializable;

public class UserIn implements Serializable {

    public String username;

    public String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserIn{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
