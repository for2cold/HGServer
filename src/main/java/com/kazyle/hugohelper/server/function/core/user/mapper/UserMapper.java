package com.kazyle.hugohelper.server.function.core.user.mapper;

import com.kazyle.hugohelper.server.config.annotation.MyBatisMapper;
import com.kazyle.hugohelper.server.function.core.user.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Kazyle on 2016/8/24.
 */
@MyBatisMapper
public interface UserMapper {

    /**
     * 根据账号名获取账号
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 获取后台账号
     * @return
     */
    List<User> queryList();

    /**
     * 删除后台账号
     * @param id
     */
    void remove(Long id);

    /**
     * 添加账号
     * @param user
     */
    void save(User user);

    /**
     * 更新密码
     * @param context
     */
    void updatePassword(Map<String, Object> context);
}
