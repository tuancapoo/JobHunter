package vn.tuan.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tuan.jobhunter.domain.Job;
import vn.tuan.jobhunter.domain.Role;
import vn.tuan.jobhunter.domain.response.ApiResponse;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.JobDTO.ResJobDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.userDTO.ResUserDTO;
import vn.tuan.jobhunter.service.RoleService;
import vn.tuan.jobhunter.service.impl.RoleServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class RoleController {
    private final RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @PostMapping("/roles")
    public ResponseEntity<ApiResponse<Role>> createRole(@Valid @RequestBody Role role){
        Role newRole=roleService.createRole(role);
        ApiResponse<Role> response=new ApiResponse<>(HttpStatus.CREATED,"created role",newRole,null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/roles")
    public ResponseEntity<ApiResponse<Role>> updateRole(@RequestBody Role  role) {
        Role updated = roleService.updateRole(role);
        ApiResponse<Role> result = new ApiResponse<>(HttpStatus.OK, "updated role", updated , null);
        return ResponseEntity.ok().body(result);
    }
    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllSkills(
            @Filter Specification<Role> specification,
            Pageable pageable
    ){
        ResultPaginationDTO result= roleService.getAllRoles(specification,pageable);
        ApiResponse<ResultPaginationDTO> response=new ApiResponse<>(HttpStatus.OK,"get all users",result,null);
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSkill(@PathVariable("id") int id) {
        roleService.deleteRole(id);
        ApiResponse<Void> result = new ApiResponse<>(HttpStatus.OK, "delete successful", null, null);
        return ResponseEntity.ok().body(result);
    }
    @GetMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<Role>> getRoleById(@PathVariable int id){
        return roleService.getRoleById(id).map(role -> {
            var response = new ApiResponse<>(HttpStatus.OK, "getUserById", role  , null);
            return ResponseEntity.ok(response);

        }).orElseGet(() -> {
            ApiResponse<Role> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND,
                    "Không tìm thấy user với ID: " + id, null, "USER_NOT_FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        });
    }


}
