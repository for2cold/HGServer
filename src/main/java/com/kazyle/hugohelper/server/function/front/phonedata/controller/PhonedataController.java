package com.kazyle.hugohelper.server.function.front.phonedata.controller;

import com.kazyle.hugohelper.server.config.annotation.CurrentUser;
import com.kazyle.hugohelper.server.config.domain.data.BaseFrontController;
import com.kazyle.hugohelper.server.config.domain.data.Constants;
import com.kazyle.hugohelper.server.config.exception.FileException;
import com.kazyle.hugohelper.server.config.helper.FileHelper;
import com.kazyle.hugohelper.server.function.config.SysConfig;
import com.kazyle.hugohelper.server.function.core.script.entity.Script;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileFilter;

/**
 * Created by Kazyle on 2016/8/24.
 */
@Controller
@RequestMapping("/front/phonedata")
public class PhonedataController extends BaseFrontController<User> {

    @Resource
    private SysConfig sysConfig;
    @Resource
    private FileHelper fileHelper;

    @RequestMapping({"", "/", "index"})
    public String index(@CurrentUser User user, Model model) {
        File root = new File(sysConfig.getPath() + "/" + sysConfig.getPhoneDataFolder() + user.getId() + "/");
        File[] files = root.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        model.addAttribute("phoneDataSize", files.length);
        return viewName("index");
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        return viewName("add");
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@CurrentUser User user, @RequestParam("file")MultipartFile file, Model model) {
        if (!file.isEmpty()) {
            try {
                String source = fileHelper.upload(file, sysConfig.getPhoneDataTempFolder());
                boolean result = fileHelper.unzip(source, sysConfig.getPhoneDataFolder() + user.getId() + "/");
                if (!result) {
                    model.addAttribute(Constants.ERROR_MSG, "资源解压失败");
                    return add(model);
                }
            } catch (FileException e) {
                model.addAttribute(Constants.ERROR_MSG, e.getMessage());
                return add(model);
            }
        }
        return redirectToUrl("/front/phonedata/index");
    }
}
