package com.kazyle.hugohelper.server.config.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.stereotype.Service;

/**
 * Created by Kazyle on 2016/8/23.
 */
@Service
public class JSONHelper {

    public String toJSONString(Object obj) {
        return JSON.toJSONString(obj,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.BrowserCompatible);
    }
}
