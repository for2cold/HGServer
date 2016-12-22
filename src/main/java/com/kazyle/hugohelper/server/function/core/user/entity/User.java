package com.kazyle.hugohelper.server.function.core.user.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kazyle on 2016/8/23.
 */
public class User implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String realname;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
