package vn.tuan.jobhunter.domain.response.dto.responseDTO.JobDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.userDTO.ResCreateUserDTO;
import vn.tuan.jobhunter.util.constant.GenderEnum;
import vn.tuan.jobhunter.util.constant.LevelEnum;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResCreateJob {
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

    private List<String> skills;

}
