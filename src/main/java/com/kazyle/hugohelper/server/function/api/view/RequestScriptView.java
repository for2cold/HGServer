package com.kazyle.hugohelper.server.function.api.view;

import java.util.List;

/**
 * Created by Kazyle on 2016/9/5.
 */
public class RequestScriptView {

    private Long userId;

    private List<Long> scriptIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getScriptIds() {
        return scriptIds;
    }

    public void setScriptIds(List<Long> scriptIds) {
        this.scriptIds = scriptIds;
    }
}
