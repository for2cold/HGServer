package com.kazyle.hugohelper.server.function.api.controller;

import com.kazyle.hugohelper.server.config.domain.data.BaseController;
import com.kazyle.hugohelper.server.config.domain.data.ResponseCode;
import com.kazyle.hugohelper.server.config.domain.data.ResponseEntity;
import com.kazyle.hugohelper.server.function.api.view.RequestScriptView;
import com.kazyle.hugohelper.server.function.core.script.entity.Script;
import com.kazyle.hugohelper.server.function.core.script.service.ScriptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Kazyle on 2016/8/24.
 */
@RestController
@RequestMapping("/api/script")
public class ScriptApiController extends BaseController<Script> {

    @Resource
    private ScriptService scriptService;

    @RequestMapping(value = "upgrade", method = RequestMethod.GET)
    public ResponseEntity upgrade(RequestScriptView view) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "更新脚本成功");
        List<Script> list = scriptService.queryUpgrade(view.getUserId(), view.getScriptIds());
        entity.setObj(list);
        return entity;
    }
}
