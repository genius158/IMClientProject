package com.yan.imclientproject.ui.login.bean;


/**
 * Created by Administrator on 2016/7/21.
 */
public class Account {

    private String username;
    private String password;

    public String getUsername() {

        return username;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
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
}


