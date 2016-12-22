package com.kazyle.hugohelper.server.function.core.apk.service.impl;

import com.kazyle.hugohelper.server.function.config.SysConfig;
import com.kazyle.hugohelper.server.function.core.apk.entity.Apk;
import com.kazyle.hugohelper.server.function.core.apk.repository.ApkRepository;
import com.kazyle.hugohelper.server.function.core.apk.service.ApkService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Kazyle on 2016/9/8.
 */
@Service
public class ApkServiceImpl implements ApkService {

    @Resource
    private ApkRepository apkRepository;
    @Resource
    private SysConfig sysConfig;

    @Override
    public List<Apk> queryList(Long userId) {
        return apkRepository.queryList(userId);
    }

    @Override
    public void remove(Long userId) {
        apkRepository.remove(userId);
    }

    @Override
    public void save(Long userId, String path, String name) {
        Apk pojo = new Apk();
        pojo.setPath(path);
        pojo.setCreateDate(new Date());
        pojo.setName(name);
        pojo.setUserId(userId);
        apkRepository.save(pojo);
    }

    @Override
    public Set<File> findList(Long userId) {
        List<String> paths = apkRepository.findList(userId);
        Set<File> apkSet = new HashSet<>();
        for (String path : paths) {
            File file = new File(sysConfig.getPath() + "/" + path);
            apkSet.add(file);
        }
        return apkSet;
    }
}
