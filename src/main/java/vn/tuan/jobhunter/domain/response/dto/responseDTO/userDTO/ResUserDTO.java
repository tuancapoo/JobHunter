package vn.tuan.jobhunter.domain.response.dto.responseDTO.userDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class ResUserDTO {
    private int id;
    private String email;
    @JsonProperty("name")
    private String username;
    private Integer age;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant updatedAt;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    private GenderEnum gender;
    private String address;
    @JsonProperty("company")
    private CompanyUser companyUser;
    @Getter
    @Setter
    @AllArgsConstructor
    public static class CompanyUser{
        private int id;
        private String name;
    }



}
