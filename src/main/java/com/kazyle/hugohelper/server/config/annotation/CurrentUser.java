package com.kazyle.hugohelper.server.config.annotation;

import com.kazyle.hugohelper.server.config.domain.data.Constants;

import java.lang.annotation.*;

/**
 * Created by Kazyle on 2016/8/23.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

    String value() default Constants.CURRENT_USER;
}
