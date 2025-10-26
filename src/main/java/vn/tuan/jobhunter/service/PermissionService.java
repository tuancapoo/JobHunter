package vn.tuan.jobhunter.service;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.domain.Permission;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;

@Service
public interface PermissionService {
    public Permission updatePermission(Permission permission);
    public Permission createPermission(Permission permission);
    public ResultPaginationDTO getAllPermissions(Specification<Permission> specification, Pageable pageable);
    public void deletePermission(Long id);
}
