package ${packageName}.${moduleName}.service.impl;

import ${packageName}.${moduleName}.repository.${ClassName}Repository;
import ${packageName}.${moduleName}.domain.${ClassName};
import ${packageName}.${moduleName}.service.${ClassName}Service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Created by ${author!""} on ${date!""}.
 */
@Service(value = "${className}Service")
public class ${ClassName}ServiceImpl implements ${ClassName}Service {

    @Resource
    ${ClassName}Repository ${className}Repository;

    @Override
    public ${ClassName} get(Serializable id) {
        Optional<${ClassName}> optional = ${className}Repository.findById(String.valueOf(id));
        return optional.orElse(null);
    }

    @Override
    @Transactional
    public ${ClassName} save(${ClassName} ${className}) {

        ${className}Repository.save(${className});

        return ${className};
    }

    @Override
    @Transactional
    public ${ClassName} update(${ClassName} ${className}) {
        // ${className}Dao.update(${className});
        ${className}Repository.save(${className});
        return ${className};
    }

    @Override
    @Transactional
    public void delete(Serializable[] ids) {
        for (Serializable id : ids) {
            ${className}Repository.deleteById(String.valueOf(id));
        }
    }

    @Override
    @Transactional
    public void deleteByLogic(Serializable[] ids) {
        for (Serializable id : ids) {
            ${ClassName} ${className} = get(id);
            if (${className} != null) {
                ${className}.setDelFlg(1);
                ${className}Repository.save(${className});
            }
        }
    }

    @Override
    public List<${ClassName}> list() {
        ${ClassName} ${className} = new ${ClassName}();
        ${className}.setDelFlg(0);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("delFlg", ExampleMatcher.GenericPropertyMatchers.regex())
                .withIgnorePaths("updateTime", "state");
        Example<${ClassName}> example = Example.of(${className}, matcher);
        return ${className}Repository.findAll(example);
    }

    @Override
    public Page<${ClassName}> list(int offset, int size) {
        int page = offset;
        return ${className}Repository.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    @Override
    public Page<${ClassName}> search(String keywords, Pageable page) {
        ${ClassName} ${className} = new ${ClassName}();
        // TODO set keywords
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("fieldName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("updateTime", "delFlg");
        Example<${ClassName}> example = Example.of(${className}, matcher);
        return ${className}Repository.findAll(example, page);
    }
}
