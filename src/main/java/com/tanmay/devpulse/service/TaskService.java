package com.tanmay.devpulse.service;

import com.tanmay.devpulse.dto.TaskRequest;
import com.tanmay.devpulse.dto.TaskResponse;
import com.tanmay.devpulse.entity.Task;
import com.tanmay.devpulse.entity.User;
import com.tanmay.devpulse.enums.TaskStatus;
import com.tanmay.devpulse.exception.TaskNotFoundException;
import com.tanmay.devpulse.repository.TaskRepository;
import com.tanmay.devpulse.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private TaskResponse mapToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }

    private Task getTaskEntityById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("You are not authorized to access this task.");
        }

        return task;
    }

    public TaskResponse createTask(TaskRequest request) {

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        } else {
            task.setStatus(TaskStatus.TODO);
        }

        task.setUser(getCurrentUser());

        Task savedTask = taskRepository.save(task);

        return mapToResponse(savedTask);
    }

    public List<TaskResponse> getAllTasks() {

        return taskRepository.findByUser(getCurrentUser())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long id) {

        Task task = getTaskEntityById(id);

        return mapToResponse(task);
    }

    public List<TaskResponse> getTasksByStatus(TaskStatus status) {

        return taskRepository.findByUserAndStatus(getCurrentUser(), status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TaskResponse updateTask(Long id, TaskRequest request) {

        Task task = getTaskEntityById(id);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        Task updatedTask = taskRepository.save(task);

        return mapToResponse(updatedTask);
    }

    public void deleteTask(Long id) {

        Task task = getTaskEntityById(id);

        taskRepository.delete(task);
    }
}