package com.kazyle.hugohelper.server.function.front.balance.form;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * <b>QueryForm</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/5
 */
public class QueryForm {

    private String platform;
    private Integer type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
