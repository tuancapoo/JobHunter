package vn.tuan.jobhunter.domain.response.dto.responseDTO.JobDTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.tuan.jobhunter.util.constant.LevelEnum;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResJobDTO {
    private int id;
    private String name;
    private String location;
    private String salary;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private LevelEnum level;
    private Instant startDate;
    private Instant endDate;
    private boolean active;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;

    private List<String> skills;
}
