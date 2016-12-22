package com.kazyle.hugohelper.server.function.core.vpn.repository;

import com.github.pagehelper.PageHelper;
import com.kazyle.hugohelper.server.config.domain.data.Page;
import com.kazyle.hugohelper.server.config.domain.data.PageImpl;
import com.kazyle.hugohelper.server.config.domain.data.PageRequest;
import com.kazyle.hugohelper.server.function.core.script.entity.Script;
import com.kazyle.hugohelper.server.function.core.vpn.entity.Vpn;
import com.kazyle.hugohelper.server.function.core.vpn.mapper.VpnMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Kazyle on 2016/9/18.
 */
@Repository
public class VpnRepository {

    @Resource
    private VpnMapper vpnMapper;

    public Page<Vpn> queryList(Long userId, PageRequest pageable) {
        long total = vpnMapper.queryCount(userId);
        PageHelper.startPage(pageable.getPage(), pageable.getRows());
        List<Vpn> list = vpnMapper.queryList(userId);
        return new PageImpl<>(list, pageable, total);
    }

    public void save(Vpn pojo) {
        vpnMapper.save(pojo);
    }

    public List<Vpn> queryList(Long userId) {
        return vpnMapper.queryList(userId);
    }
}
