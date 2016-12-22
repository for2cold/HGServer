package com.kazyle.hugohelper.server.function.front.user.controller;

import com.kazyle.hugohelper.server.config.annotation.CurrentUser;
import com.kazyle.hugohelper.server.config.domain.data.*;
import com.kazyle.hugohelper.server.function.core.script.entity.Script;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import com.kazyle.hugohelper.server.function.core.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Created by Kazyle on 2016/8/24.
 */
@Controller
@RequestMapping("/front/user")
public class UserController extends BaseFrontController<User> {

    @Resource
    private UserService userService;

    @RequestMapping({"", "/", "index"})
    public String index(PageRequest pageRequest, Model model) {

        Page<User> page = userService.queryList(pageRequest);
        model.addAttribute("page", page);
        return viewName("index");
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add() {
        return viewName("add");
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@CurrentUser User user, User pojo) {
        userService.save(user, pojo);
        return redirectToUrl("/front/user/index");
    }

    @RequestMapping("{id}/remove")
    @ResponseBody
    public ResponseEntity remove(@PathVariable("id") Long id) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "删除成功！");
        userService.remove(id);
        return entity;
    }
}
