package com.tanmay.devpulse.service;

import com.tanmay.devpulse.dto.TaskResponse;
import com.tanmay.devpulse.dto.UserResponse;
import com.tanmay.devpulse.entity.Task;
import com.tanmay.devpulse.entity.User;
import com.tanmay.devpulse.repository.TaskRepository;
import com.tanmay.devpulse.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public AdminService(UserRepository userRepository,
                        TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .toList();
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}