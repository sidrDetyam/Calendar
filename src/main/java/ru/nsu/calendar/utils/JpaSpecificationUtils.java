package ru.nsu.calendar.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class JpaSpecificationUtils {
    public static <T> @NonNull Specification<T> equalSpecification(@NonNull final String fieldName,
                                                                   @NonNull final Object value) {
        return (root, cq, cb) -> cb.equal(root.get(fieldName), value);
    }

    public static <T> @NonNull Specification<T> likeSpecification(@NonNull final String fieldName,
                                                                  @NonNull final String value) {
        return (root, cq, cb) -> {
            final String lowercasePredicate = "%" + value.toLowerCase() + "%";
            return cb.like(cb.lower(root.get(fieldName)), lowercasePredicate);
        };
    }
}
