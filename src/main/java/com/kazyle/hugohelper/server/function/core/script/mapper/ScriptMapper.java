package com.kazyle.hugohelper.server.function.core.script.mapper;

import com.kazyle.hugohelper.server.config.annotation.MyBatisMapper;
import com.kazyle.hugohelper.server.function.core.script.entity.Script;
import com.kazyle.hugohelper.server.function.core.script.entity.enums.ScriptStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Kazyle on 2016/8/24.
 */
@MyBatisMapper
public interface ScriptMapper {

    /**
     * 统计脚本列表
     * @param userId
     * @return
     */
    long queryCount(@Param("userId") Long userId);

    /**
     * 获取脚本列表
     * @param userId
     * @return
     */
    List<Script> queryList(@Param("userId") Long userId);

    /**
     * 添加脚本
     * @param pojo
     */
    void save(Script pojo);

    /**
     * 删除脚本
     * @param id
     */
    void delete(Long id);

    /**
     * 更新唯一码脚本的状态
     * @param context
     */
    void updateStatus(Map<String, Object> context);

    /**
     * 根据账号获取脚本
     * @param context
     * @return
     */
    List<Script> queryByUserId(Map<String, Object> context);
}
