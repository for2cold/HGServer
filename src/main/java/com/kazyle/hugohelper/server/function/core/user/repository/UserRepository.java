package com.kazyle.hugohelper.server.function.core.user.repository;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.kazyle.hugohelper.server.config.domain.data.Page;
import com.kazyle.hugohelper.server.config.domain.data.PageImpl;
import com.kazyle.hugohelper.server.config.domain.data.PageRequest;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import com.kazyle.hugohelper.server.function.core.user.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Kazyle on 2016/8/24.
 */
@Repository
public class UserRepository {

    @Resource
    private UserMapper userMapper;

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public Page<User> queryList(PageRequest pageRequest) {
        com.github.pagehelper.Page<User> page
                = PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows(), true);
        List<User> list = userMapper.queryList();
        return new PageImpl<>(list, pageRequest, page.getTotal());
    }

    public void remove(Long id) {
        userMapper.remove(id);
    }

    public void save(User user) {
        userMapper.save(user);
    }

    public void updatePassword(Long id, String password) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("id", id);
        context.put("password", password);
        userMapper.updatePassword(context);
    }
}
