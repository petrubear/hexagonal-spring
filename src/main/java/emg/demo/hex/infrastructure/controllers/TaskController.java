package emg.demo.hex.infrastructure.controllers;

import emg.demo.hex.application.services.TaskService;
import emg.demo.hex.domain.models.AdditionalTaskInfo;
import emg.demo.hex.domain.models.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(this.taskService.createTask(task));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        return taskService.getTask(taskId).map(
                ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Task>> getAllTasks() {
        return ResponseEntity.ok(this.taskService.getAllTasks());
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        return taskService.updateTask(taskId, updatedTask).map(
                ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(this.taskService.deleteTask(taskId));
    }

    @GetMapping("/{taskId}/additionalInfo")
    public ResponseEntity<AdditionalTaskInfo> getAdditionalTaskInfo(@PathVariable Long taskId) {
        return ResponseEntity.ok(this.taskService.getAdditionalTaskInfo(taskId));
    }

}
