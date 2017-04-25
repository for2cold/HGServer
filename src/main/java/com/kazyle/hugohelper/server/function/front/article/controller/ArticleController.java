package com.kazyle.hugohelper.server.function.front.article.controller;

import com.kazyle.hugohelper.server.config.annotation.CurrentUser;
import com.kazyle.hugohelper.server.config.domain.data.BaseFrontController;
import com.kazyle.hugohelper.server.config.domain.data.ResponseCode;
import com.kazyle.hugohelper.server.config.domain.data.ResponseEntity;
import com.kazyle.hugohelper.server.function.core.article.entity.Article;
import com.kazyle.hugohelper.server.function.core.article.service.ArticleService;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>HGServer</p>
 * <p>
 * <b>ArticleController</b> is
 * </p>
 *
 * @version 4.0
 * @since 4.0
 * Created by Kazyle on 2017/4/25.
 */
@Controller
@RequestMapping("/front/article")
public class ArticleController extends BaseFrontController<Article> {

    @Resource
    private ArticleService articleService;

    @RequestMapping("/index")
    public String index(@CurrentUser User user, Integer type, Model model) {

        List<Article> articles = articleService.queryList(user.getId(), type);
        model.addAttribute("articles", articles);
        model.addAttribute("type", type);
        return viewName("index");
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Integer type, Model model) {
        model.addAttribute("type", type);
        return viewName("add");
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@CurrentUser User user, Article pojo) {

        Integer type = pojo.getType();

        articleService.save(user, pojo);

        String url = "/front/article/index";
        if (type != null) {
            url += "?type=" + type;
        }
        return redirectToUrl(url);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity remove(Long[] ids) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "文章删除成功！");
        articleService.remove(ids);
        return entity;
    }
}
