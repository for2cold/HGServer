package com.kazyle.hugohelper.server.function.core.account.service;

import com.kazyle.hugohelper.server.config.domain.data.Page;
import com.kazyle.hugohelper.server.config.domain.data.PageRequest;
import com.kazyle.hugohelper.server.function.core.account.entity.Account;
import com.kazyle.hugohelper.server.function.core.account.entity.Email;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import com.kazyle.hugohelper.server.function.front.account.dto.AccountReportsDto;
import com.kazyle.hugohelper.server.function.front.account.dto.AccountSearchDto;
import com.kazyle.hugohelper.server.function.front.account.dto.AccountUpdateDto;
import com.kazyle.hugohelper.server.function.front.account.result.ReportResult;
import com.kazyle.hugohelper.server.function.front.account.result.WithdrawResult;

import java.util.Date;
import java.util.List;

/**
 * Created by Kazyle on 2016/8/23.
 */
public interface AccountService {

    /**
     * 获取账号
     * @param dto
     * @return
     */
    List<Object[]> queryList(AccountSearchDto dto);

    /**
     * 添加或更新账号信息
     * @param user
     * @param dto
     * @return
     */
    Long saveOrUpdate(User user, AccountUpdateDto dto);

    /**
     * 删除账号
     * @param id
     */
    void remove(Long id);

    /**
     * 统计收入信息
     * @param user
     * @param periodDate
     * @return
     */
    WithdrawResult queryStatistics(User user, Date periodDate);

    /**
     * 收入报表
     * @param id
     * @param dto
     * @return
     */
    List<ReportResult> queryReports(Long id, AccountReportsDto dto);

    /**
     * 获取邮箱列表
     * @param id
     * @return
     */
    List<Email> queryEmailList(Long id);
}
