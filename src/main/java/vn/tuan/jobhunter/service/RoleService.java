package vn.tuan.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.domain.Role;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;

@Service
public interface RoleService {
    public Role createRole(Role role);
    public Role updateRole(Role role);
    public ResultPaginationDTO getAllRoles(Specification<Role> spec, Pageable pageable);
    public void deleteRole(int id);
}
