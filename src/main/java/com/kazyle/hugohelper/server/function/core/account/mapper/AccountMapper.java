package com.kazyle.hugohelper.server.function.core.account.mapper;

import com.kazyle.hugohelper.server.config.annotation.MyBatisMapper;
import com.kazyle.hugohelper.server.function.core.account.entity.Account;
import com.kazyle.hugohelper.server.function.core.account.entity.AccountSheet;
import com.kazyle.hugohelper.server.function.core.account.entity.WithdrawRecord;
import com.kazyle.hugohelper.server.function.front.account.dto.AccountSearchDto;
import com.kazyle.hugohelper.server.function.front.account.result.WithdrawResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Kazyle on 2016/8/23.
 */
@MyBatisMapper
public interface AccountMapper {

    /**
     * 根据条件查询账号
     * @param context
     * @return
     */
    List<Account> queryList(Map<String, Object> context);

    /**
     * 根据日期获取账目
     * @param title
     * @return
     */
    AccountSheet queryOneSheet(String title);

    /**
     * 创建账目
     * @param sheet
     */
    void createSheet(AccountSheet sheet);

    /**
     * 更新账号信息
     * @param account
     */
    void update(Account account);

    /**
     * 添加账号信息
     * @param account
     */
    void create(Account account);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Account queryOne(Long id);

    /**
     * 更新逻辑删除标识
     * @param context
     */
    void updateDeleted(Map<String, Object> context);

    /**
     * 保存提现记录
     * @param record
     */
    void saveRecord(WithdrawRecord record);

    /**
     * 统计收入信息
     * @param context
     * @return
     */
    WithdrawResult queryStatistics(Map<String, Object> context);
}
