package com.kazyle.hugohelper.server.function.api.controller;

import com.kazyle.hugohelper.server.config.domain.data.BaseController;
import com.kazyle.hugohelper.server.config.domain.data.ResponseCode;
import com.kazyle.hugohelper.server.config.domain.data.ResponseEntity;
import com.kazyle.hugohelper.server.config.exception.FileException;
import com.kazyle.hugohelper.server.config.helper.FileHelper;
import com.kazyle.hugohelper.server.function.config.SysConfig;
import com.kazyle.hugohelper.server.function.core.script.entity.Script;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Kazyle on 2016/8/26.
 */
@RestController
@RequestMapping("/api/phonedata")
public class PhonedataApiController extends BaseController<User> {

    private static final int size = 1;
    int counter = 0;

    @Resource
    private SysConfig sysConfig;
    @Resource
    private FileHelper fileHelper;

    @RequestMapping("get")
    public ResponseEntity get(String userId) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "获取手机数据成功");

        File root = new File(sysConfig.getPath() + "/" + sysConfig.getPhoneDataFolder() + userId + "/");
        File[] files = root.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (counter != size) {
                    counter++;
                    return true;
                } else {
                    return false;
                }
            }
        });
        counter = 0;
        if (files.length == 0) {
            entity.setCode(ResponseCode.ERROR.getValue());
            entity.setMsg("手机数据用完了~");
            return entity;
        }
        Set<File> sendFiles = new HashSet<>();
        sendFiles.add(files[0]);
        try {
            String url = fileHelper.zip(sendFiles, sysConfig.getPhoneDataTempFolder(), true);
            entity.setObj(url);
        } catch (FileException e) {
            entity.setCode(ResponseCode.ERROR.getValue());
            entity.setMsg("压缩资源失败");
        }
        return entity;
    }
}
