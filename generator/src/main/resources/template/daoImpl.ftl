package ${packageName}.${moduleName}.dao.impl;

import ${packageName}.${moduleName}.dao.${ClassName}Dao;
import ${packageName}.${moduleName}.domain.${ClassName};

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ${author!""} on ${date!""}.
 */
@Repository(value = "${className}Dao")
public class ${ClassName}DaoImpl implements ${ClassName}Dao {

    @Override
    public ${ClassName} get(Serializable id) {
        return null;
    }

    @Override
    public ${ClassName} save(${ClassName} user) {
        return null;
    }

    @Override
    public ${ClassName} update(${ClassName} user) {
        return null;
    }

    @Override
    public void delete(Serializable id) {
    }

    @Override
    public List<${ClassName}> list() {
        return null;
    }

    @Override
    public Page<${ClassName}> list(int offset, int size) {
        return null;
    }

}
