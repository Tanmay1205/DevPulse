package com.tanmay.devpulse.repository;

import com.tanmay.devpulse.entity.Task;
import com.tanmay.devpulse.entity.User;
import com.tanmay.devpulse.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByUser(User user);

    List<Task> findByUserAndStatus(User user, TaskStatus status);
    Page<Task> findByUser(User user, Pageable pageable);

    Page<Task> findByUserAndStatus(User user,
                                   TaskStatus status,
                                   Pageable pageable);

    Page<Task> findByUserAndTitleContainingIgnoreCase(User user,
                                                      String keyword,
                                                      Pageable pageable);
}