package com.kazyle.hugohelper.server.function.core.script.repository;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.kazyle.hugohelper.server.config.domain.data.Page;
import com.kazyle.hugohelper.server.config.domain.data.PageImpl;
import com.kazyle.hugohelper.server.config.domain.data.PageRequest;
import com.kazyle.hugohelper.server.function.core.account.entity.Account;
import com.kazyle.hugohelper.server.function.core.script.entity.Script;
import com.kazyle.hugohelper.server.function.core.script.entity.enums.ScriptStatus;
import com.kazyle.hugohelper.server.function.core.script.mapper.ScriptMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Kazyle on 2016/8/24.
 */
@Repository
public class ScriptRepository {

    @Resource
    private ScriptMapper scriptMapper;

    public Page<Script> queryList(Long userId, PageRequest pageRequest) {
        long total = scriptMapper.queryCount(userId);

        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<Script> list = scriptMapper.queryList(userId);

        return new PageImpl<>(list, pageRequest, total);
    }

    public void save(Script pojo) {
        scriptMapper.save(pojo);
    }

    public void remove(Long id) {
        scriptMapper.delete(id);
    }

    public void updateStatus(Long userId, String key, ScriptStatus status) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", userId);
        context.put("key", key);
        context.put("status", status.ordinal());
        scriptMapper.updateStatus(context);
    }

    public List<Script> queryByUserId(Long userId, List<Long> scriptIds) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", userId);
        context.put("scriptIds", scriptIds);
        context.put("status", ScriptStatus.OPEN.ordinal());
        return scriptMapper.queryByUserId(context);
    }
}
