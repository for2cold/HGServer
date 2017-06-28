package com.kazyle.hugohelper.server.function.core.balance.view;

import java.util.Map;

/**
 * <p>
 * <b>ZhaoCaiTuItemView</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/24
 */
public class ZhaoCaiTuItemView {

    private String phone;

    private double residue_money;

    private double sum_money;

    private int state;

    private Map<String, Double> today_info;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getResidue_money() {
        return residue_money;
    }

    public void setResidue_money(double residue_money) {
        this.residue_money = residue_money;
    }

    public double getSum_money() {
        return sum_money;
    }

    public void setSum_money(double sum_money) {
        this.sum_money = sum_money;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Map<String, Double> getToday_info() {
        return today_info;
    }

    public void setToday_info(Map<String, Double> today_info) {
        this.today_info = today_info;
    }
}
