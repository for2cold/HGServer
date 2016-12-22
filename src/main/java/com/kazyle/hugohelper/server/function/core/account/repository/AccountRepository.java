package com.kazyle.hugohelper.server.function.core.account.repository;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.kazyle.hugohelper.server.config.domain.data.Page;
import com.kazyle.hugohelper.server.config.domain.data.PageImpl;
import com.kazyle.hugohelper.server.config.domain.data.PageRequest;
import com.kazyle.hugohelper.server.function.core.account.entity.Account;
import com.kazyle.hugohelper.server.function.core.account.entity.AccountSheet;
import com.kazyle.hugohelper.server.function.core.account.mapper.AccountMapper;
import com.kazyle.hugohelper.server.function.front.account.dto.AccountSearchDto;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Kazyle on 2016/8/23.
 */
@Repository
public class AccountRepository {

    @Resource
    private AccountMapper accountMapper;

    public List<Account> queryList(AccountSearchDto dto) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", dto.getUserId());
        context.put("period", dto.getPeriod());
        context.put("status", dto.getStatus());
        context.put("phone", dto.getPhone());
        context.put("alipay", dto.getAlipay());
        context.put("balance", dto.getBalance());
        context.put("sortMap", dto.getSortMap());
        context.put("deleted", dto.isDeleted());
        return accountMapper.queryList(context);
    }

    public AccountSheet queryOneSheet(String title) {
        return accountMapper.queryOneSheet(title);
    }

    public void createSheet(AccountSheet sheet) {
        accountMapper.createSheet(sheet);
    }

    public void update(Account account) {
        accountMapper.update(account);
    }

    public void create(Account account) {
        accountMapper.create(account);
    }

    public Account queryOne(Long id) {
        return accountMapper.queryOne(id);
    }

    public void updateLogicDeleted(Long id, boolean deleted) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("id", id);
        context.put("deleted", deleted);
        accountMapper.updateDeleted(context);
    }
}
