package com.tanmay.devpulse.dto;

import java.time.LocalDate;

public class RecentTaskResponse {

    private Long id;
    private String title;
    private String priority;
    private String status;
    private LocalDate dueDate;

    public RecentTaskResponse() {
    }

    public RecentTaskResponse(
            Long id,
            String title,
            String priority,
            String status,
            LocalDate dueDate
    ) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}