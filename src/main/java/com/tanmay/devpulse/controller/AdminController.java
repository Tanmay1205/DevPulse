package com.tanmay.devpulse.controller;

import com.tanmay.devpulse.dto.UserResponse;
import com.tanmay.devpulse.entity.Task;
import com.tanmay.devpulse.service.AdminService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return adminService.getAllTasks();
    }
}