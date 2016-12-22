package com.kazyle.hugohelper.server.function.core.script.service.impl;

import com.kazyle.hugohelper.server.config.domain.data.Page;
import com.kazyle.hugohelper.server.config.domain.data.PageRequest;
import com.kazyle.hugohelper.server.function.config.SysConfig;
import com.kazyle.hugohelper.server.function.core.script.entity.Script;
import com.kazyle.hugohelper.server.function.core.script.entity.enums.ScriptStatus;
import com.kazyle.hugohelper.server.function.core.script.repository.ScriptRepository;
import com.kazyle.hugohelper.server.function.core.script.service.ScriptService;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Kazyle on 2016/8/24.
 */
@Service
public class ScriptServiceImpl implements ScriptService {

    @Resource
    private ScriptRepository scriptRepository;
    @Resource
    private SysConfig sysConfig;

    @Transactional(readOnly = true)
    @Override
    public Page<Script> queryList(User user, PageRequest pageRequest) {
        return scriptRepository.queryList(user.getId(), pageRequest);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(User user, Script pojo) {
        // 更新同key的其它脚本状态
        scriptRepository.updateStatus(user.getId(), pojo.getKey(), ScriptStatus.CLOSE);
        // 添加脚本
        pojo.setUserId(user.getId());
        pojo.setCreateDate(new Date());
        scriptRepository.save(pojo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void remove(Long id) {
        scriptRepository.remove(id);
    }

    @Override
    public List<Script> queryUpgrade(Long userId, List<Long> scriptIds) {
        List<Script> scripts = scriptRepository.queryByUserId(userId, scriptIds);
        for (Script pojo : scripts) {
            pojo.setPath(sysConfig.getAccessUrl() + pojo.getPath());
        }
        return scripts;
    }
}
