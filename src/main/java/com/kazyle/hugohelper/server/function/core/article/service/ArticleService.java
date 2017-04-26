package com.kazyle.hugohelper.server.function.core.article.service;

import com.kazyle.hugohelper.server.function.core.article.entity.Article;
import com.kazyle.hugohelper.server.function.core.user.entity.User;

import java.util.List;

/**
 * <p>HGServer</p>
 * <p>
 * <b>ArticleService</b> is
 * </p>
 *
 * @version 4.0
 * @since 4.0
 * Created by Kazyle on 2017/4/25.
 */
public interface ArticleService {
    /**
     * 获取文章列表
     * @param id
     * @param type
     * @return
     */
    List<Article> queryList(Long id, Integer type);

    /**
     * 添加文章
     * @param user
     * @param pojo
     */
    void save(User user, Article pojo);

    /**
     * 删除文章
     * @param ids
     */
    void remove(Long[] ids);

    /**
     * 随机获取文章
     * @param userId
     * @param platform
     * @param type
     * @return
     */
    Article get(Long userId, String platform, Integer type);

    /**
     * 获取平台列表
     * @param userId
     * @return
     */
    List<String> queryPlatforms(Long userId);
}
