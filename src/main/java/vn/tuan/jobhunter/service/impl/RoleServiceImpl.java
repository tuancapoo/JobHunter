package vn.tuan.jobhunter.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.controller.errors.CustomException;
import vn.tuan.jobhunter.domain.*;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.repository.CompanyRepository;
import vn.tuan.jobhunter.repository.PermissionRepository;
import vn.tuan.jobhunter.repository.RoleRepository;
import vn.tuan.jobhunter.service.RoleService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }
    public Role createRole(Role role) {
        if (roleRepository.existsById(role.getId())) {
            throw new CustomException("Role already exists");
        }
        if (roleRepository.existsByName(role.getName())) {
            throw new CustomException("Role name already exists");
        }
        if (role.getPermissions()!=null && role.getPermissions().size()>0) {
            List<Long> reqSkills=role.getPermissions()
                    .stream()
                    .map(x->Long.valueOf(x.getId()))
                    .collect(Collectors.toList());
            List<Permission> permissions=permissionRepository.findByIdIn(reqSkills);
            role.setPermissions(permissions);
        }
        return roleRepository.save(role);
    }
    public Role updateRole(Role updateRole) {
        if (!roleRepository.existsById(updateRole.getId())) {
            throw new CustomException("Role does not exist");
        }

        if (updateRole.getPermissions()!=null && updateRole.getPermissions().size()>0) {
            List<Long> reqSkills=updateRole.getPermissions()
                    .stream()
                    .map(x->Long.valueOf(x.getId()))
                    .collect(Collectors.toList());
            List<Permission> permissions=permissionRepository.findByIdIn(reqSkills);
            updateRole.setPermissions(permissions);
        }
        Role updated=roleRepository.findById((updateRole.getId()))
                .map(role -> {
                    if ((updateRole.getName()!=null ) && ((role.getName().equals(updateRole.getName())) ||(!roleRepository.existsByName(updateRole.getName())))) {
                        role.setName(updateRole.getName());
                    }
                    if (updateRole.getDescription()!=null) {
                        role.setDescription(updateRole.getDescription());
                    }
                    if (updateRole.isActive()){
                        role.setActive(true);
                    }
                    else
                        role.setActive(false);
                    if (updateRole.getPermissions()!=null) {
                        role.setPermissions(updateRole.getPermissions());
                    }
                    return roleRepository.save(role);
                }).orElseThrow(() -> new NoSuchElementException("Skill not found"));
        return updated;
    }
    @Override
    public ResultPaginationDTO getAllRoles(Specification<Role> spec, Pageable pageable) {
        Page<Role> page=roleRepository.findAll(spec,pageable);

        List<Role> roles=page.getContent();




        ResultPaginationDTO resultPaginationDTO=new ResultPaginationDTO();

        ResultPaginationDTO.Meta mt=new ResultPaginationDTO.Meta();
        mt.setPage(page.getNumber()+1);
        mt.setPageSize(page.getSize());

        mt.setPages(page.getTotalPages());
        mt.setTotal(page.getNumberOfElements());

        resultPaginationDTO.setMeta(mt);
        resultPaginationDTO.setResult(roles);
        return resultPaginationDTO;
    }
    @Override
    public void deleteRole(int id) {
        Optional<Role> role=roleRepository.findById((id));
        if (!role.isPresent()) {
            throw new NoSuchElementException("Role not found");
        }
        roleRepository.deleteById(id);
    }
    @Override
    public Optional<Role> getRoleById(int id){
        return roleRepository.findById(id);

    }

}
