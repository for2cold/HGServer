package com.kazyle.hugohelper.server.function.core.article.service.impl;

import com.google.common.collect.Maps;
import com.kazyle.hugohelper.server.function.core.article.entity.Article;
import com.kazyle.hugohelper.server.function.core.article.entity.ArticlePlatform;
import com.kazyle.hugohelper.server.function.core.article.repository.ArticleRepository;
import com.kazyle.hugohelper.server.function.core.article.service.ArticleService;
import com.kazyle.hugohelper.server.function.core.balance.repository.BalanceRepository;
import com.kazyle.hugohelper.server.function.core.balance.service.BalanceService;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    @Resource
    private BalanceRepository balanceRepository;

    @Resource
    private BalanceService balanceService;

    private static Map<String, Long> cacheMap = Maps.newConcurrentMap();

    @Override
    public List<Article> queryList(Long userId, Integer type, String platform) {
        if (type == null) {
            type = 0;
        }
        List<Article> articles = articleRepository.queryList(userId, type, platform);
        for (Article pojo : articles) {
            String url = pojo.getUrl();
            int index = url.length();
            if (index > 40) {
                url = url.substring(0, 40) + "...";
            }
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
        String url = pojo.getUrl();
        int index = url.indexOf("http");
        if (index > 1) {
            url = url.substring(index);
        }
        pojo.setUrl(url);
        pojo.setType(pojo.getType());
        if (pojo.getType() == null) {
            pojo.setType(0);
        }
        if (StringUtils.isNotEmpty(pojo.getWechat())) {
            pojo.setWechat(pojo.getWechat().toUpperCase());
        }
        articleRepository.save(pojo);

        if (StringUtils.isNotEmpty(pojo.getParams())) {
            balanceService.saveBalance(pojo.getUserId(), pojo.getPlatform(), pojo.getWechat(), pojo.getType(), pojo.getParams());
        }
    }

    @Override
    public void remove(Long[] ids) {
        if (ids != null && ids.length > 0) {
            articleRepository.remove(ids);
        }
    }

    @Override
    public void update(Long[] ids) {
        articleRepository.update(ids);
    }

    @Override
    public Article get(Long userId, String platform, String wechat, Integer type) {
        if (type == null) {
            type = 0;
        }
        List<String> platforms = Lists.newArrayList();
        if ("瞎转".equals(platform) || "无敌赚".equals(platform) || "牛逼赚".equals(platform) || "快转发".equals(platform) || "转乐".equals(platform)) {
            platforms.add("瞎转");
            platforms.add("无敌赚");
            platforms.add("牛逼赚");
            platforms.add("转乐");
            platforms.add("快转发");
        } else {
            platforms.add(platform);
        }
        List<Article> list = articleRepository.queryList(userId, type, platforms, wechat);
        if (CollectionUtils.isEmpty(list)) {
            if (StringUtils.isNotEmpty(wechat)) {
                wechat = null;
                list = articleRepository.queryList(userId, type, platforms, wechat);
                if (CollectionUtils.isEmpty(list)) {
                    return null;
                }
            } else {
                return null;
            }
        }
        Random random = new Random();
        int index = random.nextInt(list.size());
        Article pojo = list.get(index);

        if (pojo.getTimes() == (pojo.getVisitCount() + 1)) {
            articleRepository.updateActive(pojo.getId());
        }
        articleRepository.updateTimes(pojo.getId());

        return pojo;
    }


    @Override
    public List<String> queryPlatforms(Long userId) {
        return articleRepository.queryPlatforms(userId);
    }

    @Override
    public Article findOne(Long id) {
        return articleRepository.findOne(id);
    }

    @Override
    public void update(Long id, Article form) {
        Article pojo = articleRepository.findOne(id);
        pojo.setPlatform(form.getPlatform());
        pojo.setVisitCount(0);
        pojo.setTimes(form.getTimes());
        String url = form.getUrl();
        int index = url.indexOf("http");
        if (index > 1) {
            url = url.substring(index);
        }
        pojo.setUrl(url);
        if (pojo.getType() == null) {
            pojo.setType(0);
        }
        if (StringUtils.isNotEmpty(form.getWechat())) {
            pojo.setWechat(form.getWechat().toUpperCase());
        } else {
            pojo.setWechat("");
        }
        articleRepository.updateEntity(pojo);

        if (StringUtils.isNotEmpty(pojo.getParams())) {
            balanceService.saveBalance(pojo.getUserId(), pojo.getPlatform(), pojo.getWechat(), pojo.getType(), pojo.getParams());
        }
    }

    @Override
    public void changeActiveJob() {
        articleRepository.changeActiveJob();
    }
}
