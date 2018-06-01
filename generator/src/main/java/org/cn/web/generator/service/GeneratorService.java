package org.cn.web.generator.service;

import org.cn.web.generator.domain.Module;

import java.io.File;

public interface GeneratorService {

    boolean generate(File root, Module module);

}
