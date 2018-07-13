package org.cn.web.generator;

import org.cn.web.generator.domain.Module;
import org.cn.web.generator.domain.ModuleColumn;
import org.cn.web.generator.util.GenUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class GeneratorTest {

    @Test
    public void test() {
        String projectRootDir = "/home/work/dev/java/web/generator";
        Module module = new Module();
        module.setTemplateId("jpa");
        module.setDesc("user");
        module.setBasePackage("org.cn.web");
        module.setModuleName("sys");
        module.setClassName("user");
        module.setColumns(new ArrayList<>());
        module.getColumns().add(new ModuleColumn("username", "String", "username"));
        module.getColumns().add(new ModuleColumn("password", "String", "password"));
        GenUtils.generator(new File(projectRootDir), module);
    }

}
