package vn.tuan.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tuan.jobhunter.domain.Permission;
import vn.tuan.jobhunter.domain.Resume;
import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.domain.response.ApiResponse;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.userDTO.ResCreateUserDTO;
import vn.tuan.jobhunter.service.PermissionService;

@RestController
@RequestMapping("/api/v1")
public class PermissionController {
    private final PermissionService permissionService;
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    @PostMapping("/permissions")
    public ResponseEntity<ApiResponse<Permission>> creatPermission(
            @Valid @RequestBody Permission permission){
        Permission newPermission=permissionService.createPermission(permission);
        ApiResponse<Permission> apiResponse = new ApiResponse<>(HttpStatus.CREATED, "created", newPermission , null);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @PutMapping("/permissions")
    public ResponseEntity<ApiResponse<Permission>> updatePermission(
            @Valid @RequestBody Permission permission){
        Permission newPermission=permissionService.updatePermission(permission);
        ApiResponse<Permission> apiResponse = new ApiResponse<>(HttpStatus.OK, "created", newPermission , null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @GetMapping("/permissions")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllPermissions(
            @Filter Specification<Permission> specification,
            Pageable pageable
    ){
        ResultPaginationDTO result= permissionService.getAllPermissions(specification,pageable);
        ApiResponse<ResultPaginationDTO> response=new ApiResponse<>(HttpStatus.OK,"get all users",result,null);
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSkill(@PathVariable("id") Long id) {
        permissionService.deletePermission(id);
        ApiResponse<Void> result = new ApiResponse<>(HttpStatus.OK, "delete successful", null, null);
        return ResponseEntity.ok().body(result);
    }

}
