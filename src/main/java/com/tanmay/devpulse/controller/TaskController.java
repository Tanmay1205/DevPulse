package com.tanmay.devpulse.controller;

import com.tanmay.devpulse.dto.TaskRequest;
import com.tanmay.devpulse.dto.TaskResponse;
import com.tanmay.devpulse.enums.TaskStatus;
import com.tanmay.devpulse.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponse createTask(@Valid @RequestBody TaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping
    public Page<TaskResponse> getTasks(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction,

            @RequestParam(required = false) String keyword,

            @RequestParam(required = false) TaskStatus status) {

        return taskService.getTasks(
                page,
                size,
                sortBy,
                direction,
                keyword,
                status
        );
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {

        return taskService.updateTask(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return "Task deleted successfully";
    }

    @GetMapping("/overdue")
    public List<TaskResponse> getOverdueTasks() {
        return taskService.getOverdueTasks();
    }

    @GetMapping("/today")
    public List<TaskResponse> getTodayTasks() {
        return taskService.getTodayTasks();
    }
    @GetMapping("/high-priority")
    public List<TaskResponse> getHighPriorityTasks() {
        return taskService.getHighPriorityTasks();
    }

}