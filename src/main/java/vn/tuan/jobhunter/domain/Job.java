package vn.tuan.jobhunter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.tuan.jobhunter.util.SecurityUtil;
import vn.tuan.jobhunter.util.constant.LevelEnum;

import java.time.Instant;
import java.util.List;


@Entity
@Table(name="jobs")
@Getter
@Setter
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String location;
    private String salary;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private LevelEnum level;

    @Column(columnDefinition = "Text")
    private String description;

    private Instant startDate;
    private Instant endDate;
    private boolean active;
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
    // N Job - 1 Company
    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;
    // N Job - N Company
    @ManyToMany
    @JsonIgnoreProperties(value={"jobs"})
    @JoinTable(name="job_skill",joinColumns =@JoinColumn(name="job_id"),
    inverseJoinColumns = @JoinColumn(name="skill_id"))
    private List<Skill> skills;





}
