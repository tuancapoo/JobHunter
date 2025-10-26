package vn.tuan.jobhunter.domain.response.dto.responseDTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.tuan.jobhunter.domain.Role;

@Getter
@Setter
public class ResLoginDTO {
    @JsonProperty("access_token")
    private  String accessToken;
    private UserLogin user;
    private Role role;



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class UserLogin{
        private int id;
        private String email;
        private String username;
        private Role role;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class UserGetAccount{
        private UserLogin user;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInsideToken{
        private int id;
        private String email;
        private String username;
    }
}
