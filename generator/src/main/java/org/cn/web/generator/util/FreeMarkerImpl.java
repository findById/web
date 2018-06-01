/*
 * Copyright [2016-2016] The Cn Open Source Project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.cn.web.generator.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class FreeMarkerImpl {

    private Configuration cfg = new Configuration(Configuration.getVersion());

    public FreeMarkerImpl() {
    }

    public FreeMarkerImpl(String template) {
        setTemplatePath(template);
    }

    public FreeMarkerImpl(ClassLoader classLoader, String template) {
        setTemplatePath(classLoader, template);
    }

    public void setTemplatePath(String template) {
        try {
            cfg.setDefaultEncoding("UTF-8");
            cfg.setDirectoryForTemplateLoading(new File(template));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTemplatePath(ClassLoader classLoader, String template) {
        cfg.setDefaultEncoding("UTF-8");
        cfg.setClassLoaderForTemplateLoading(classLoader, template);
    }

    public String renderTemplate(String template, Object model) {
        try {
            Template temp = cfg.getTemplate(template);

            StringWriter result = new StringWriter();
            temp.process(model, result);
            return result.toString();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

}
