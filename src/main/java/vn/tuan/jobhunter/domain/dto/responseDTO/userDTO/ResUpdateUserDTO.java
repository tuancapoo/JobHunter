package vn.tuan.jobhunter.domain.dto.responseDTO.userDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import vn.tuan.jobhunter.util.constant.GenderEnum;

@Getter
@Setter
public class ResUpdateUserDTO {
    @JsonProperty("name")
    @NotBlank(message = "Username không được để trống")
    @Size(min=4,max=20)
    private String username;
    @Size(min = 6, message = "Password must be at least 8 characters long")
    private String password;
    private Integer age=0;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;
}