package vn.tuan.jobhunter.domain.response.dto.criterial;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class UserCriteriaDTO {
    private Optional<String> username;
    private Optional<String> email;
}
