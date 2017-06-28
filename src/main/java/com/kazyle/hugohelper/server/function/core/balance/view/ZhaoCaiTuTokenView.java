package com.kazyle.hugohelper.server.function.core.balance.view;

/**
 * <p>
 * <b>ZhaoCaiTuTokenView</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/28
 */
public class ZhaoCaiTuTokenView {

    private String uid;

    private String token;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "uid=" + uid + "&token=" + token;
    }
}
