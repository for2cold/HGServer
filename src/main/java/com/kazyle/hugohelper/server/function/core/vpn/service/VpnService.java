package com.kazyle.hugohelper.server.function.core.vpn.service;

import com.kazyle.hugohelper.server.config.domain.data.Page;
import com.kazyle.hugohelper.server.config.domain.data.PageRequest;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import com.kazyle.hugohelper.server.function.core.vpn.entity.Vpn;

import java.util.List;

/**
 * Created by Kazyle on 2016/9/18.
 */
public interface VpnService {

    Page<Vpn> queryList(Long userId, PageRequest pageable);

    void save(User user, Vpn pojo);

    List<Vpn> queryList(Long userId);

    Vpn queryOne(Long userId, Long id);
}
