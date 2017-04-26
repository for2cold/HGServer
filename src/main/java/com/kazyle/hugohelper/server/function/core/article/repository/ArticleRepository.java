package com.kazyle.hugohelper.server.function.core.article.repository;

import com.google.common.collect.Maps;
import com.kazyle.hugohelper.server.function.core.article.entity.Article;
import com.kazyle.hugohelper.server.function.core.article.entity.ArticlePlatform;
import com.kazyle.hugohelper.server.function.core.article.mapper.ArticleMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>HGServer</p>
 * <p>
 * <b>ArticleRepository</b> is
 * </p>
 *
 * @version 4.0
 * @since 4.0
 * Created by Kazyle on 2017/4/25.
 */
@Repository
public class ArticleRepository {

    @Resource
    private ArticleMapper articleMapper;

    public List<Article> queryList(Long userId, Integer type) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", userId);
        context.put("type", type);
        return articleMapper.findList(context);
    }

    public void save(Article pojo) {
        articleMapper.save(pojo);
    }

    public void remove(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        articleMapper.delete(idList);
    }

    public List<Article> queryList(Long userId, Integer type, String platform) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", userId);
        context.put("type", type);
        context.put("platform", platform);
        return articleMapper.queryList(context);
    }

    public List<String> queryPlatforms(Long userId) {
        return articleMapper.queryPlatforms(userId);
    }

    public ArticlePlatform queryPlatform(Long userId, String platform) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", userId);
        context.put("platform", platform);
        return articleMapper.queryPlatform(context);
    }

    public void savePlatform(ArticlePlatform platform) {
        articleMapper.savePlatform(platform);
    }
}
