package vn.tuan.jobhunter.controller.errors;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.tuan.jobhunter.domain.ApiResponse;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(NoSuchElementException ex) {
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR, "handleNotFound",
                null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /*
     * handle the rest exceptions
     */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<?>> handleAllException(Exception ex) {
//        var result = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null,
//                "INTERNAL_SERVER_ERROR");
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        String errors = String.join("; ", errorList);
        ApiResponse<Object> response = new ApiResponse<>(HttpStatus.BAD_REQUEST, errors, null, "VALIDATION_ERROR");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={
            UsernameNotFoundException.class,
            BadCredentialsException.class
    })
    public ResponseEntity<ApiResponse<Object>> handleIdExeption(Exception ex) {
        ApiResponse<Object> res=new ApiResponse<>();
        res.setStatus(Integer.toString(HttpStatus.BAD_REQUEST.value()));
        res.setErrorCode(ex.getMessage());
        res.setMessage("Thông tin đăng nhập không hợp lệ ...");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);


    }
}
