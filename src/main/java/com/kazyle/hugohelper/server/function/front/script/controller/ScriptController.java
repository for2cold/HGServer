package com.kazyle.hugohelper.server.function.front.script.controller;

import com.kazyle.hugohelper.server.config.annotation.CurrentUser;
import com.kazyle.hugohelper.server.config.domain.data.*;
import com.kazyle.hugohelper.server.config.exception.FileException;
import com.kazyle.hugohelper.server.config.helper.FileHelper;
import com.kazyle.hugohelper.server.function.config.SysConfig;
import com.kazyle.hugohelper.server.function.core.script.entity.Script;
import com.kazyle.hugohelper.server.function.core.script.service.ScriptService;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Created by Kazyle on 2016/8/24.
 */
@Controller
@RequestMapping("/front/script")
public class ScriptController extends BaseFrontController<Script> {

    @Resource
    private ScriptService scriptService;
    @Resource
    private FileHelper fileHelper;
    @Resource
    private SysConfig sysConfig;

    @RequestMapping({"", "/", "index"})
    public String index(@CurrentUser User user, PageRequest pageRequest, Model model) {
        Page<Script> page = scriptService.queryList(user, pageRequest);
        model.addAttribute("page", page);
        return viewName("index");
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(RedirectAttributes redirectAttributes) {
        return viewName("add");
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@CurrentUser User user, Script pojo, @RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            try {
                String path = fileHelper.upload(file, sysConfig.getScriptFolder());
                pojo.setPath(path);
            } catch (FileException e) {
                redirectAttributes.addFlashAttribute(Constants.ERROR_MSG, e.getMessage());
                return add(redirectAttributes);
            }
        }
        scriptService.save(user, pojo);
        return redirectToUrl("/front/script/index");
    }

    @RequestMapping("{id}/remove")
    @ResponseBody
    public ResponseEntity remove(@PathVariable("id") Long id) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "脚本删除成功！");
        scriptService.remove(id);
        return entity;
    }
}
