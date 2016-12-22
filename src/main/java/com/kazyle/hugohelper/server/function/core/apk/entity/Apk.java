package com.kazyle.hugohelper.server.function.core.apk.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kazyle on 2016/9/8.
 */
public class Apk implements Serializable {

    private Long id;

    private String name;

    private Long userId;

    private String path;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
