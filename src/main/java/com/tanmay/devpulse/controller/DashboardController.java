package com.tanmay.devpulse.controller;

import com.tanmay.devpulse.dto.DashboardResponse;
import com.tanmay.devpulse.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tanmay.devpulse.dto.RecentTaskResponse;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final TaskService taskService;

    public DashboardController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping("/activity")
    public Map<LocalDate, Long> getActivity() {
        return taskService.getActivity();
    }
    @GetMapping
    public DashboardResponse getDashboard() {
        return taskService.getDashboard();
    }

    @GetMapping("/recent-tasks")
    public ResponseEntity<List<RecentTaskResponse>> getRecentTasks() {

        return ResponseEntity.ok(
                taskService.getRecentTasks()
        );

    }
}

