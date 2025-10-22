package vn.tuan.jobhunter.domain.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meta {
    private int page;
    private int pageSize;
    private int total;
    private int pages;
}
