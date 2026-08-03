package com.tanmay.devpulse.dto;

public class DashboardResponse {

    private long totalTasks;
    private long todo;
    private long inProgress;
    private long completed;
    private long highPriority;
    private long todayTasks;

    public DashboardResponse() {
    }

    public DashboardResponse(
            long totalTasks,
            long todo,
            long inProgress,
            long completed,
            long highPriority,
            long todayTasks
    ) {
        this.totalTasks = totalTasks;
        this.todo = todo;
        this.inProgress = inProgress;
        this.completed = completed;
        this.highPriority = highPriority;
        this.todayTasks = todayTasks;
    }

    public long getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(long totalTasks) {
        this.totalTasks = totalTasks;
    }

    public long getTodo() {
        return todo;
    }

    public void setTodo(long todo) {
        this.todo = todo;
    }

    public long getInProgress() {
        return inProgress;
    }

    public void setInProgress(long inProgress) {
        this.inProgress = inProgress;
    }

    public long getCompleted() {
        return completed;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }

    public long getHighPriority() {
        return highPriority;
    }

    public void setHighPriority(long highPriority) {
        this.highPriority = highPriority;
    }

    public long getTodayTasks() {
        return todayTasks;
    }

    public void setTodayTasks(long todayTasks) {
        this.todayTasks = todayTasks;
    }
}