package com.kazyle.hugohelper.server.function.core.account.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>HGServer</p>
 * <p>
 * <b>AccountSheet</b> is
 * </p>
 *
 * @version 1.0
 * @since 1.0
 * Created by Kazyle on 2016/12/14 10:21
 */
public class AccountSheet implements Serializable {

    private Long id;            // ID

    private String title;       // 标题

    private Long userId;        // 所属用户

    private Date createDate;    // 创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
