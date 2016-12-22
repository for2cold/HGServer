package com.kazyle.hugohelper.server.function.core.user.service.impl;

import com.kazyle.hugohelper.server.config.domain.data.Page;
import com.kazyle.hugohelper.server.config.domain.data.PageRequest;
import com.kazyle.hugohelper.server.config.util.Md5Utils;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import com.kazyle.hugohelper.server.function.core.user.exception.LoginException;
import com.kazyle.hugohelper.server.function.core.user.repository.UserRepository;
import com.kazyle.hugohelper.server.function.core.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Kazyle on 2016/8/24.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public User login(String username, String password) throws LoginException {
        User user = userRepository.findByUsername(username);
        if (null == user) {
            throw new LoginException();
        }
        password = Md5Utils.hash(user.getId() + password);
        if (!user.getPassword().equals(password)) {
            throw new LoginException();
        }
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> queryList(PageRequest pageRequest) {
        return userRepository.queryList(pageRequest);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void remove(Long id) {
        userRepository.remove(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(User user, User pojo) {
        // 添加账号
        pojo.setCreateDate(new Date());
        userRepository.save(pojo);
        // 更新密码
        System.out.println("pojo--------->" + pojo.getId());
        String password = Md5Utils.hash(pojo.getId() + pojo.getPassword());
        userRepository.updatePassword(pojo.getId(), password);
    }
}
