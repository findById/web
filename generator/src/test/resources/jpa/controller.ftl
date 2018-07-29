package ${packageName}.${moduleName}.web.controller;

import com.cn.web.core.platform.web.ResponseBuilder;
import ${packageName}.${moduleName}.domain.${ClassName};
import ${packageName}.${moduleName}.service.${ClassName}Service;
import ${packageName}.${moduleName}.web.vo.${ClassName}Bean;
import ${packageName}.${moduleName}.web.request.${ClassName}Req;
import ${packageName}.${moduleName}.web.response.${ClassName}Resp;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${author!""} on ${date!""}.
 */
@RestController
@RequestMapping(value = "${className}")
public class ${ClassName}Controller {

    @Resource
    ${ClassName}Service ${className}Service;

    // @PermissionRequired(value = "${moduleName}:${className}:save")
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public String save(@RequestBody ${ClassName}Req req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();

        if (req == null) {
            builder.statusCode(201);
            builder.message("Request body must not be null.");
            return builder.buildJSONString();
        }

        ${ClassName} ${className} = new ${ClassName}();
        // TODO copy req

        ${className}Service.save(${className});

        ${ClassName}Resp resp = new ${ClassName}Resp();
        BeanUtils.copyProperties(${className}, resp);

        builder.statusCode(200);
        builder.message("success");
        builder.result(resp);
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "${moduleName}:${className}:update")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String update(@RequestBody ${ClassName}Req req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();

        if (req == null || req.getId() == null) {
            builder.statusCode(201);
            builder.message("${ClassName} not exists");
            return builder.buildJSONString();
        }

        ${ClassName} ${className} = ${className}Service.get(req.getId());
        if (${className} == null) {
            builder.statusCode(201);
            builder.message("${ClassName} not exists");
            return builder.buildJSONString();
        }
        // TODO copy value

        ${className}Service.update(${className});

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "${moduleName}:${className}:delete")
    @RequestMapping(value = "delete", method = {RequestMethod.POST})
    public String delete(@RequestBody String[] ids) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();

        ${className}Service.delete(ids);

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "${moduleName}:${className}:view")
    @RequestMapping(value = "list", method = {RequestMethod.GET})
    public String list(@RequestParam(name = "page", defaultValue = "1") @Min(1) int page,
                       @RequestParam(name = "size", defaultValue = "20") @Max(50) int size,
                       @RequestParam(name = "sort", required = false) String sortBy,
                       @RequestParam(name = "order", required = false) String orderBy,
                       @RequestParam(name = "keywords", required = false) String keywords) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();

        Page<${ClassName}> list;
        if (!StringUtils.isEmpty(keywords)) {
            list = ${className}Service.search(keywords, PageRequest.of(page - 1, size));
        } else {
            list = ${className}Service.list(page - 1, size);
        }

        List<${ClassName}Resp> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (${ClassName} ${className} : list.getContent()) {
                ${ClassName}Resp item = new ${ClassName}Resp();
                BeanUtils.copyProperties(${className}, item);
                beanList.add(item);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        builder.statusCode(200);
        builder.message("success");
        builder.result(result);
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "${moduleName}:${className}:view")
    @RequestMapping(value = "view", method = {RequestMethod.GET})
    public String view() {
        return "unimplemented";
    }
}
