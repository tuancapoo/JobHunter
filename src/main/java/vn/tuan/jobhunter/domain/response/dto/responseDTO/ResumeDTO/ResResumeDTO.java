package vn.tuan.jobhunter.domain.response.dto.responseDTO.ResumeDTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.tuan.jobhunter.domain.Resume;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResResumeDTO {
    private Long id;
    private String email;
    private String url;

    @Enumerated(EnumType.STRING)
    private Instant createdAt;
    private Instant updatedAt;

    private String createdBy;
    private String updatedBy;

    private UserName user;
    private JobResume job;
    private String companyName;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UserName {
        private long id;
        private String name;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class JobResume {
        private long id;
        private String name;

    }
}
