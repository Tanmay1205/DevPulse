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
import com.tanmay.devpulse.dto.DashboardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


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
                task.getStatus(),
                task.getPriority(),
                task.getDueDate()
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
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }

        task.setDueDate(request.getDueDate());

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
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }

        task.setDueDate(request.getDueDate());

        Task updatedTask = taskRepository.save(task);

        return mapToResponse(updatedTask);
    }

    public void deleteTask(Long id) {

        Task task = getTaskEntityById(id);

        taskRepository.delete(task);
    }

    public DashboardResponse getDashboard() {

        User currentUser = getCurrentUser();

        long totalTasks = taskRepository.findByUser(currentUser).size();

        long todo = taskRepository.findByUserAndStatus(currentUser, TaskStatus.TODO).size();

        long inProgress = taskRepository.findByUserAndStatus(currentUser, TaskStatus.IN_PROGRESS).size();

        long completed = taskRepository.findByUserAndStatus(currentUser, TaskStatus.COMPLETED).size();

        return new DashboardResponse(
                totalTasks,
                todo,
                inProgress,
                completed
        );
    }

    public Page<TaskResponse> getTasks(
            int page,
            int size,
            String sortBy,
            String direction,
            String keyword,
            TaskStatus status) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        User currentUser = getCurrentUser();

        Page<Task> tasks;

        if (keyword != null && !keyword.isBlank()) {
            tasks = taskRepository.findByUserAndTitleContainingIgnoreCase(
                    currentUser,
                    keyword,
                    pageable
            );
        } else if (status != null) {
            tasks = taskRepository.findByUserAndStatus(
                    currentUser,
                    status,
                    pageable
            );
        } else {
            tasks = taskRepository.findByUser(
                    currentUser,
                    pageable
            );
        }

        return tasks.map(this::mapToResponse);
    }
}