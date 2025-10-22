package vn.tuan.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.domain.dto.responseDTO.userDTO.ResCreateUserDTO;
import vn.tuan.jobhunter.domain.dto.responseDTO.userDTO.ResUserDTO;
import vn.tuan.jobhunter.domain.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.service.UserService;
import vn.tuan.jobhunter.domain.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import vn.tuan.jobhunter.util.annotation.ApiMessage;


@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<ResCreateUserDTO>> creatUser(
           @Valid @RequestBody User user){
        ResCreateUserDTO createdUser = userService.createUser(user);
        ApiResponse<ResCreateUserDTO> response=new ApiResponse<>(HttpStatus.CREATED,"create successful",createdUser,null);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/users")
    @ApiMessage("Get all user")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllUsers(
            @Filter Specification<User> specification,
            Pageable pageable
            //@ModelAttribute UserCriteriaDTO userCriteriaDTO
        ){
        //ResultPaginationDTO result= userService.getAllUsers(userCriteriaDTO,pageable);
        ResultPaginationDTO result= userService.getAllUsers(specification,pageable);
        ApiResponse<ResultPaginationDTO> response=new ApiResponse<>(HttpStatus.OK,"get all users",result,null);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<ResUserDTO>> getUser(@PathVariable Long id){
        return userService.getUserById(id).map(user -> {
            var response = new ApiResponse<>(HttpStatus.OK, "getUserById", userService.convertUserToResUserDTO(user)  , null);
            return ResponseEntity.ok(response);

        }).orElseGet(() -> {
            ApiResponse<ResUserDTO> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND,
                    "Không tìm thấy user với ID: " + id, null, "USER_NOT_FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        });
    }
    @PutMapping("/users")
    public ResponseEntity<ApiResponse<ResUserDTO>> updateUser(@Valid @RequestBody User user) {
        User updated = userService.updateUser(user);
        var result = new ApiResponse<>(HttpStatus.OK, "updateUser", userService.convertUserToResUserDTO(updated) , null);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        ApiResponse<User> result = new ApiResponse<>(HttpStatus.OK, "delete successful", null, null);
        return ResponseEntity.ok().body(result);
    }

}
