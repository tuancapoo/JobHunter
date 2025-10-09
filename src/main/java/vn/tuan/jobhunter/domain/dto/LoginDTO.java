package vn.tuan.jobhunter.domain.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

public class LoginDTO {
    @NotBlank(message = "Khong duoc de trong username")
    public String username;
    @NotBlank(message = "Khong duoc de trong password")
    public String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
