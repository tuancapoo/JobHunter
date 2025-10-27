package vn.tuan.jobhunter.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.tuan.jobhunter.util.SecurityUtil;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name="skills")
@Getter
@Setter
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Instant createdAt;
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

    /////////mapping////////
    ///
    // N Job - N Skill
    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    private List<Job> jobs;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "skills")
    @JsonIgnore
    private List<Subscriber> subscribers;
}
