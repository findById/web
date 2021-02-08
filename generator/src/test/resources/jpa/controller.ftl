package ${packageName}.${moduleName}.web.controller;

import com.cn.web.core.platform.web.Result;
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
        if (req == null) {
            return Result.error(201, "Request body must not be null.").toJSONString();
        }

        ${ClassName} ${className} = new ${ClassName}();
        // TODO copy req

        ${className}Service.save(${className});

        ${ClassName}Resp resp = new ${ClassName}Resp();
        BeanUtils.copyProperties(${className}, resp);

        return Result.success(resp).toJSONString();
    }

    // @PermissionRequired(value = "${moduleName}:${className}:update")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String update(@RequestBody ${ClassName}Req req) {
        if (req == null || req.getId() == null) {
            return Result.error(201, "${ClassName} not exists").toJSONString();
        }

        ${ClassName} ${className} = ${className}Service.get(req.getId());
        if (${className} == null) {
            return Result.error(201, "${ClassName} not exists").toJSONString();
        }
        // TODO copy value

        ${className}Service.update(${className});

        return Result.success().toJSONString();
    }

    // @PermissionRequired(value = "${moduleName}:${className}:delete")
    @RequestMapping(value = "delete", method = {RequestMethod.POST})
    public String delete(@RequestBody String[] ids) {

        ${className}Service.delete(ids);

        return Result.success().toJSONString();
    }

    // @PermissionRequired(value = "${moduleName}:${className}:view")
    @RequestMapping(value = "list", method = {RequestMethod.GET})
    public String list(@RequestParam(name = "page", defaultValue = "1") @Min(1) int page,
                       @RequestParam(name = "size", defaultValue = "20") @Max(50) int size,
                       @RequestParam(name = "sort", required = false) String sortBy,
                       @RequestParam(name = "order", required = false) String orderBy,
                       @RequestParam(name = "keywords", required = false) String keywords) {

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

        return Result.success(result).toJSONString();
    }

    // @PermissionRequired(value = "${moduleName}:${className}:view")
    @RequestMapping(value = "view", method = {RequestMethod.GET})
    public String view() {
        return "unimplemented";
    }
}
