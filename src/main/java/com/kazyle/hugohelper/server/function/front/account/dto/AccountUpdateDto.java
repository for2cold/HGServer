package com.kazyle.hugohelper.server.function.front.account.dto;

import com.kazyle.hugohelper.server.function.core.account.entity.enums.AccountStatus;
import com.kazyle.hugohelper.server.function.core.account.entity.enums.TaskType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>HGServer</p>
 * <p>
 * <b>AccountUpdateDto</b> is
 * </p>
 *
 * @version 1.0
 * @since 1.0
 * Created by Kazyle on 2016/12/19 13:50
 */
public class AccountUpdateDto implements Serializable {

    private Long id;

    private String mobile;

    private BigDecimal balance;

    private BigDecimal withdrawBalance;

    private String phone;

    private String alipay;

    private TaskType taskType;

    private AccountStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date withdrawDate;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getWithdrawBalance() {
        return withdrawBalance;
    }

    public void setWithdrawBalance(BigDecimal withdrawBalance) {
        this.withdrawBalance = withdrawBalance;
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

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getWithdrawDate() {
        return withdrawDate;
    }

    public void setWithdrawDate(Date withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
