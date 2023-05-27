package ru.nsu.calendar.task;

import jakarta.persistence.criteria.Expression;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.nsu.calendar.entities.Task;
import ru.nsu.calendar.repository.TaskRepository;
import ru.nsu.calendar.task.dto.TaskDto;
import ru.nsu.calendar.utils.JpaSpecificationUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public @NonNull List<@NonNull TaskDto> getTasks(final long userId,
                                                    final LocalDateTime from,
                                                    final LocalDateTime to,
                                                    final String taskName,
                                                    final String description) {

        final List<Specification<Task>> specifications = new ArrayList<>();
        specifications.add(JpaSpecificationUtils.equalSpecification("userId", userId));

        if (from != null) {
            specifications.add(fromTaskSpecification(from));
        }
        if (to != null) {
            specifications.add(toTaskSpecification(to));
        }
        if (taskName != null) {
            specifications.add(JpaSpecificationUtils
                    .likeSpecification("taskName", taskName));
        }
        if (description != null) {
            specifications.add(JpaSpecificationUtils
                    .likeSpecification("description", description));
        }

        return taskRepository.findAll(Specification.allOf(specifications))
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    private static @NonNull Specification<Task> fromTaskSpecification(@NonNull final LocalDateTime from) {
        return (root, query, criteriaBuilder) -> {
            final Expression<LocalDate> taskEndDate = criteriaBuilder
                    .function("add_days_to_date", LocalDate.class,
                            root.get("taskDate"),
                            root.get("countOfRepeat"));
            return criteriaBuilder.greaterThanOrEqualTo(taskEndDate, from.toLocalDate());
        };
    }

    private static @NonNull Specification<Task> toTaskSpecification(@NonNull final LocalDateTime to) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("taskDate"), to.toLocalDate());
    }
}
