package com.kazyle.hugohelper.server.function.core.apk.repository;

import com.kazyle.hugohelper.server.function.core.apk.entity.Apk;
import com.kazyle.hugohelper.server.function.core.apk.mapper.ApkMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Kazyle on 2016/9/8.
 */
@Repository
public class ApkRepository {

    @Resource
    private ApkMapper apkMapper;

    public List<Apk> queryList(Long userId) {
        return apkMapper.queryList(userId);
    }

    public void remove(Long id) {
        apkMapper.delete(id);
    }

    public void save(Apk pojo) {
        apkMapper.save(pojo);
    }

    public List<String> findList(Long userId) {
        return apkMapper.findList(userId);
    }
}
