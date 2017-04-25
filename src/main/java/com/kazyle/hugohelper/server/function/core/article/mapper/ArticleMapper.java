package com.kazyle.hugohelper.server.function.core.article.mapper;

import com.kazyle.hugohelper.server.config.annotation.MyBatisMapper;
import com.kazyle.hugohelper.server.function.core.article.entity.Article;

import java.util.List;
import java.util.Map;

/**
 * <p>HGServer</p>
 * <p>
 * <b>ArticleMapper</b> is
 * </p>
 *
 * @version 4.0
 * @since 4.0
 * Created by Kazyle on 2017/4/25.
 */
@MyBatisMapper
public interface ArticleMapper {

    /**
     * 获取文章列表
     * @param context
     * @return
     */
    List<Article> findList(Map<String, Object> context);

    /**
     * 保存文章
     * @param pojo
     */
    void save(Article pojo);

    /**
     * 删除文章
     * @param idList
     */
    void delete(List<Long> idList);
}
