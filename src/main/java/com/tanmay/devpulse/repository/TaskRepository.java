package com.tanmay.devpulse.repository;

import com.tanmay.devpulse.entity.Task;
import com.tanmay.devpulse.entity.User;
import com.tanmay.devpulse.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByUser(User user);

    List<Task> findByUserAndStatus(User user, TaskStatus status);
}