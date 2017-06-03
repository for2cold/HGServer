package com.kazyle.hugohelper.server.function.front.balance.controller;

import com.kazyle.hugohelper.server.config.annotation.CurrentUser;
import com.kazyle.hugohelper.server.config.domain.data.BaseFrontController;
import com.kazyle.hugohelper.server.config.domain.data.ResponseCode;
import com.kazyle.hugohelper.server.config.domain.data.ResponseEntity;
import com.kazyle.hugohelper.server.function.core.article.service.ArticleService;
import com.kazyle.hugohelper.server.function.core.balance.entity.Balance;
import com.kazyle.hugohelper.server.function.core.balance.service.BalanceService;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * <b>BalanceController</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/3
 */
@Controller
@RequestMapping("/front/balance")
public class BalanceController extends BaseFrontController<Balance> {

    @Resource
    private BalanceService balanceService;

    @Resource
    private ArticleService articleService;

    @RequestMapping("/index")
    public String index(@CurrentUser User user, String platform, Integer type, Model model) {
        List<String> platforms = articleService.queryPlatforms(user.getId());
        model.addAttribute("platforms", platforms);
        model.addAttribute("type", type);
        return viewName("index");
    }

    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity list(@CurrentUser User user, String platform, Integer type) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "success");
        List<Balance> balances = balanceService.findAll(user.getId(), platform, type);
        entity.setObj(balances);
        return entity;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity remove(Long[] ids) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "删除成功！");
        balanceService.remove(ids);
        return entity;
    }

    @RequestMapping(value = "/update/date", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateDate(Long[] ids) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "存档更新成功！");
        balanceService.updateDate(ids);
        return entity;
    }
}
