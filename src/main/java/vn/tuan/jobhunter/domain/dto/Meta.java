package vn.tuan.jobhunter.domain.dto;

import lombok.Getter;
import lombok.Setter;
import vn.tuan.jobhunter.domain.Company;

@Getter
@Setter
public class Meta {
    private int page;
    private int pageSize;
    private int total;
    private int pages;
}
