package com.kazyle.hugohelper.server.function.core.apk.service;

import com.kazyle.hugohelper.server.function.core.apk.entity.Apk;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Created by Kazyle on 2016/9/8.
 */
public interface ApkService {

    List<Apk> queryList(Long id);

    void remove(Long id);

    void save(Long userId, String path, String name);

    Set<File> findList(Long userId);
}
