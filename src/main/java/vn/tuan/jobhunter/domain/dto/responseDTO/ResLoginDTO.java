package vn.tuan.jobhunter.domain.dto.responseDTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ResLoginDTO {
    @JsonProperty("access_token")
    private  String accessToken;
    private UserLogin user;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class UserLogin{
        private int id;
        private String email;
        private String username;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class UserGetAccount{
        private UserLogin user;

    }
}
