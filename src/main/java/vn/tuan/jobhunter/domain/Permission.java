package vn.tuan.jobhunter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.tuan.jobhunter.util.SecurityUtil;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="permissions")
@NoArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message="name khong de trong")
    private String name;
    @NotBlank(message = "apiPath khong de trong")
    private String apiPath;
    @NotBlank(message = "method khong de trong")
    private String method;
    @NotBlank(message = "module khong de trong")
    private String module;
    private String createdBy;
    private Instant createdAt;
    private String updatedBy;
    private Instant updatedAt;
    public Permission(String name, String apiPath, String method, String module){
        this.name = name;
        this.apiPath = apiPath;
        this.method = method;
        this.module = module;
    }

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



    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private List<Role> roles;

}
