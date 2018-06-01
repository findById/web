package com.cn.web.core.platform;

import com.cn.web.core.platform.context.Messages;
import com.cn.web.core.platform.monitor.MonitorManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

@Configuration
@PropertySource(value = {"classpath*:*properties"}, ignoreResourceNotFound = true)
public class DefaultAppConfig {

    @Bean
    public Messages messages() throws IOException {
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:messages/*.properties");
        Messages messages = new Messages();
        String[] names = new String[resources.length];
        for (int i = 0, resLen = resources.length; i < resLen; i++) {
            Resource resource = resources[i];
            String filename = resource.getFilename();
            names[i] = "messages/" + filename.substring(0, filename.indexOf("."));
        }
        messages.setBasenames(names);
        return messages;
    }

    @Bean
    MonitorManager monitorManager() {
        return new MonitorManager();
    }

}
