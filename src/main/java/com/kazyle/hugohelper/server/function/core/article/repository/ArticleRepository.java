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

    public List<Article> queryList(Long userId, Integer type, String platform) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", userId);
        context.put("type", type);
        context.put("platform", platform);
        return articleMapper.findList(context);
    }

    public void save(Article pojo) {
        articleMapper.save(pojo);
    }

    public void remove(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        articleMapper.delete(idList);
    }

    public List<Article> queryList(Long userId, Integer type, List<String> platforms, String wechat) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", userId);
        context.put("type", type);
        context.put("platforms", platforms);
        context.put("wechat", wechat);
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

    public void update(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        articleMapper.update(idList);
    }

    public void updateActive(Long id) {
        articleMapper.updateActive(id);
    }

    public void updateTimes(Long id) {
        articleMapper.updateTimes(id);
    }

    public void updateHold(Long userId, String platform, Integer type, String wechat, int hold) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", userId);
        context.put("platform", platform);
        context.put("type", type);
        context.put("wechat", wechat);
        context.put("hold", hold);
        articleMapper.updateHold(context);
    }

    public Article findOne(Long id) {
        return articleMapper.findOne(id);
    }

    public void updateEntity(Article pojo) {
        articleMapper.updateEntity(pojo);
    }

    public void autoStop(String platform, String username, Integer type, Long userId) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", userId);
        context.put("platform", platform);
        context.put("type", type);
        context.put("wechat", username);
        context.put("active", 0);
        context.put("visitCount", 0);
        articleMapper.autoStop(context);
    }

    public void activeArticle(Long userId, Integer type, String platform, String username) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", userId);
        context.put("platform", platform);
        context.put("type", type);
        context.put("wechat", username);
        context.put("visitCount", 0);
        articleMapper.activeArticle(context);
    }

    public void changeActiveJob() {
        articleMapper.changeActiveJob();
    }

    public void updateLink(Long userId, String platform, Integer type, String username, String url) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", userId);
        context.put("platform", platform);
        context.put("type", type);
        context.put("wechat", username);
        context.put("url", url);
        articleMapper.updateLink(context);
    }
}
