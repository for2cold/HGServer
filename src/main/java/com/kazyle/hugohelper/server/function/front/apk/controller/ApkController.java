package com.kazyle.hugohelper.server.function.front.apk.controller;

import com.kazyle.hugohelper.server.config.annotation.CurrentUser;
import com.kazyle.hugohelper.server.config.domain.data.BaseFrontController;
import com.kazyle.hugohelper.server.config.domain.data.Constants;
import com.kazyle.hugohelper.server.config.domain.data.ResponseCode;
import com.kazyle.hugohelper.server.config.domain.data.ResponseEntity;
import com.kazyle.hugohelper.server.config.exception.FileException;
import com.kazyle.hugohelper.server.config.helper.FileHelper;
import com.kazyle.hugohelper.server.function.config.SysConfig;
import com.kazyle.hugohelper.server.function.core.apk.entity.Apk;
import com.kazyle.hugohelper.server.function.core.apk.service.ApkService;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Kazyle on 2016/9/8.
 */
@Controller
@RequestMapping("/front/apk")
public class ApkController extends BaseFrontController<Apk> {

    @Resource
    private ApkService apkService;
    @Resource
    private SysConfig sysConfig;
    @Resource
    private FileHelper fileHelper;

    @RequestMapping("/index")
    public String index(@CurrentUser User user, Model model) {

        List<Apk> apks = apkService.queryList(user.getId());
        model.addAttribute("apks", apks);
        return viewName("index");
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        return viewName("add");
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@CurrentUser User user, String name, @RequestParam("file")MultipartFile file, Model model) {
        if (!file.isEmpty()) {
            try {
                String path = fileHelper.upload(file, sysConfig.getApkFolder());
                if (StringUtils.isEmpty(name)) {
                    name = file.getOriginalFilename();
                }
                apkService.save(user.getId(), path, name);
                removeHistoryZip(user);
            } catch (FileException e) {
                model.addAttribute(Constants.ERROR_MSG, e.getMessage());
                return add(model);
            }
        }
        return redirectToUrl("/front/apk/index");
    }

    @RequestMapping("{id}/remove")
    @ResponseBody
    public ResponseEntity remove(@PathVariable("id") Long id, @CurrentUser User user) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "APK删除成功！");
        apkService.remove(id);
        removeHistoryZip(user);
        return entity;
    }

    private void removeHistoryZip(User user) {
        String target = sysConfig.getPath() + "/" + sysConfig.getApkFolder();
        String filename = user.getId() + "_initapk.zip";
        fileHelper.removeFile(target + filename);
    }
}
