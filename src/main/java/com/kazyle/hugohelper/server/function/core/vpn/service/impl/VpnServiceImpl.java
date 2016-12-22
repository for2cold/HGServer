package com.kazyle.hugohelper.server.function.core.vpn.service.impl;

import com.kazyle.hugohelper.server.config.domain.data.Page;
import com.kazyle.hugohelper.server.config.domain.data.PageRequest;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import com.kazyle.hugohelper.server.function.core.vpn.entity.Vpn;
import com.kazyle.hugohelper.server.function.core.vpn.entity.enums.VpnStatus;
import com.kazyle.hugohelper.server.function.core.vpn.repository.VpnRepository;
import com.kazyle.hugohelper.server.function.core.vpn.service.VpnService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Kazyle on 2016/9/18.
 */
@Service
public class VpnServiceImpl implements VpnService {

    @Resource
    private VpnRepository vpnRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<Vpn> queryList(Long userId, PageRequest pageable) {
        return vpnRepository.queryList(userId, pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(User user, Vpn pojo) {
        pojo.setUserId(user.getId());
        pojo.setCreateDate(new Date());
        pojo.setStatus(VpnStatus.IDLE);
        vpnRepository.save(pojo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Vpn> queryList(Long userId) {
        return vpnRepository.queryList(userId);
    }

    @Override
    public Vpn queryOne(Long userId, Long id) {
        return null;
    }
}
