package com.kazyle.hugohelper.server.config.domain.data;

import com.kazyle.hugohelper.server.config.helper.JSONHelper;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by Kazyle on 2016/8/23.
 */
public abstract class BaseController<T extends Serializable> {

    @Resource
    protected JSONHelper jsonHelper;

    protected String responseCallback(ResponseCode code, String msg, Object... obj) {

        ResponseEntity entity = new ResponseEntity(code.getValue(), msg);
        if (obj != null && obj.length > 0) {
            entity.setObj(obj[0]);
        }
        return jsonHelper.toJSONString(entity);
    }
}
