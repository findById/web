package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.web.ResponseBuilder;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class UploadController {

    @RequestMapping(value = "upload", method = {RequestMethod.POST})
    public String upload(HttpServletRequest request) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        factory.setSizeThreshold(10 << 20 /* 10M */);
//        ServletFileUpload upload = new ServletFileUpload(factory);
//        upload.setFileSizeMax(20 << 20 /* 20M */);
//        upload.setSizeMax(100 << 20 /* 100M */);
//        upload.setHeaderEncoding("UTF-8");
//        if (!ServletFileUpload.isMultipartContent(request)) {
//            builder.statusCode(201);
//            builder.message("failed");
//            return builder.buildJSONString();
//        }
//        try {
//            List<FileItem> fileItemList = upload.parseRequest(request);
//            for (FileItem item : fileItemList) {
//                if (item.isFormField()) {
//                    System.out.println(item.getFieldName() + " = " + item.getString("UTF-8"));
//                    continue;
//                }
//                String filename = item.getName();
//                System.out.println(filename);
//            }
//        } catch (FileUploadException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        if (request != null && ServletFileUpload.isMultipartContent(request)) {
            MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipart.getFileMap();
            if (fileMap.isEmpty()) {
                builder.statusCode(201);
                builder.message("failed");
                return builder.buildJSONString();
            }
            for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                MultipartFile file = entry.getValue();
                String filename = file.getOriginalFilename();
                System.out.println(filename);
                // TODO check file and read
            }
        }

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

}
