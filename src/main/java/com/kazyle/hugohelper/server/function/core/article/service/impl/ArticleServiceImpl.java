package com.kazyle.hugohelper.server.function.core.article.service.impl;

import com.kazyle.hugohelper.server.function.core.article.entity.Article;
import com.kazyle.hugohelper.server.function.core.article.repository.ArticleRepository;
import com.kazyle.hugohelper.server.function.core.article.service.ArticleService;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
            url = url.substring(0, url.indexOf("?")) + "...";
            pojo.setShortUrl(url);
        }
        return articles;
    }

    @Override
    public void save(User user, Article pojo) {
        pojo.setUserId(user.getId());
        pojo.setCreateDate(new Date());
        pojo.setType(pojo.getType());
        if (pojo.getType() == null) {
            pojo.setType(0);
        }
        String url = pojo.getUrl();
        url = url.substring(url.indexOf("http://"));
        pojo.setUrl(url);
        articleRepository.save(pojo);
    }

    @Override
    public void remove(Long[] ids) {
        if (ids != null && ids.length > 0) {
            articleRepository.remove(ids);
        }
    }
}
