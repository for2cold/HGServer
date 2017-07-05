package com.kazyle.hugohelper.server.function.core.balance.view;

/**
 * <p>
 * <b>SuperAiZhuanView</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/7/5
 */
public class SuperAiZhuanView {

    private String uid;

    private String balance;

    private String today_income;

    private int status;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getToday_income() {
        return today_income;
    }

    public void setToday_income(String today_income) {
        this.today_income = today_income;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
