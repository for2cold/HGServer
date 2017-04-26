package com.kazyle.hugohelper.server.function.core.article.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>HGServer</p>
 * <p>
 * <b>ArticlePlatform</b> is
 * </p>
 *
 * @version 4.0
 * @since 4.0
 * Created by Kazyle on 2017/4/26.
 */
public class ArticlePlatform implements Serializable {

    private Long id;

    private String name;

    private Long userId;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
