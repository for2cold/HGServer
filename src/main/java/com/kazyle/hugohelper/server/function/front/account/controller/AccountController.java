package com.kazyle.hugohelper.server.function.front.account.controller;

import com.github.pagehelper.PageInfo;
import com.kazyle.hugohelper.server.config.annotation.CurrentUser;
import com.kazyle.hugohelper.server.config.domain.data.*;
import com.kazyle.hugohelper.server.function.core.account.entity.Account;
import com.kazyle.hugohelper.server.function.core.account.service.AccountService;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import com.kazyle.hugohelper.server.function.front.account.dto.AccountReportsDto;
import com.kazyle.hugohelper.server.function.front.account.dto.AccountSearchDto;
import com.kazyle.hugohelper.server.function.front.account.dto.AccountUpdateDto;
import com.kazyle.hugohelper.server.function.front.account.result.ReportResult;
import com.kazyle.hugohelper.server.function.front.account.result.WithdrawResult;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Kazyle on 2016/8/23.
 */
@Controller
@RequestMapping("/front/account")
public class AccountController extends BaseFrontController<Account> {

    private static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private AccountService accountService;

    @RequestMapping({"", "/", "/index"})
    public String index(@CurrentUser User user, AccountSearchDto dto, Model model) {

        model.addAttribute("searchView", dto);
        dto.setUserId(user.getId());
        List<Object[]> accounts = accountService.queryList(dto);
        model.addAttribute("accounts", jsonHelper.toJSONString(accounts));

        // 收入统计
        if (dto.getPeriodDate() != null) {
            WithdrawResult withdrawResult = accountService.queryStatistics(user, dto.getPeriodDate());
            model.addAttribute("withdrawResult", withdrawResult);
        }
        return viewName("index");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity update(@CurrentUser User user, AccountUpdateDto dto) {

        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue());

        String msg = "账号添加成功";
        if (dto.getId() != null) {
            msg = "账号更新成功！";
        }
        Long id = accountService.saveOrUpdate(user, dto);

        entity.setMsg(msg);
        entity.setObj(id);
        return entity;
    }

    @RequestMapping("{id}/remove")
    @ResponseBody
    public ResponseEntity remove(@PathVariable("id") Long id) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "账号删除成功！");
        accountService.remove(id);
        return entity;
    }

    @RequestMapping("/reports")
    @ResponseBody
    public ResponseEntity reports(@CurrentUser User user, AccountReportsDto dto) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "获取报表成功");
        List<ReportResult> results = accountService.queryReports(user.getId(), dto);
        return entity;
    }
}
