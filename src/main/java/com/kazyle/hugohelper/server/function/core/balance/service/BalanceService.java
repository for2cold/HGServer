package com.kazyle.hugohelper.server.function.core.balance.service;

import com.kazyle.hugohelper.server.function.core.balance.entity.Balance;

import java.util.List;

/**
 * <p>
 * <b>BalanceService</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/3
 */
public interface BalanceService {

    List<Balance> findAll(Long userId, String platform, Integer type);

    void remove(Long[] ids);

    void updateDate(Long[] ids);
}
