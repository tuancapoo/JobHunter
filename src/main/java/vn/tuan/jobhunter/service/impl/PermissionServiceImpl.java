package vn.tuan.jobhunter.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.controller.errors.CustomException;
import vn.tuan.jobhunter.domain.Permission;
import vn.tuan.jobhunter.domain.Resume;
import vn.tuan.jobhunter.domain.Skill;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResumeDTO.ResUpdateResumeDTO;
import vn.tuan.jobhunter.repository.PermissionRepository;
import vn.tuan.jobhunter.service.PermissionService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission createPermission(Permission permission) {
        if (isPermissionExist(permission)) {
            throw new CustomException("Permission already exist");
        }
        return permissionRepository.save(permission);
    }

    private boolean isPermissionExist(Permission permission) {
        return permissionRepository.existsByModuleAndApiPathAndMethod(permission.getModule(), permission.getApiPath(), permission.getMethod());
    }
    public Permission updatePermission(Permission permission) {
        Optional<Permission> permissionOptional = permissionRepository.findById(Long.valueOf(permission.getId()));
        if (!permissionOptional.isPresent()) {
            throw new CustomException("Permission not found");
        }
        if (isPermissionExist(permission)) {
            throw new CustomException("Permission already exist");
        }
        Permission updated=permissionOptional
                .map(per -> {
                    if (permission.getApiPath()!=null) {
                        per.setApiPath(permission.getApiPath());
                    }
                    if (permission.getMethod()!=null) {
                        per.setMethod(permission.getMethod());
                    }
                    if (permission.getModule()!=null) {
                        per.setModule(permission.getModule());
                    }if (permission.getName()!=null) {
                        per.setName(permission.getName());
                    }
                    return permissionRepository.save(per);
                }).orElseThrow(() -> new NoSuchElementException("Skill not found"));
        return updated;

    };
    public ResultPaginationDTO getAllPermissions(Specification<Permission> spec, Pageable pageable) {
        Page<Permission> page=permissionRepository.findAll(spec,pageable);

        List<Permission> permissions=page.getContent();


        ResultPaginationDTO resultPaginationDTO=new ResultPaginationDTO();

        ResultPaginationDTO.Meta mt=new ResultPaginationDTO.Meta();
        mt.setPage(page.getNumber()+1);
        mt.setPageSize(page.getSize());

        mt.setPages(page.getTotalPages());
        mt.setTotal(page.getNumberOfElements());

        resultPaginationDTO.setMeta(mt);
        resultPaginationDTO.setResult(permissions);
        return resultPaginationDTO;
    }
    @Override
    public void deletePermission(Long id) {
        Optional<Permission> permission=permissionRepository.findById(id);
        if (!permission.isPresent()) {
            throw new NoSuchElementException("Skill not found");
        }
        Permission currentPermission=permission.get();
        currentPermission.getRoles().forEach(j->j.getPermissions().remove(currentPermission));

        permissionRepository.deleteById(id);
    }

}

