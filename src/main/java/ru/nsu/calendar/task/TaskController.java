package ru.nsu.calendar.task;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.calendar.entities.User;
import ru.nsu.calendar.security.AuthService;
import ru.nsu.calendar.task.dto.TaskDto;

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
}
