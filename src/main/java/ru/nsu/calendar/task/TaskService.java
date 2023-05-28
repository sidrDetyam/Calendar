package ru.nsu.calendar.task;

import jakarta.persistence.criteria.Expression;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.calendar.entities.Task;
import ru.nsu.calendar.entities.User;
import ru.nsu.calendar.repository.TaskRepository;
import ru.nsu.calendar.task.dto.CreateTaskRequestDto;
import ru.nsu.calendar.task.dto.EditTaskRequestDto;
import ru.nsu.calendar.task.dto.TaskDto;
import ru.nsu.calendar.utils.JpaSpecificationUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional(readOnly = true)
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

    @Transactional
    public void createTask(final @NonNull User user,
                           final @NonNull CreateTaskRequestDto requestDto){
        final Task task = new Task();
        task.setIsFinish(false);
        task.setUserId(user);
        task.setTaskName(requestDto.getTaskName());
        task.setDescription(requestDto.getDescription());
        task.setTaskDate(requestDto.getTaskDateTime().toLocalDate());
        task.setCountOfRepeat(requestDto.getCountOfRepeat());
        taskRepository.save(task);
    }

    @Transactional
    public void editTask(final @NonNull User user,
                         final @NonNull EditTaskRequestDto requestDto){
        final Task task = taskRepository.findById(requestDto.getId()).orElseThrow();
        if(!user.equals(task.getUserId())){
            throw new NoSuchElementException();
        }

        task.setTaskName(requestDto.getTaskName());
        task.setIsFinish(requestDto.getIsFinish());
        task.setCountOfRepeat(requestDto.getCountOfRepeat());
        task.setDescription(requestDto.getDescription());
        taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(final @NonNull User user,
                           final long taskId){
        final Task task = taskRepository.findById(taskId).orElseThrow();
        if(!user.equals(task.getUserId())){
            throw new NoSuchElementException();
        }
        taskRepository.delete(task);
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
