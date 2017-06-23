package com.kazyle.hugohelper.server.function.front.balance.controller;

import com.kazyle.hugohelper.server.config.annotation.CurrentUser;
import com.kazyle.hugohelper.server.config.domain.data.BaseFrontController;
import com.kazyle.hugohelper.server.config.domain.data.ResponseCode;
import com.kazyle.hugohelper.server.config.domain.data.ResponseEntity;
import com.kazyle.hugohelper.server.function.core.article.service.ArticleService;
import com.kazyle.hugohelper.server.function.core.balance.entity.Balance;
import com.kazyle.hugohelper.server.function.core.balance.service.BalanceService;
import com.kazyle.hugohelper.server.function.core.balance.view.WuDiZhuanRecordView;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import com.kazyle.hugohelper.server.function.front.balance.form.QueryForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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
    public ResponseEntity list(@CurrentUser User user, QueryForm form) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "success");
        List<Balance> balances = balanceService.findAll(user.getId(), form.getPlatform(), form.getType(), form.getDate());
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

    @RequestMapping(value = "/{id}/withdraw", method = RequestMethod.GET)
    public String withdraw(@PathVariable("id") Long id) throws IOException {
        String url = balanceService.getWithdrawUrl(id);
        return redirectToUrl(url);
    }

    @RequestMapping(value = "/{id}/record", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity record(@PathVariable("id") Long id) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "获取提现记录成功！");
        List<WuDiZhuanRecordView> records = balanceService.getRecord(id);
        entity.setObj(records);
        return entity;
    }

    @RequestMapping(value = "{id}/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateArticle(@PathVariable("id") Long id) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "操作成功！");
        balanceService.updateArticle(id);
        return entity;
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateArticles(Long[] ids) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "操作成功！");
        for (Long id : ids) {
            balanceService.updateArticle(id);
        }
        return entity;
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity importAccount(@CurrentUser User user, Integer type, MultipartFile file) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "导入成功！");
        String msg = balanceService.importAccount(user, type, file);
        entity.setMsg(msg);
        return entity;
    }

    @RequestMapping(value = "/update/link", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateLink(Long[] ids) throws IOException {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "链接更新成功！");
        balanceService.updateLink(ids);
        return entity;
    }
}
