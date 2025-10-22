package vn.tuan.jobhunter.domain.response.dto.criterial;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class CompanyCriteriaDTO {
    private Optional<String> name;
    private Optional<String> address;
    private Optional<String> phone;


}
