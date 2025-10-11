package vn.tuan.jobhunter.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import vn.tuan.jobhunter.domain.ApiResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    private final ObjectMapper mapper;

    public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        this.delegate.commence(request, response, authException);
        response.setContentType("application/json;charset=UTF-8");
        ApiResponse<Object> problemDetail = new ApiResponse<>();
        String errorMessage= Optional.ofNullable(authException.getCause())
                        .map(Throwable::getMessage)
                        .orElse(authException.getMessage());
        problemDetail.setStatus(Integer.toString(HttpStatus.UNAUTHORIZED.value()));
        problemDetail.setErrorCode(errorMessage);
        problemDetail.setMessage("Token invalid");
        mapper.writeValue(response.getWriter(), problemDetail);

    }
}
