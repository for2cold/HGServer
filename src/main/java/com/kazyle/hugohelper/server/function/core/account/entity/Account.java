package com.kazyle.hugohelper.server.function.core.account.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.kazyle.hugohelper.server.function.core.account.entity.enums.AccountStatus;
import com.kazyle.hugohelper.server.function.core.account.entity.enums.TaskType;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Kazyle on 2016/8/23.
 */
public class Account implements Serializable {

    private Long id;                // 账号ID

    private Long sheetId;           // 账目ID

    private Long userId;            // 用户ID

    private String mobile;          // 手机号码

    private String alipay;         // 所属支付宝

    private String phone;           // 所属机器

    private BigDecimal amount;      // 总金额

    private BigDecimal balance;     // 余额

    private BigDecimal withdrawBalance; // 已提现金额

    private AccountStatus status;   // 账号状态

    private TaskType taskType;      // 任务类型

    private Date deadline;          // 完成时间

    private Date withdrawDate;      // 可提现日期

    private Date createDate;        // 账号创建时间

    private String remark;          // 备注

    private boolean deleted = Boolean.FALSE;        // 是否已删除

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSheetId() {
        return sheetId;
    }

    public void setSheetId(Long sheetId) {
        this.sheetId = sheetId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @JSONField(name = "withdraw_balance")
    public BigDecimal getWithdrawBalance() {
        return withdrawBalance;
    }

    public void setWithdrawBalance(BigDecimal withdrawBalance) {
        this.withdrawBalance = withdrawBalance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    @JSONField(format = "yyyy-MM-dd")
    public Date getDeadline() {
        return deadline;
    }

    public String getDeadlineText() {
        if (getDeadline() == null) {
            return null;
        }
        DateTime dateTime = new DateTime(getDeadline());
        return dateTime.toString("yyyy-MM-dd");
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @JSONField(format = "yyyy-MM-dd", name = "withdraw_date")
    public Date getWithdrawDate() {
        return withdrawDate;
    }

    public String getWithdrawDateText() {
        if (getWithdrawDate() == null) {
            return null;
        }
        DateTime dateTime = new DateTime(getWithdrawDate());
        return dateTime.toString("yyyy-MM-dd");
    }

    public void setWithdrawDate(Date withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
