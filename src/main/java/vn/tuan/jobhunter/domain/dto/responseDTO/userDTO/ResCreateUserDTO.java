package vn.tuan.jobhunter.domain.dto.responseDTO.userDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.tuan.jobhunter.util.constant.GenderEnum;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResCreateUserDTO {
    private int id;
    private String email;
    @JsonProperty("name")
    @NotBlank(message = "Username không được để trống")
    @Size(min=4,max=20)
    private String username;
    private Integer age;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;

}
