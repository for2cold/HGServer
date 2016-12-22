package com.kazyle.hugohelper.server.config.configuration;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Kazyle on 2016/8/23.
 */
@Configuration
public class FastJsonConfiguration {

    @Bean
    public HttpMessageConverters customConverters() {
        Collection<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converters.add(converter);
        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(true, converters);
        return httpMessageConverters;
    }
}
