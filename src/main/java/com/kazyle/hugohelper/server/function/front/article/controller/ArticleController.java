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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    public String index(@CurrentUser User user, Integer type, String platform, Model model) {
        List<String> platforms = articleService.queryPlatforms(user.getId());
        model.addAttribute("platforms", platforms);
        model.addAttribute("type", type);
        model.addAttribute("platform", platform);
        return viewName("index");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity list(@CurrentUser User user, Integer type, String platform) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "success");
        List<Article> articles = articleService.queryList(user.getId(), type, platform);
        entity.setObj(articles);
        return entity;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@CurrentUser User user, Integer type, Model model) {
        model.addAttribute("type", type);
        List<String> platforms = articleService.queryPlatforms(user.getId());
        model.addAttribute("platforms", platforms);
        return viewName("add");
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@CurrentUser User user, Article pojo) throws UnsupportedEncodingException {

        Integer type = pojo.getType();

        articleService.save(user, pojo);

        String url = "/front/article/add";
        if (type != null) {
            url += "?type=" + type + "&platform=" + URLEncoder.encode(pojo.getPlatform(), "utf-8");
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

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity update(Long[] ids) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "状态更新成功！");
        articleService.update(ids);
        return entity;
    }

    @RequestMapping(value = "{id}/update", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model, @CurrentUser User user) {
        List<String> platforms = articleService.queryPlatforms(user.getId());
        model.addAttribute("platforms", platforms);
        Article pojo = articleService.findOne(id);
        model.addAttribute("pojo", pojo);
        return viewName("edit");
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String edit(@PathVariable("id") Long id, Article pojo) throws UnsupportedEncodingException {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "修改成功！");
        articleService.update(id, pojo);
        String url = "/front/article/index";
        if (pojo.getType() != null) {
            url += "?type=" + pojo.getType() + "&platform=" + URLEncoder.encode(pojo.getPlatform(), "utf-8");
        }
        return redirectToUrl(url);
    }
}
