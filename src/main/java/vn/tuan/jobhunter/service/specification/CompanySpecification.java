package vn.tuan.jobhunter.service.specification;

import org.springframework.data.jpa.domain.Specification;
import vn.tuan.jobhunter.domain.Company;
import vn.tuan.jobhunter.domain.dto.criterial.CompanyCriteriaDTO;

public class CompanySpecification {

    public static Specification<Company> companySpecification(CompanyCriteriaDTO dto) {
        Specification<Company> spec = Specification.where(null);
        if (dto == null) return spec;

        spec = spec.and(GenericSpecification.like("name", dto.getName()));
        spec = spec.and(GenericSpecification.like("address", dto.getAddress()));
        return spec;
    }
}
