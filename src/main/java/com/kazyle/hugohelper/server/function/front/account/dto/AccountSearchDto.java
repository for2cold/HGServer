package com.kazyle.hugohelper.server.function.front.account.dto;/**
 * Created by Kazyle on 2016/12/12.
 */

import com.kazyle.hugohelper.server.function.core.account.entity.enums.AccountStatus;
import com.kazyle.hugohelper.server.function.front.account.enums.SortType;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>HGServer</p>
 * <p>
 * <b>AccountSearchDto</b> is
 * </p>
 *
 * @version 1.0
 * @since 1.0
 * Created by Kazyle on 2016/12/12.
 */
public class AccountSearchDto implements Serializable {

    private Long userId;

    private String period;

    @DateTimeFormat(pattern = "yyyy-MM")
    private Date periodDate;

    private AccountStatus status;

    private String phone;

    private String alipay;

    private BigDecimal balance;

    private TreeMap<String, SortType> sortMap;

    private boolean deleted = Boolean.FALSE;

    public String getPeriod() {
        if (getPeriodDate() != null) {
            DateTime dateTime = new DateTime(getPeriodDate());
            period = dateTime.toString("yyyy-MM");
        }
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(Date periodDate) {
        this.periodDate = periodDate;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public TreeMap<String, SortType> getSortMap() {
        return sortMap;
    }

    public void setSortMap(TreeMap<String, SortType> sortMap) {
        this.sortMap = sortMap;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
