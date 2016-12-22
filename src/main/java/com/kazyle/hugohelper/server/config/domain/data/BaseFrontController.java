package com.kazyle.hugohelper.server.config.domain.data;

import com.kazyle.hugohelper.server.config.util.ReflectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;

/**
 * Created by Kazyle on 2016/8/23.
 */
public abstract class BaseFrontController<T extends Serializable> extends BaseController<T> {

    protected final Class<T> entityClass;
    private String viewPrefix;

    protected BaseFrontController() {
        this.entityClass = ReflectUtils.findParameterizedType(getClass(), 0);
        if (this.entityClass != null) {
            setViewPrefix(defaultViewPrefix());
        }
    }

    /**
     * 获取视图名称，即：prefixViewName + "/" + suffixName
     *
     * @param suffixName
     * @return
     */
    protected String viewName(String suffixName) {
        if (!suffixName.startsWith("/")) {
            suffixName = "/" + suffixName;
        }
        return getViewPrefix() + suffixName;
    }

    /**
     * 重定向URL
     *
     * @param backURL
     * @return
     */
    protected String redirectToUrl(String backURL) {
        if (StringUtils.isEmpty(backURL)) {
            backURL = getViewPrefix();
        }
        if (!backURL.startsWith("/") && !backURL.startsWith("http") && !backURL.startsWith("https") ) {
            backURL = "/" + backURL;
        }
        return "redirect:" + backURL;
    }

    /**
     * 当前模块 视图的前缀
     * 默认
     * 1、获取当前类头上的@RequestMapping中的value作为前缀
     * 2、如果没有就使用当前模型的简单类名
     * @param viewPrefix
     */
    public void setViewPrefix(String viewPrefix) {
        if (viewPrefix.startsWith("/")) {
            viewPrefix = viewPrefix.substring(1);
        }
        this.viewPrefix = viewPrefix;
    }

    protected String defaultViewPrefix() {
        String currentViewPrefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (null != requestMapping && requestMapping.value().length > 0) {
            currentViewPrefix = requestMapping.value()[0];
        }
        if (StringUtils.isEmpty(currentViewPrefix)) {
            currentViewPrefix = this.entityClass.getSimpleName();
        }
        return currentViewPrefix;
    }

    public String getViewPrefix() {
        return viewPrefix;
    }
}
