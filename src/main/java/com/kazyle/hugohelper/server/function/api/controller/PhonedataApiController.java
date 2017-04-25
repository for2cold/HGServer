package com.kazyle.hugohelper.server.function.api.controller;

import com.kazyle.hugohelper.server.config.domain.data.BaseController;
import com.kazyle.hugohelper.server.config.domain.data.ResponseCode;
import com.kazyle.hugohelper.server.config.domain.data.ResponseEntity;
import com.kazyle.hugohelper.server.config.exception.FileException;
import com.kazyle.hugohelper.server.config.helper.FileHelper;
import com.kazyle.hugohelper.server.function.config.SysConfig;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.apache.commons.codec.binary.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * Created by Kazyle on 2016/8/26.
 */
@Controller
@RequestMapping("/api/phonedata")
public class PhonedataApiController extends BaseController<User> {

    private static final int size = 1;
    int counter = 0;

    @Resource
    private SysConfig sysConfig;
    @Resource
    private FileHelper fileHelper;

    @RequestMapping("get")
    @ResponseBody
    public ResponseEntity get(String userId, String signature) {
        ResponseEntity entity = new ResponseEntity(ResponseCode.SUCCESS.getValue(), "获取手机数据成功");

        if (!"2".equals(userId) && !sysConfig.getSignature().equals(signature)) {
            entity.setCode(ResponseCode.ERROR.getValue());
            entity.setMsg("手机数据用完了~");
            return entity;
        }

        File root = new File(sysConfig.getPath() + "/" + sysConfig.getPhoneDataFolder() + userId + "/");
        File[] files = root.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (counter != size) {
                    counter++;
                    return true;
                } else {
                    return false;
                }
            }
        });
        counter = 0;
        if (files.length == 0) {
            entity.setCode(ResponseCode.ERROR.getValue());
            entity.setMsg("手机数据用完了~");
            return entity;
        }
        Set<File> sendFiles = new HashSet<>();
        sendFiles.add(files[0]);
        try {
            String url = fileHelper.zip(sendFiles, sysConfig.getPhoneDataTempFolder(), true);
            entity.setObj(url);
        } catch (FileException e) {
            entity.setCode(ResponseCode.ERROR.getValue());
            entity.setMsg("压缩资源失败");
        }
        return entity;
    }

    @RequestMapping("getString/{userId}")
    public void getString(@PathVariable("userId") String userId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = new String(org.apache.commons.codec.binary.Base64.decodeBase64(userId));
        File root = new File(sysConfig.getPath() + "/" + sysConfig.getPhoneDataFolder() + id + "/");
        File[] files = root.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (counter != size) {
                    counter++;
                    return true;
                } else {
                    return false;
                }
            }
        });
        counter = 0;
        String apk008 = request.getHeader("apk008");
        response.setHeader("Set-Cookie", "JSESSIONID=" + request.getSession().getId() + ";");
        response.setHeader("apk008", apk008);
        response.setHeader("Key008", apk008);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if (files.length == 0) {
            out.print("");
            out.flush();
        } else {
            BufferedReader reader = null;
            File file = files[0];
            try {
                reader = new BufferedReader(new FileReader(file));
                StringBuilder json = new StringBuilder("");
                String str = null;
                while ((str = reader.readLine()) != null) {
                    json.append(str);
                }
                out.print(json.toString());
                out.flush();
            } catch (FileNotFoundException e) {
                out.print("");
                out.flush();
            } catch (IOException e) {
                out.print("");
                out.flush();
            } finally {
                if (null != reader) {
                    reader.close();
                }
                file.delete();
            }
        }
    }
}
