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
        module.setBasePackage("com.cn.web");
        module.setModuleName("test");
        module.setClassName("test");

        module.setColumns(new ArrayList<>());
        for (int i = 0; i < 3; i++) {
            ModuleColumn column = new ModuleColumn();
            column.setName("column" + i);
            column.setType("String");
            column.setDesc("column comment" + i);
            module.getColumns().add(column);
        }
        GenUtils.generator(new File(projectRootDir), module);
    }

}
