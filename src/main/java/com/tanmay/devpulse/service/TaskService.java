package com.tanmay.devpulse.service;

import com.tanmay.devpulse.dto.DashboardResponse;
import com.tanmay.devpulse.dto.TaskRequest;
import com.tanmay.devpulse.dto.TaskResponse;
import com.tanmay.devpulse.entity.Task;
import com.tanmay.devpulse.entity.User;
import com.tanmay.devpulse.enums.Priority;
import com.tanmay.devpulse.enums.TaskStatus;
import com.tanmay.devpulse.exception.TaskNotFoundException;
import com.tanmay.devpulse.repository.TaskRepository;
import com.tanmay.devpulse.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;
import com.tanmay.devpulse.dto.RecentTaskResponse;


@Service
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;
    private final CurrentUserService currentUserService;

    public TaskService(TaskRepository taskRepository,
                       CurrentUserService currentUserService) {
        this.taskRepository = taskRepository;
        this.currentUserService = currentUserService;
    }


    private TaskResponse mapToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    private Task getTaskEntityById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Task not found. Task ID: {}", id);
                    return new TaskNotFoundException("Task not found with id: " + id);
                });

        if (!task.getUser().getId().equals(currentUserService.getCurrentUser().getId())) {
            logger.warn("Unauthorized access attempt for Task ID: {}", id);
            throw new AccessDeniedException(
                    "You are not authorized to access this task.");
        }

        return task;
    }

    public TaskResponse createTask(TaskRequest request) {

        User currentUser = currentUserService.getCurrentUser();

        logger.info("Creating task for user: {}", currentUser.getEmail());

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

        task.setUser(currentUser);

        Task savedTask = taskRepository.save(task);

        logger.info("Task created successfully. Task ID: {}", savedTask.getId());

        return mapToResponse(savedTask);
    }

    public List<TaskResponse> getAllTasks() {

        return taskRepository.findByUser(currentUserService.getCurrentUser())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long id) {

        Task task = getTaskEntityById(id);

        return mapToResponse(task);
    }

    public List<TaskResponse> getTasksByStatus(TaskStatus status) {

        return taskRepository.findByUserAndStatus(currentUserService.getCurrentUser(), status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TaskResponse updateTask(Long id, TaskRequest request) {
        logger.info("Updating task. Task ID: {}", id);
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
        logger.info("Task updated successfully. Task ID: {}", updatedTask.getId());
        return mapToResponse(updatedTask);
    }

    public void deleteTask(Long id) {

        Task task = getTaskEntityById(id);

        taskRepository.delete(task);
        logger.info("Task deleted successfully. Task ID: {}", id);
    }

    public DashboardResponse getDashboard() {

        User currentUser = currentUserService.getCurrentUser();

        logger.info("Fetching dashboard for user: {}", currentUser.getEmail());

        long totalTasks = taskRepository.findByUser(currentUser).size();

        long todo = taskRepository.findByUserAndStatus(
                currentUser,
                TaskStatus.TODO
        ).size();

        long inProgress = taskRepository.findByUserAndStatus(
                currentUser,
                TaskStatus.IN_PROGRESS
        ).size();

        long completed = taskRepository.findByUserAndStatus(
                currentUser,
                TaskStatus.COMPLETED
        ).size();

        long highPriority = taskRepository.findByUserAndPriority(
                currentUser,
                Priority.HIGH
        ).size();

        long todayTasks = taskRepository.findByUserAndDueDate(
                currentUser,
                LocalDate.now()
        ).size();

        return new DashboardResponse(
                totalTasks,
                todo,
                inProgress,
                completed,
                highPriority,
                todayTasks
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

        User currentUser = currentUserService.getCurrentUser();

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

    public List<TaskResponse> getOverdueTasks() {

        return taskRepository
                .findByUserAndDueDateBeforeAndStatusNot(
                        currentUserService.getCurrentUser(),
                        LocalDate.now(),
                        TaskStatus.COMPLETED
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    public List<TaskResponse> getTodayTasks() {

        return taskRepository
                .findByUserAndDueDate(
                        currentUserService.getCurrentUser(),
                        LocalDate.now()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    public List<TaskResponse> getHighPriorityTasks() {

        return taskRepository
                .findByUserAndPriority(
                        currentUserService.getCurrentUser(),
                        Priority.HIGH
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Map<LocalDate, Long> getActivity() {

        User user = currentUserService.getCurrentUser();

        List<Task> tasks = taskRepository.findByUser(user);

        return tasks.stream()
                .filter(task -> task.getCreatedAt() != null)
                .collect(Collectors.groupingBy(
                        task -> task.getCreatedAt().toLocalDate(),
                        Collectors.counting()
                ));
    }

    public List<RecentTaskResponse> getRecentTasks() {

        User user = currentUserService.getCurrentUser();

        return taskRepository
                .findTop5ByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(task -> new RecentTaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getPriority() != null
                                ? task.getPriority().name()
                                : "NONE",
                        task.getStatus().name(),
                        task.getDueDate()
                ))
                .toList();
    }
}