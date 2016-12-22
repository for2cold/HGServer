package com.kazyle.hugohelper.server.function.core.vpn.mapper;

import com.kazyle.hugohelper.server.config.annotation.MyBatisMapper;
import com.kazyle.hugohelper.server.function.core.vpn.entity.Vpn;

import java.util.List;

/**
 * Created by Kazyle on 2016/9/18.
 */
@MyBatisMapper
public interface VpnMapper {

    /**
     * 统计
     * @param userId
     * @return
     */
    long queryCount(Long userId);

    /**
     * 列表
     * @param userId
     * @return
     */
    List<Vpn> queryList(Long userId);

    /**
     * 保存VPN
     * @param pojo
     */
    void save(Vpn pojo);
}
