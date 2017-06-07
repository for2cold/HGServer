package com.kazyle.hugohelper.server.function.api.controller;

import com.kazyle.hugohelper.server.config.domain.data.BaseController;
import com.kazyle.hugohelper.server.config.domain.data.ResponseCode;
import com.kazyle.hugohelper.server.config.domain.data.ResponseEntity;
import com.kazyle.hugohelper.server.function.core.article.entity.Article;
import com.kazyle.hugohelper.server.function.core.article.service.ArticleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>HGServer</p>
 * <p>
 * <b>ArticleController</b> is
 * </p>
 *
 * @version 4.0
 * @since 4.0
 * Created by Kazyle on 2017/4/26.
 */
@RestController
@RequestMapping("/api/article")
public class ArticleApiController extends BaseController<Article> {

    @Resource
    private ArticleService articleService;

    @RequestMapping("/index")
    public ResponseEntity index(Long userId, String platform, String wechat, Integer type) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "获取文章成功");

        Article pojo = articleService.get(userId, platform, wechat, type);
        entity.setObj(pojo);
        if (pojo == null) {
            entity.setCode(ResponseCode.ERROR.getValue());
            entity.setMsg("请先添加文章");
        }

        return entity;
    }

}
