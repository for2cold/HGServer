package com.kazyle.hugohelper.server.function.front.account.result;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>HGServer</p>
 * <p>
 * <b>WithdrawResult</b> is
 * </p>
 *
 * @version 1.0
 * @since 1.0
 * Created by Kazyle on 2016/12/23 16:54
 */
public class WithdrawResult implements Serializable {

    private String period;

    private BigDecimal amount;

    private BigDecimal averageAmount;

    private int days;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAverageAmount() {
        return averageAmount;
    }

    public void setAverageAmount(BigDecimal averageAmount) {
        this.averageAmount = averageAmount;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
