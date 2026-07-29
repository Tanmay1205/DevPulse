package com.tanmay.devpulse.controller;

import com.tanmay.devpulse.dto.DashboardResponse;
import com.tanmay.devpulse.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final TaskService taskService;

    public DashboardController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public DashboardResponse getDashboard() {
        return taskService.getDashboard();
    }
}