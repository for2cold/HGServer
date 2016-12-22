package com.kazyle.hugohelper.server.function.core.user.service;

import com.kazyle.hugohelper.server.config.domain.data.Page;
import com.kazyle.hugohelper.server.config.domain.data.PageRequest;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import com.kazyle.hugohelper.server.function.core.user.exception.LoginException;

/**
 * Created by Kazyle on 2016/8/24.
 */
public interface UserService {

    /**
     * 登录账号
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password) throws LoginException;

    /**
     * 获取后台账号列表
     * @return
     * @param pageRequest
     */
    Page<User> queryList(PageRequest pageRequest);

    /**
     * 删除管理员
     * @param id
     */
    void remove(Long id);

    /**
     * 添加管理员
     * @param user
     * @param pojo
     */
    void save(User user, User pojo);
}
