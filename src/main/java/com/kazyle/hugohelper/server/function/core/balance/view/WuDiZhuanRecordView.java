package com.kazyle.hugohelper.server.function.core.balance.view;

import com.alibaba.fastjson.annotation.JSONField;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * <p>
 * <b>WuDiZhuanRecordView</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/7
 */
public class WuDiZhuanRecordView {

    private String tixian_time;

    @JSONField(format = "yyyy-MM-dd")
    private Date tixianDate;

    private String remark;

    private String note;

    private String description;

    public String getTixian_time() {
        return tixian_time;
    }

    public void setTixian_time(String tixian_time) {
        this.tixian_time = tixian_time;
    }

    public Date getTixianDate() {
        if (tixianDate == null) {
            try {
                Long time = Long.parseLong(getTixian_time());
                time = time * 1000;
                DateTime dateTime = new DateTime(time);
                return dateTime.toDate();
            } catch (NumberFormatException e) {
            }
        }
        return tixianDate;
    }

    public void setTixianDate(Date tixianDate) {
        this.tixianDate = tixianDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
