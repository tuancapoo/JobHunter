package vn.tuan.jobhunter.domain.dto.responseDTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultPaginationDTO {
    private Meta meta;
    private Object result;
}
