package com.kazyle.hugohelper.server.function.core.script.service;

import com.kazyle.hugohelper.server.config.domain.data.Page;
import com.kazyle.hugohelper.server.config.domain.data.PageRequest;
import com.kazyle.hugohelper.server.function.core.script.entity.Script;
import com.kazyle.hugohelper.server.function.core.user.entity.User;

import java.util.List;

/**
 * Created by Kazyle on 2016/8/24.
 */
public interface ScriptService {

    /**
     * 获取脚本列表
     * @param user
     * @param pageRequest
     * @return
     */
    Page<Script> queryList(User user, PageRequest pageRequest);

    /**
     * 添加脚本
     * @param user
     * @param pojo
     */
    void save(User user, Script pojo);

    /**
     * 删除脚本
     * @param id
     */
    void remove(Long id);

    /**
     * 更新脚本
     * @param userId
     * @param scriptIds
     * @return
     */
    List<Script> queryUpgrade(Long userId, List<Long> scriptIds);
}
