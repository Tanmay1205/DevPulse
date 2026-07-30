package com.tanmay.devpulse.service;

import com.tanmay.devpulse.dto.TaskRequest;
import com.tanmay.devpulse.dto.TaskResponse;
import com.tanmay.devpulse.entity.Task;
import com.tanmay.devpulse.entity.User;
import com.tanmay.devpulse.enums.Priority;
import com.tanmay.devpulse.enums.TaskStatus;
import com.tanmay.devpulse.exception.TaskNotFoundException;
import com.tanmay.devpulse.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

        @Mock
        private TaskRepository taskRepository;

        @Mock
        private CurrentUserService currentUserService;

        @InjectMocks
        private TaskService taskService;

        private User user;
        private Task task;
        private TaskRequest request;


        @BeforeEach
        void setUp() {

            user = new User();
            user.setId(1L);
            user.setEmail("tanmay@gmail.com");

            task = new Task();
            task.setId(1L);
            task.setTitle("Learn Spring");
            task.setDescription("Mockito Testing");
            task.setStatus(TaskStatus.TODO);
            task.setPriority(Priority.HIGH);
            task.setDueDate(LocalDate.now().plusDays(5));
            task.setUser(user);

            request = new TaskRequest();
            request.setTitle("Learn Spring");
            request.setDescription("Mockito Testing");
            request.setStatus(TaskStatus.TODO);
            request.setPriority(Priority.HIGH);
            request.setDueDate(LocalDate.now().plusDays(5));
        }

        @Test
        void createTask_ShouldReturnSavedTask() {

            when(currentUserService.getCurrentUser())
                    .thenReturn(user);

            when(taskRepository.save(any(Task.class)))
                    .thenAnswer(invocation -> {

                        Task saved = invocation.getArgument(0);
                        saved.setId(1L);
                        return saved;
                    });

            TaskResponse response =
                    taskService.createTask(request);

            assertNotNull(response);
            assertEquals("Learn Spring", response.getTitle());
            assertEquals(TaskStatus.TODO, response.getStatus());
            assertEquals(Priority.HIGH, response.getPriority());

            ArgumentCaptor<Task> captor =
                    ArgumentCaptor.forClass(Task.class);

            verify(taskRepository).save(captor.capture());

            Task captured = captor.getValue();

            assertEquals(user, captured.getUser());
            assertEquals("Learn Spring", captured.getTitle());
        }

        @Test
        void getTaskById_ShouldReturnTask() {

            when(taskRepository.findById(1L))
                    .thenReturn(Optional.of(task));

            when(currentUserService.getCurrentUser())
                    .thenReturn(user);

            TaskResponse response =
                    taskService.getTaskById(1L);

            assertNotNull(response);

            assertEquals(task.getId(), response.getId());
            assertEquals(task.getTitle(), response.getTitle());

            verify(taskRepository).findById(1L);
        }

        @Test
        void getTaskById_ShouldThrowTaskNotFoundException() {

            when(taskRepository.findById(1L))
                    .thenReturn(Optional.empty());

            assertThrows(
                    TaskNotFoundException.class,
                    () -> taskService.getTaskById(1L)
            );

            verify(taskRepository).findById(1L);
        }

        @Test
        void getTaskById_ShouldThrowAccessDeniedException() {

            User anotherUser = new User();
            anotherUser.setId(10L);

            task.setUser(anotherUser);

            when(taskRepository.findById(1L))
                    .thenReturn(Optional.of(task));

            when(currentUserService.getCurrentUser())
                    .thenReturn(user);

            assertThrows(
                    AccessDeniedException.class,
                    () -> taskService.getTaskById(1L)
            );
        }
}
