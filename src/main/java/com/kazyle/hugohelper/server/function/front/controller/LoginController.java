package com.kazyle.hugohelper.server.function.front.controller;

import com.kazyle.hugohelper.server.config.domain.data.Constants;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import com.kazyle.hugohelper.server.function.core.user.exception.LoginException;
import com.kazyle.hugohelper.server.function.core.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Kazyle on 2016/8/23.
 */
@Controller
@RequestMapping("")
public class LoginController {

    @Resource
    private UserService userService;

    @RequestMapping({"", "/"})
    public String main() {
        return "redirect:/front/script/index";
    }

    @RequestMapping(value = {"/{login:login;?.*}"}, method =  RequestMethod.GET)
    public String login(HttpSession session) {
        session.removeAttribute(Constants.CURRENT_USER);
        return "front/login";
    }

    @RequestMapping(value = {"/{login:login;?.*}"}, method =  RequestMethod.POST)
    public String login(String username, String password, HttpSession session, Model model) {
        model.addAttribute("username", username);
        if (StringUtils.isEmpty(username)) {
            model.addAttribute(Constants.ERROR_MSG, "请输入登录账号");
            return "front/login";
        }
        if (StringUtils.isEmpty(password)) {
            model.addAttribute(Constants.ERROR_MSG, "请输入登录密码");
            return "front/login";
        }
        try {
            User user = userService.login(username, password);
            session.setAttribute(Constants.CURRENT_USER, user);
        } catch (LoginException e) {
            model.addAttribute(Constants.ERROR_MSG, "账号或密码错误");
            return "front/login";
        } catch (Exception e) {
            model.addAttribute(Constants.ERROR_MSG, "登录失败，请稍后再试");
            return "front/login";
        }
        return "redirect:/front/phonedata/index";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(Constants.CURRENT_USER);
        return login(session);
    }
}
