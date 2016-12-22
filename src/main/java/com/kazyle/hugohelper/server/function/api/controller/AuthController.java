package com.kazyle.hugohelper.server.function.api.controller;

import com.kazyle.hugohelper.server.config.domain.data.ResponseCode;
import com.kazyle.hugohelper.server.config.domain.data.ResponseEntity;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import com.kazyle.hugohelper.server.function.core.user.exception.LoginException;
import com.kazyle.hugohelper.server.function.core.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Kazyle on 2016/8/24.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "token", method = RequestMethod.POST)
    public ResponseEntity login(String username, String password) {

        ResponseEntity entity = new ResponseEntity();

        if (StringUtils.isEmpty(username)) {
            entity.setCode(ResponseCode.VALIDATE_ERROR.getValue());
            entity.setMsg("请输入登录账号");
            return entity;
        }
        if (StringUtils.isEmpty(username)) {
            entity.setCode(ResponseCode.VALIDATE_ERROR.getValue());
            entity.setMsg("请输入登录密码");
            return entity;
        }
        try {
            User user = userService.login(username, password);
            entity.setCode(ResponseCode.SUCCESS.getValue());
            entity.setMsg("登录成功!");
            entity.setObj(user);
        } catch (LoginException e) {
            e.printStackTrace();
            entity.setCode(ResponseCode.ERROR.getValue());
            entity.setMsg("账号或密码不正确");
        } catch (Exception e) {
            e.printStackTrace();
            entity.setCode(ResponseCode.ERROR.getValue());
            entity.setMsg("登录失败，请稍后再试");
        }
        return entity;
    }
}
