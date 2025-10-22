package vn.tuan.jobhunter.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import vn.tuan.jobhunter.util.SecurityUtil;

import java.time.Instant;

@Entity
@Table(name="companies")
@Getter
@Setter

public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "name khong de trong")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String logo;


    private String address;
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant updatedAt;
    private  String createdBy;
    private  String updatedBy;

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




}
