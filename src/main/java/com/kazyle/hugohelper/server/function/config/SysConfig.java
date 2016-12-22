package com.kazyle.hugohelper.server.function.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Kazyle on 2016/8/24.
 */
@ConfigurationProperties(prefix = "sys", locations = "classpath:sysconfig.properties")
public class SysConfig {

    private String accessUrl;

    private String path;

    private String phoneDataFolder;

    private String phoneDataTempFolder;

    private String scriptFolder;

    private String apkFolder;

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPhoneDataFolder() {
        return phoneDataFolder;
    }

    public void setPhoneDataFolder(String phoneDataFolder) {
        this.phoneDataFolder = phoneDataFolder;
    }

    public String getScriptFolder() {
        return scriptFolder;
    }

    public void setScriptFolder(String scriptFolder) {
        this.scriptFolder = scriptFolder;
    }

    public String getPhoneDataTempFolder() {
        return phoneDataTempFolder;
    }

    public void setPhoneDataTempFolder(String phoneDataTempFolder) {
        this.phoneDataTempFolder = phoneDataTempFolder;
    }

    public String getApkFolder() {
        return apkFolder;
    }

    public void setApkFolder(String apkFolder) {
        this.apkFolder = apkFolder;
    }
}
