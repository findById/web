package com.cn.web.core.platform;

import com.cn.web.core.platform.monitor.TrackInterceptor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractServletConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(messageConverter());
    }

    public HttpMessageConverter<String> messageConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        converter.setWriteAcceptCharset(false);
        List<MediaType> list = new ArrayList<>();
        list.add(new MediaType("application", "json", Charset.forName("UTF-8")));
        list.add(new MediaType("application", "*+json", Charset.forName("UTF-8")));
        list.add(new MediaType("application", "xml", Charset.forName("UTF-8")));
        list.add(new MediaType("application", "*+xml", Charset.forName("UTF-8")));
        list.add(new MediaType("text", "xml", Charset.forName("UTF-8")));
        list.add(new MediaType("text", "html", Charset.forName("UTF-8")));
        // list.add(new MediaType("*", "*", Charset.forName("UTF-8")));
        converter.setSupportedMediaTypes(list);
        return converter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TrackInterceptor());
    }
}
