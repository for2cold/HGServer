package com.kazyle.hugohelper.server.function.core.vpn.entity;

import com.kazyle.hugohelper.server.function.core.vpn.entity.enums.VpnStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kazyle on 2016/9/18.
 */
public class Vpn implements Serializable {

    private Long id;

    private Long userId;

    private String name;

    private String host;

    private String username;

    private String password;

    private VpnStatus status;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public VpnStatus getStatus() {
        return status;
    }

    public void setStatus(VpnStatus status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
