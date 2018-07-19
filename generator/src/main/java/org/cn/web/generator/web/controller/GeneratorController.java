package org.cn.web.generator.web.controller;

import com.alibaba.fastjson.JSON;
import org.cn.web.generator.domain.Module;
import org.cn.web.generator.util.GenUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping(value = "generator")
public class GeneratorController {

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void generator(@RequestBody String body, HttpServletResponse response) throws IOException {
        if (body == null || body.isEmpty()) {
            response.setStatus(400);
            return;
        }
        Module module = JSON.parseObject(body, Module.class);

        byte[] data = GenUtils.genToZipFile(module);
        if (data == null) {
            response.setStatus(500);
            return;
        }

        String fileName = String.format("source-%1$s-%2$s.zip", module.getModuleName(), module.getClassName());
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        OutputStream os = response.getOutputStream();
        os.write(data);
        os.flush();
        os.close();
    }

}
