package com.kazyle.hugohelper.server.function.core.balance.mapper;

import com.kazyle.hugohelper.server.config.annotation.MyBatisMapper;
import com.kazyle.hugohelper.server.function.core.balance.entity.Balance;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * <b>BalanceMapper</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/3
 */
@MyBatisMapper
public interface BalanceMapper {

    List<Balance> findAll(Map<String, Object> context);

    void save(Balance pojo);

    long countOne(Map<String, Object> context);

    void delete(List<Long> idList);

    List<Balance> findByIds(List<Long> idList);

    void update(Map<String, Object> context);

    void updateWithdraw(Map<String, Object> context);

    Balance findOne(Long id);
}
