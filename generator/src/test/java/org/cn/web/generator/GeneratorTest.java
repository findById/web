package org.cn.web.generator;

import org.cn.web.generator.domain.Module;
import org.cn.web.generator.service.GeneratorService;
import org.cn.web.generator.service.impl.GeneratorServiceImpl;
import org.junit.Test;

import java.io.File;

public class GeneratorTest {

    @Test
    public void test() {
        String projectRootDir = "/home/work/dev/java/web/web-admin";
        Module module = new Module();
        module.setTemplateId("jpa");
        module.setBasePackage("org.cn.web");
        module.setModuleName("Admin");
        module.setClassName("Admin");

        GeneratorService generatorService = new GeneratorServiceImpl();
        generatorService.generate(new File(projectRootDir), module);
    }

}
