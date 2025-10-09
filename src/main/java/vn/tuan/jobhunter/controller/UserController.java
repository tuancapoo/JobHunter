package vn.tuan.jobhunter.controller;

import org.springframework.web.bind.annotation.*;

import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.service.UserService;
import vn.tuan.jobhunter.domain.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;


@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<ApiResponse<User>> creatUser(
            @RequestBody User user){
        User createdUser = userService.createUser(user);
        ApiResponse<User> response=new ApiResponse<>(HttpStatus.CREATED,"create successful",createdUser,null);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("user")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(){
        ApiResponse<List<User>> response=new ApiResponse<>(HttpStatus.OK,"get all users",userService.getAllUsers(),null);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("user/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable Long id){
        return userService.getUserById(id).map(user -> {
            var response = new ApiResponse<>(HttpStatus.OK, "getUserById", user, null);
            return ResponseEntity.ok(response);

        }).orElseGet(() -> {
            ApiResponse<User> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND,
                    "Không tìm thấy user với ID: " + id, null, "USER_NOT_FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        });
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updated = userService.updateUser(id, user);
        var result = new ApiResponse<>(HttpStatus.OK, "updateUser", updated, null);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        ApiResponse<User> result = new ApiResponse<>(HttpStatus.NO_CONTENT, "deleteUser", null, null);
        return ResponseEntity.ok().body(result);
    }

}
