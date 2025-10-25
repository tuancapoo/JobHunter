package vn.tuan.jobhunter.domain.response.dto.responseDTO.ResumeDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class ResResumeCreateDTO {
    private int id;
    private Instant createdAt;
    private String createdBy;
}
