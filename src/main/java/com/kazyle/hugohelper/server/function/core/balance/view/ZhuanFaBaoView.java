package com.kazyle.hugohelper.server.function.core.balance.view;

/**
 * <p>
 * <b>ZhuanFaBaoView</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/29
 */
public class ZhuanFaBaoView {

    private int status;

    private String message;

    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static class ZhuanFaBaoLoginView {

        private String uid;

        private String token;

        private String mobile;

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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        @Override
        public String toString() {
            return "uid=" + uid + "&token=" + token;
        }
    }

    public static class ZhuanFaBaoInfoView {

        private String score;

        private String total_add_score;

        private String dayScore;

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getTotal_add_score() {
            return total_add_score;
        }

        public void setTotal_add_score(String total_add_score) {
            this.total_add_score = total_add_score;
        }

        public String getDayScore() {
            return dayScore;
        }

        public void setDayScore(String dayScore) {
            this.dayScore = dayScore;
        }
    }
}
