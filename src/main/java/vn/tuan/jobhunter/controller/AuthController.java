package vn.tuan.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.tuan.jobhunter.domain.ApiResponse;
import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.domain.dto.LoginDTO;
import vn.tuan.jobhunter.util.SecurityUtil;

@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String> > login(@Valid @RequestBody LoginDTO loginDTO){
        //nap input ussername/password vao Security
        UsernamePasswordAuthenticationToken authenticationToken
                =new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        //xac thuc nguoi dung =>
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //create a token
        String access_token=authentication.getCredentials().toString();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ApiResponse<String> response=new ApiResponse<>(HttpStatus.CREATED,"Call API success",access_token,null);
        return ResponseEntity.ok().body(response);
    }
}
