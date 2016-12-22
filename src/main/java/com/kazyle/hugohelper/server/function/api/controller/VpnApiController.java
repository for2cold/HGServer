package com.kazyle.hugohelper.server.function.api.controller;

import com.kazyle.hugohelper.server.config.domain.data.BaseController;
import com.kazyle.hugohelper.server.config.domain.data.ResponseCode;
import com.kazyle.hugohelper.server.config.domain.data.ResponseEntity;
import com.kazyle.hugohelper.server.function.core.vpn.entity.Vpn;
import com.kazyle.hugohelper.server.function.core.vpn.service.VpnService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Kazyle on 2016/9/18.
 */
@RestController
@RequestMapping("/api/vpn")
public class VpnApiController extends BaseController<Vpn> {

    @Resource
    private VpnService vpnService;

    @RequestMapping("/list")
    public ResponseEntity list(Long userId) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "获取列表成功");
        List<Vpn> vpns = vpnService.queryList(userId);
        entity.setObj(vpns);
        return entity;
    }

    @RequestMapping("/get")
    public ResponseEntity get(Long userId, Long id) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "获取一组VPN成功");
        Vpn vpn = vpnService.queryOne(userId, id);
        entity.setObj(vpn);
        return entity;
    }
}
