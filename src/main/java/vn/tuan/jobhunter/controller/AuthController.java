package vn.tuan.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import vn.tuan.jobhunter.controller.errors.CustomException;
import vn.tuan.jobhunter.domain.response.ApiResponse;
import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.domain.request.requestLoginDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResLoginDTO;
import vn.tuan.jobhunter.service.UserService;
import vn.tuan.jobhunter.util.SecurityUtil;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;

    @Value("${tuan.jwt.refresh-token-validity-in-seconds}")
    private Long refreshTokenExpirationInSeconds;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil, UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<ResLoginDTO> > login(@Valid @RequestBody requestLoginDTO requestLoginDTO){
        //nap input ussername/password vao Security
        UsernamePasswordAuthenticationToken authenticationToken
                =new UsernamePasswordAuthenticationToken(requestLoginDTO.getUsername(), requestLoginDTO.getPassword());

        //xac thuc nguoi dung =>
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //set thông tin người dùng đăng phập vào context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //create a token
        ResLoginDTO resLoginDTO=new ResLoginDTO();

        User user=userService.getUserByEmail(requestLoginDTO.getUsername());
        resLoginDTO.setUser(resLoginDTO.new UserLogin(user.getId(), user.getEmail(), user.getUsername(),user.getRole()));

        //create acess token
        String access_token=securityUtil.createAccessToken(authentication.getName(),resLoginDTO);



        resLoginDTO.setAccessToken(access_token);

        //create refreshtoken
        String refresh_token=securityUtil.createRefreshToken(requestLoginDTO.getUsername(),resLoginDTO);
        //update user
        userService.updateUserToken(refresh_token,user.getEmail());

        //set cookie
        ResponseCookie responseCookie=ResponseCookie
                .from("refresh_Token", refresh_token)
                .httpOnly(true)//Chỉ server dùng đc
                .secure(true)
                .path("/")//tất cả đường link
                .maxAge(refreshTokenExpirationInSeconds) //giay het han
                .build();


        ApiResponse<ResLoginDTO> response=new ApiResponse<>(HttpStatus.OK,"Call API success",resLoginDTO,null);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);
    }
    @GetMapping("/auth/account")
    public ResponseEntity<ApiResponse<ResLoginDTO.UserGetAccount>> getAccount(){
        String email=SecurityUtil.getCurrentUserLogin().isPresent()?
                SecurityUtil.getCurrentUserLogin().get():null;


        ResLoginDTO resLoginDTO=new ResLoginDTO();
        User currentUser=userService.getUserByEmail(email);
        ResLoginDTO.UserGetAccount userGetAccount=null;
        if(currentUser!=null){
            ResLoginDTO.UserLogin user= resLoginDTO.new UserLogin(currentUser.getId(), currentUser.getEmail(), currentUser.getUsername(),currentUser.getRole());
            userGetAccount= resLoginDTO.new UserGetAccount(user);
        }

        ApiResponse<ResLoginDTO.UserGetAccount> response=new ApiResponse<>(HttpStatus.OK,"Get account",userGetAccount,null);
        return ResponseEntity.ok().body(response);

    }
    @GetMapping("/auth/refresh")
    public ResponseEntity<ApiResponse<ResLoginDTO>> getRefreshToken(
            @CookieValue(name = "refresh_Token" ,defaultValue = "quanque")String refreshToken

    ){
        if (refreshToken.equals("quanque")){
            throw new CustomException("Không có token trong cookie");
        }
        //checkValid Token
        Jwt decodedToken = securityUtil.checkValidRefreshToken(refreshToken);
        //check user by email+refreshtoken

        String email=decodedToken.getSubject();
        User currentUser=userService.getUserByEmailAndRefreshToken(email,refreshToken);
        if (currentUser==null){
            throw new RuntimeException("Refresh token invalid");
        }
        //issue new refresh token
        //create a token
        ResLoginDTO resLoginDTO=new ResLoginDTO();

        User user=userService.getUserByEmail(email);
        resLoginDTO.setUser(resLoginDTO.new UserLogin(user.getId(), user.getEmail(), user.getUsername(), user.getRole()));

        //create acess token
        String access_token=securityUtil.createAccessToken(email,resLoginDTO);
        resLoginDTO.setAccessToken(access_token);

        //create refreshtoken
        String new_refresh_token=securityUtil.createRefreshToken(email,resLoginDTO);
        //update user
        userService.updateUserToken(new_refresh_token,user.getEmail());

        //set cookie
        ResponseCookie responseCookie=ResponseCookie
                .from("refresh_Token", new_refresh_token)
                .httpOnly(true)//Chỉ server dùng đc
                .secure(true)
                .path("/")//tất cả đường link
                .maxAge(refreshTokenExpirationInSeconds) //giay het han
                .build();


        ApiResponse<ResLoginDTO> response=new ApiResponse<>(HttpStatus.OK,"Call API success",resLoginDTO,null);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);
    }
    @PostMapping("/auth/logout")
    public ResponseEntity<ApiResponse<Void>> logout(){
        String email=SecurityUtil.getCurrentUserLogin().isPresent()?  SecurityUtil.getCurrentUserLogin().get():"";

        if (email.equals("")){
            throw new CustomException("Access token invalid");
        }

        //update refresh token = null
        this.userService.updateUserToken(null,email);
        //remove refreshtoken
        ResponseCookie deleteCookie=ResponseCookie
                .from("refresh_Token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
        ApiResponse<Void> response=new ApiResponse<>(HttpStatus.OK,"Login success",null,null);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,deleteCookie.toString()).body(response);

    }

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<String>>  test(){

        ApiResponse<String> response=new ApiResponse<>(HttpStatus.OK,"TestSuccess",null,null);
        return ResponseEntity.ok().body(response);
    }
}
