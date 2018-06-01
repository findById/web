package ${packageName}.${moduleName}.repository;

import ${packageName}.${moduleName}.domain.${ClassName};

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ${author!""} on ${date!""}.
 */
@Repository("${className}Repository")
public interface ${ClassName}Repository extends JpaRepository<${ClassName}, String> {
}
