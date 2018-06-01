package ${packageName}.${moduleName}.service;

import ${packageName}.${moduleName}.domain.${ClassName};

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${author!""} on ${date!""}.
 */
public interface ${ClassName}Service {

    ${ClassName} get(Serializable id);

    ${ClassName} save(${ClassName} user);

    ${ClassName} update(${ClassName} user);

    void delete(Serializable id);

    List<${ClassName}> list();

    Page<${ClassName}> list(int offset, int size);

}
