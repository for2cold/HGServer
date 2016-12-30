package com.kazyle.hugohelper.server.function.core.account.service.impl;

import com.google.common.collect.Lists;
import com.kazyle.hugohelper.server.function.core.account.entity.Account;
import com.kazyle.hugohelper.server.function.core.account.entity.AccountSheet;
import com.kazyle.hugohelper.server.function.core.account.entity.Email;
import com.kazyle.hugohelper.server.function.core.account.entity.WithdrawRecord;
import com.kazyle.hugohelper.server.function.core.account.entity.enums.AccountStatus;
import com.kazyle.hugohelper.server.function.core.account.repository.AccountRepository;
import com.kazyle.hugohelper.server.function.core.account.service.AccountService;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import com.kazyle.hugohelper.server.function.front.account.dto.AccountReportsDto;
import com.kazyle.hugohelper.server.function.front.account.dto.AccountSearchDto;
import com.kazyle.hugohelper.server.function.front.account.dto.AccountUpdateDto;
import com.kazyle.hugohelper.server.function.front.account.result.ReportResult;
import com.kazyle.hugohelper.server.function.front.account.result.WithdrawResult;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Kazyle on 2016/8/23.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountRepository accountRepository;

    @Override
    public List<Object[]> queryList(AccountSearchDto dto) {
        dto.setDeleted(Boolean.FALSE);
        List<Account> list = accountRepository.queryList(dto);
        List<Object[]> accounts = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(list)) {
            for (Account pojo : list) {
                Object[] obj = new Object[]{
                        pojo.getId(),
                        pojo.getMobile(),
                        pojo.getAmount(),
                        pojo.getBalance(),
                        pojo.getWithdrawBalance(),
                        pojo.getPhone(),
                        pojo.getAlipay(),
                        pojo.getStatus(),
                        pojo.getDeadlineText(),
                        pojo.getWithdrawDateText(),
                        pojo.getRemark()
                };
                accounts.add(obj);
            }
        }
        return accounts;
    }

    @Override
    public Long saveOrUpdate(User user, AccountUpdateDto dto) {

        Long id = dto.getId();
        Date today = new Date();

        Account account = new Account();
        account.setId(id);
        account.setUserId(user.getId());
        account.setMobile(dto.getMobile());
        account.setBalance(dto.getBalance());
        account.setWithdrawBalance(dto.getWithdrawBalance());
        account.setPhone(dto.getPhone());
        account.setAlipay(dto.getAlipay());
        account.setStatus(dto.getStatus());
        account.setDeadline(dto.getDeadline());
        account.setWithdrawDate(dto.getWithdrawDate());
        account.setRemark(dto.getRemark());
        account.setCreateDate(today);

        // 获取账目ID
        Long sheetId = null;
        Date date = dto.getWithdrawDate();
        if (date != null) {
            DateTime dateTime = new DateTime(date);
            String title = dateTime.toString("yyyy-MM");
            AccountSheet sheet = accountRepository.queryOneSheet(title);
            if (sheet == null) {
                // 添加账号
                sheet = new AccountSheet();
                sheet.setTitle(title);
                sheet.setCreateDate(today);
                sheet.setUserId(user.getId());
                accountRepository.createSheet(sheet);
            }
            sheetId = sheet.getId();
        } else {
            sheetId = 0L;
        }
        account.setSheetId(sheetId);
        account.setUpdateTime(DateTimeUtils.currentTimeMillis());

        if (id != null) {
            // update
            Account _account = accountRepository.queryOne(id);
            account.setAmount(_account.getAmount());
            if (account.getStatus() == AccountStatus.RECEIVED) {
                if (account.getBalance().compareTo(account.getWithdrawBalance()) < 0) {
                    account.setBalance(new BigDecimal(0));
                } else {
                    account.setBalance(account.getBalance().subtract(account.getWithdrawBalance()));
                }
                account.setAmount(_account.getAmount().add(account.getWithdrawBalance()));
                account.setWithdrawBalance(new BigDecimal(0));
            } else if (account.getStatus() == AccountStatus.IDLE && _account.getStatus() == AccountStatus.RECEIVED) {
                account.setDeadline(null);
            }
            accountRepository.update(account);
        } else {
            // save
            account.setAmount(new BigDecimal(0));
            accountRepository.create(account);
            id = account.getId();
        }
        if (account.getStatus() == AccountStatus.RECEIVED) {
            WithdrawRecord record = new WithdrawRecord();
            record.setUserId(user.getId());
            record.setAccountId(id);
            record.setWithdrawBalance(dto.getWithdrawBalance());
            record.setWithdrawDate(today);
            accountRepository.saveRecord(record);
        }

        return id;
    }

    @Override
    public WithdrawResult queryStatistics(User user, Date periodDate) {
        return accountRepository.queryStatistics(user, periodDate);
    }

    @Override
    public void remove(Long id) {
        accountRepository.updateLogicDeleted(id, Boolean.TRUE);
    }

    @Override
    public List<ReportResult> queryReports(Long userId, AccountReportsDto dto) {
        return accountRepository.queryReports(userId, dto);
    }

    @Override
    public List<Email> queryEmailList(Long userId) {
        return accountRepository.queryEmailList(userId);
    }
}