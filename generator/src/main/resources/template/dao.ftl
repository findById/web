package ${packageName}.${moduleName}.dao;

import ${packageName}.${moduleName}.domain.${ClassName};

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${author!""} on ${date!""}.
 */
public interface ${ClassName}Dao {

    ${ClassName} get(Serializable id);

    ${ClassName} save(${ClassName} ${className});

    ${ClassName} update(${ClassName} ${className});

    void delete(Serializable id);

    List<${ClassName}> list();

    Page<${ClassName}> list(int offset, int size);

}
