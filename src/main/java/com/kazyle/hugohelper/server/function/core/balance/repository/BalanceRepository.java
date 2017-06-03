package com.kazyle.hugohelper.server.function.core.balance.repository;

import com.google.common.collect.Maps;
import com.kazyle.hugohelper.server.function.core.balance.entity.Balance;
import com.kazyle.hugohelper.server.function.core.balance.mapper.BalanceMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * <b>BalanceRepository</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/3
 */
@Repository
public class BalanceRepository {

    @Resource
    private BalanceMapper balanceMapper;

    public List<Balance> findAll(Long userId, String platform, Integer type) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", userId);
        context.put("platform", platform);
        context.put("type", type);
        return balanceMapper.findAll(context);
    }

    public void save(Balance pojo) {
        balanceMapper.save(pojo);
    }

    public long countOne(Long userId, String platform, String username, Integer type) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", userId);
        context.put("platform", platform);
        context.put("type", type);
        context.put("username", username);
        return balanceMapper.countOne(context);
    }

    public void remove(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        balanceMapper.delete(idList);
    }

    public List<Balance> findByIds(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        return balanceMapper.findByIds(idList);
    }

    public void update(Long id, Date date) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("id", id);
        context.put("date", date);
        balanceMapper.update(context);
    }
}
