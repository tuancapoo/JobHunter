package vn.tuan.jobhunter.service.specification;

import org.springframework.data.jpa.domain.Specification;
import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.domain.response.dto.criterial.UserCriteriaDTO;

public class UserSpecification {
    public static Specification<User> userSpecification(UserCriteriaDTO dto) {
        Specification<User> spec = Specification.where(null);
        if (dto == null) return spec;

        spec = spec.and(GenericSpecification.like("username", dto.getUsername()));
        spec = spec.and(GenericSpecification.like("email", dto.getEmail()));
        return spec;
    }
}
