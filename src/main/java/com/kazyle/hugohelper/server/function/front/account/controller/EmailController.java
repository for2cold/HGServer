package com.kazyle.hugohelper.server.function.front.account.controller;

import com.kazyle.hugohelper.server.config.annotation.CurrentUser;
import com.kazyle.hugohelper.server.config.domain.data.BaseFrontController;
import com.kazyle.hugohelper.server.config.domain.data.Constants;
import com.kazyle.hugohelper.server.config.domain.data.ResponseCode;
import com.kazyle.hugohelper.server.config.domain.data.ResponseEntity;
import com.kazyle.hugohelper.server.config.exception.FileException;
import com.kazyle.hugohelper.server.config.helper.FileHelper;
import com.kazyle.hugohelper.server.function.config.SysConfig;
import com.kazyle.hugohelper.server.function.core.account.entity.Email;
import com.kazyle.hugohelper.server.function.core.account.service.AccountService;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>HGServer</p>
 * <p>
 * <b>EmailController</b> is
 * </p>
 *
 * @version 1.0
 * @since 1.0
 * Created by Kazyle on 2016/12/30 15:42
 */
@Controller
@RequestMapping("/front/email")
public class EmailController extends BaseFrontController<Email> {

    @Resource
    private FileHelper fileHelper;
    @Resource
    private SysConfig sysConfig;
    @Resource
    private AccountService accountService;

    @RequestMapping(value = "/index")
    public String index(@CurrentUser User user, Model model) {
        List<Email> emails = accountService.queryEmailList(user.getId());
        model.addAttribute("emails", emails);
        return viewName("index");
    }

    @RequestMapping(value = "importEmail", method = RequestMethod.POST)
    public String importEmail(@RequestParam("file")MultipartFile file, @CurrentUser User user, RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            try {
                String path = fileHelper.upload(file, sysConfig.getScriptFolder());
            } catch (FileException e) {
                redirectAttributes.addFlashAttribute(Constants.ERROR_MSG, e.getMessage());
            }
        }
        return redirectToUrl("/front/email/index");
    }
}
