package com.kazyle.hugohelper.server.function.api.controller;

import com.kazyle.hugohelper.server.config.domain.data.BaseController;
import com.kazyle.hugohelper.server.config.domain.data.ResponseCode;
import com.kazyle.hugohelper.server.config.domain.data.ResponseEntity;
import com.kazyle.hugohelper.server.config.exception.FileException;
import com.kazyle.hugohelper.server.config.helper.FileHelper;
import com.kazyle.hugohelper.server.function.config.SysConfig;
import com.kazyle.hugohelper.server.function.core.apk.entity.Apk;
import com.kazyle.hugohelper.server.function.core.apk.service.ApkService;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Kazyle on 2016/8/28.
 */
@RestController
@RequestMapping("/api/apk")
public class ApkApiController extends BaseController<User> {

    @Resource
    private SysConfig sysConfig;
    @Resource
    private FileHelper fileHelper;
    @Resource
    private ApkService apkService;

    @RequestMapping("init")
    public ResponseEntity init(Long userId) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "获取APK成功");

        String target = sysConfig.getPath() + "/" + sysConfig.getApkFolder();
        String filename = userId + "_initapk.zip";
        String url = sysConfig.getAccessUrl() + sysConfig.getApkFolder() + filename;
        File apkzip = new File(target + filename);
        if (apkzip.exists()) {
            entity.setObj(url);
            return entity;
        }
        Set<File> apkSet = apkService.findList(userId);
        if (apkSet.isEmpty()) {
            entity.setCode(ResponseCode.ERROR.getValue());
            entity.setMsg("请先上传APK应用");
            return entity;
        }
        try {
            fileHelper.zip(apkSet, sysConfig.getApkFolder(), filename, false);
            entity.setObj(url);
        } catch (FileException e) {
            entity.setCode(ResponseCode.ERROR.getValue());
            entity.setMsg(e.getMessage());
        }

        return entity;
    }

    @RequestMapping("list")
    public ResponseEntity list(long userId, String signature) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "获取APK列表");
        if (userId != 2 && !sysConfig.getSignature().equals(signature)) {
            entity.setCode(ResponseCode.ERROR.getValue());
            entity.setMsg("请先上传APK");
            return entity;
        }
        List<Apk> apks = apkService.queryList(userId);
        for (Apk apk : apks) {
            apk.setPath(sysConfig.getAccessUrl() + apk.getPath());
        }
        entity.setObj(apks);
        return entity;
    }
}
