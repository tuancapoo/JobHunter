package vn.tuan.jobhunter.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import vn.tuan.jobhunter.controller.errors.CustomException;
import vn.tuan.jobhunter.domain.Permission;
import vn.tuan.jobhunter.domain.Role;
import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.service.PermissionService;
import vn.tuan.jobhunter.service.UserService;
import vn.tuan.jobhunter.util.SecurityUtil;

import java.util.HashSet;
import java.util.List;

public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;
    @Override
    @Transactional
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {

        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        System.out.println(">>> RUN preHandle");
        System.out.println(">>> path= " + path);
        System.out.println(">>> httpMethod= " + httpMethod);
        System.out.println(">>> requestURI= " + requestURI);
        //check permission
        String email = SecurityUtil.getCurrentUserLogin().isPresent()
                ? SecurityUtil.getCurrentUserLogin().get() : "";
        if (email != null && !email.isEmpty()) {
            User user = this.userService.getUserByEmail(email);
            if (user != null) {
                Role role = user.getRole();
                if (role != null) {
                    List<Permission> permissionList = role.getPermissions();
                    boolean isAllow = permissionList.stream().anyMatch(
                            item -> item.getMethod().equals(httpMethod) && item.getApiPath().equals(path)
                    );
                    if (isAllow==false) {
                        throw new CustomException("Permission not allowed");

                    }

                }
                else {
                    throw new CustomException("Permission not allowed");
                }
            }
        }
        return true;
    }
}
