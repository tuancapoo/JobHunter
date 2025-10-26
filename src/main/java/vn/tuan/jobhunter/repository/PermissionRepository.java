package vn.tuan.jobhunter.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.tuan.jobhunter.domain.Permission;
import vn.tuan.jobhunter.domain.Skill;

import java.util.Collection;
import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    Boolean existsByModuleAndApiPathAndMethod(String module, String apiPath, String method);
    List<Permission> findByIdIn(Collection<Long> ids);

    boolean existsByName(@NotBlank(message="name khong de trong") String name);
}
