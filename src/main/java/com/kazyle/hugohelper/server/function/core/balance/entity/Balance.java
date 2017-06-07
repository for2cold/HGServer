package com.kazyle.hugohelper.server.function.core.balance.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <b>Balance</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/3
 */
public class Balance implements Serializable {

    private Long id;

    private String username;

    private String platform;

    private Integer type;

    private String amount;

    private String today;

    @JSONField(format = "yyyy-MM-dd")
    private Date date;

    private Long userId;

    private String params;

    private String withdraw;

    private String block;

    private String articleActive;

    private int artcleStatus;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public String getArticleActive() {
        return articleActive;
    }

    public void setArticleActive(String articleActive) {
        this.articleActive = articleActive;
    }

    public int getArtcleStatus() {
        return artcleStatus;
    }

    public void setArtcleStatus(int artcleStatus) {
        this.artcleStatus = artcleStatus;
    }
}
