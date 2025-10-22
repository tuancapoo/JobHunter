package vn.tuan.jobhunter.service.specification;

import org.springframework.data.jpa.domain.Specification;
import java.util.Optional;

public class GenericSpecification {

    public static <T> Specification<T> like(String fieldName, Optional<String> valueOpt) {
        if (valueOpt != null && valueOpt.isPresent()) {
            String value = "%" + valueOpt.get().trim() + "%";
            return (root, query, cb) -> cb.like(root.get(fieldName), value);
        }
        return Specification.where(null);
    }

    public static <T> Specification<T> equals(String fieldName, Optional<?> valueOpt) {
        if (valueOpt != null && valueOpt.isPresent()) {
            Object value = valueOpt.get();
            return (root, query, cb) -> cb.equal(root.get(fieldName), value);
        }
        return Specification.where(null);
    }
}

