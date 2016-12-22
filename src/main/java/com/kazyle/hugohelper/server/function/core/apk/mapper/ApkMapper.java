package com.kazyle.hugohelper.server.function.core.apk.mapper;

import com.kazyle.hugohelper.server.config.annotation.MyBatisMapper;
import com.kazyle.hugohelper.server.function.core.apk.entity.Apk;

import java.util.List;

/**
 * Created by Kazyle on 2016/9/8.
 */
@MyBatisMapper
public interface ApkMapper {
    List<Apk> queryList(Long userId);

    void delete(Long id);

    void save(Apk pojo);

    List<String> findList(Long userId);
}
