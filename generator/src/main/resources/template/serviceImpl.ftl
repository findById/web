package ${packageName}.${moduleName}.service.impl;

import ${packageName}.${moduleName}.repository.${ClassName}Repository;
import ${packageName}.${moduleName}.domain.${ClassName};
import ${packageName}.${moduleName}.service.${ClassName}Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<${ClassName}> search(String keyword, Pageable page) {

        System.out.println(keyword);

        return ${className}Repository.findAll(page);
    }

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
    public void delete(Serializable id) {
        ${className}Repository.deleteById(String.valueOf(id));
    }

    @Override
    public List<${ClassName}> list() {
        return ${className}Repository.findAll();
    }

    @Override
    public Page<${ClassName}> list(int offset, int size) {
        int page = offset;
        return ${className}Repository.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

}
