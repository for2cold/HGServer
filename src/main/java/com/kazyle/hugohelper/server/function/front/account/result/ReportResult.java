package com.kazyle.hugohelper.server.function.front.account.result;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>HGServer</p>
 * <p>
 * <b>ReportResult</b> is
 * </p>
 *
 * @version 1.0
 * @since 1.0
 * Created by Kazyle on 2016/12/27 16:03
 */
public class ReportResult {

    @JSONField(format = "yyyy-MM")
    private Date date;

    private BigDecimal amount;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
