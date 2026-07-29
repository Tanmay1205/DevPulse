package com.tanmay.devpulse.dto;

import com.tanmay.devpulse.enums.TaskStatus;
import java.time.LocalDate;
import com.tanmay.devpulse.enums.Priority;


public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;

    public TaskResponse(Long id,
                        String title,
                        String description,
                        TaskStatus status,
                        Priority priority,
                        LocalDate dueDate) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public TaskResponse(Long id, String title, String description, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    private Priority priority;
    private LocalDate dueDate;
}