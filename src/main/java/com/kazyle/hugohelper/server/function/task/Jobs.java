package com.kazyle.hugohelper.server.function.task;

import com.kazyle.hugohelper.server.function.core.article.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * <b>Jobs</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/8
 */
@Service
public class Jobs {

    private Logger LOGGER = LoggerFactory.getLogger(Jobs.class);

    // 定时每天0点5分执行
    private static final String CRON_EXPRESS = "0 1 0 * * ?";

    @Resource
    private ArticleService articleService;

    @Scheduled(cron = CRON_EXPRESS)
    public void changeArticleActive() {
        System.out.println("==================job start==================");
        articleService.changeActiveJob();
        System.out.println("==================job end==================");
    }
}
