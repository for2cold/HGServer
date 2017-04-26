package com.kazyle.hugohelper.server.function.core.article.service.impl;

import com.kazyle.hugohelper.server.function.core.article.entity.Article;
import com.kazyle.hugohelper.server.function.core.article.entity.ArticlePlatform;
import com.kazyle.hugohelper.server.function.core.article.repository.ArticleRepository;
import com.kazyle.hugohelper.server.function.core.article.service.ArticleService;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>HGServer</p>
 * <p>
 * <b>ArticleServiceImpl</b> is
 * </p>
 *
 * @version 4.0
 * @since 4.0
 * Created by Kazyle on 2017/4/25.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleRepository articleRepository;

    @Override
    public List<Article> queryList(Long userId, Integer type) {
        if (type == null) {
            type = 0;
        }
        List<Article> articles = articleRepository.queryList(userId, type);
        for (Article pojo : articles) {
            String url = pojo.getUrl();
            int index = url.indexOf("?");
            if (index < 0) {
                index = url.length();
            }
            url = url.substring(0, index) + "...";
            pojo.setShortUrl(url);
        }
        return articles;
    }

    @Override
    public void save(User user, Article pojo) {

        ArticlePlatform platform = articleRepository.queryPlatform(user.getId(), pojo.getPlatform());
        if (platform == null) {
            platform = new ArticlePlatform();
            platform.setUserId(user.getId());
            platform.setName(pojo.getPlatform());
            platform.setCreateDate(new Date());
            articleRepository.savePlatform(platform);
        }
        pojo.setUserId(user.getId());
        pojo.setCreateDate(new Date());
        pojo.setType(pojo.getType());
        if (pojo.getType() == null) {
            pojo.setType(0);
        }
        String url = pojo.getUrl();
        int index = url.indexOf("http://");
        if (index > 0) {
            url = url.substring(index);
        }
        pojo.setUrl(url);
        articleRepository.save(pojo);
    }

    @Override
    public void remove(Long[] ids) {
        if (ids != null && ids.length > 0) {
            articleRepository.remove(ids);
        }
    }

    @Override
    public Article get(Long userId, String platform, Integer type) {
        if (type == null) {
            type = 0;
        }
        List<Article> list = articleRepository.queryList(userId, type, platform);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(list.size());
        return list.get(index);
    }


    @Override
    public List<String> queryPlatforms(Long userId) {
        return articleRepository.queryPlatforms(userId);
    }
}
