package vn.tuan.jobhunter.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import vn.tuan.jobhunter.util.SecurityUtil;
import vn.tuan.jobhunter.util.constant.GenderEnum;

import java.time.Instant;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {
    ////attribute/////
    ///
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;
    @JsonProperty("name")
    @NotBlank(message = "Username không được để trống")
    @Size(min=4,max=20)
    private String username;
    @Size(min = 6, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    private Integer age=0;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;
    @Column(columnDefinition = "TEXT")
    private String refreshToken;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @PrePersist
    public void handleCreateAt() {
        this.createdAt = Instant.now();
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent()==true?
                SecurityUtil.getCurrentUserLogin().get():null;
    }
    @PreUpdate
    public void handleUpdateAt() {
        this.updatedAt = Instant.now();
        this.updatedBy =SecurityUtil.getCurrentUserLogin().isPresent()==true?
                SecurityUtil.getCurrentUserLogin().get():null;
    }


    //
    /////////mapping////////
    ///

    ///
    ////////method///////////




}
