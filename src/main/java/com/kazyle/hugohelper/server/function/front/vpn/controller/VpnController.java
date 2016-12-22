package com.kazyle.hugohelper.server.function.front.vpn.controller;

import com.kazyle.hugohelper.server.config.annotation.CurrentUser;
import com.kazyle.hugohelper.server.config.domain.data.BaseFrontController;
import com.kazyle.hugohelper.server.config.domain.data.Constants;
import com.kazyle.hugohelper.server.config.domain.data.Page;
import com.kazyle.hugohelper.server.config.domain.data.PageRequest;
import com.kazyle.hugohelper.server.config.exception.FileException;
import com.kazyle.hugohelper.server.function.core.script.entity.Script;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import com.kazyle.hugohelper.server.function.core.vpn.entity.Vpn;
import com.kazyle.hugohelper.server.function.core.vpn.service.VpnService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Kazyle on 2016/9/18.
 */
@Controller
@RequestMapping("/front/vpn")
public class VpnController extends BaseFrontController<Vpn> {

    @Resource
    private VpnService vpnService;

    @RequestMapping("/index")
    public String index(@CurrentUser User user, PageRequest pageable, Model model) {
        Page<Vpn> page = vpnService.queryList(user.getId(), pageable);
        model.addAttribute("page", page);
        return viewName("index");
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return viewName("add");
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@CurrentUser User user, Vpn pojo) {
        vpnService.save(user, pojo);
        return redirectToUrl("/front/vpn/index");
    }
}
