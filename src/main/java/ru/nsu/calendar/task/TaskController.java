package ru.nsu.calendar.task;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.calendar.entities.User;
import ru.nsu.calendar.security.AuthService;
import ru.nsu.calendar.task.dto.CreateTaskRequestDto;
import ru.nsu.calendar.task.dto.EditTaskRequestDto;
import ru.nsu.calendar.task.dto.TaskDto;
import ru.nsu.calendar.utils.NullFieldChecker;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/tasks")
public class TaskController {
    private final TaskService taskService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> get(@RequestParam(name = "from", required = false) final LocalDateTime from,
                                             @RequestParam(name = "to", required = false) final LocalDateTime to,
                                             @RequestParam(name = "taskName", required = false) final String taskName,
                                             @RequestParam(name = "description", required = false) final String description){

        final User user = authService.getCurrentUser();
        final List<TaskDto> resultList = taskService.getTasks(user.getId(), from, to, taskName, description);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @PostMapping
    public void create(@RequestBody final CreateTaskRequestDto requestDto){
        NullFieldChecker.check(requestDto);
        final User user = authService.getCurrentUser();
        taskService.createTask(user, requestDto);
    }

    @PutMapping
    public void edit(@RequestBody final EditTaskRequestDto requestDto){
        NullFieldChecker.check(requestDto);
        final User user = authService.getCurrentUser();
        taskService.editTask(user, requestDto);
    }

    @DeleteMapping("/{taskId}")
    public void deleteById(@PathVariable(value = "taskId") final @NonNull Long taskId){
        final User user = authService.getCurrentUser();
        taskService.deleteTask(user, taskId);
    }
}
