package com.cn.web.core.platform.web;

import com.cn.web.core.platform.context.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.inject.Inject;
import java.util.Locale;

public class DefaultController {

    @Inject
    private Messages messages;

    protected String getMessage(String key, Object... args) {
        LocaleContextHolder.setLocale(Locale.getDefault());
        Locale locale = LocaleContextHolder.getLocale();
        return messages.getMessage(key, args, locale);
    }
}
