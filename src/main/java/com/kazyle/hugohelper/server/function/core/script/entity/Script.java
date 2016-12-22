package com.kazyle.hugohelper.server.function.core.script.entity;

import com.kazyle.hugohelper.server.function.core.script.entity.enums.ScriptStatus;
import com.kazyle.hugohelper.server.function.core.script.entity.enums.ScriptType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kazyle on 2016/8/23.
 */
public class Script implements Serializable {

    private Long id;

    private String name;

    private String key;

    private Long userId;

    private String path;

    private Float version;

    private ScriptType type;

    private ScriptStatus status;

    private String remark;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public Float getVersion() {
        return version;
    }

    public void setVersion(Float version) {
        this.version = version;
    }

    public ScriptType getType() {
        return type;
    }

    public void setType(ScriptType type) {
        this.type = type;
    }

    public ScriptStatus getStatus() {
        return status;
    }

    public void setStatus(ScriptStatus status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
