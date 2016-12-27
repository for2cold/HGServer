package com.kazyle.hugohelper.server.function.front.account.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>HGServer</p>
 * <p>
 * <b>AccountReportsDto</b> is
 * </p>
 *
 * @version 1.0
 * @since 1.0
 * Created by Kazyle on 2016/12/27 16:01
 */
public class AccountReportsDto implements Serializable {

    @DateTimeFormat(pattern = "yyyy-MM")
    private Date period;

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }
}
