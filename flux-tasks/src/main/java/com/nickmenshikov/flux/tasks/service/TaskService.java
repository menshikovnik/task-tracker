package com.nickmenshikov.flux.tasks.service;

import com.nickmenshikov.flux.auth.repository.UserRepository;
import com.nickmenshikov.flux.core.dto.CreateTaskRequest;
import com.nickmenshikov.flux.core.dto.UpdateTaskRequest;
import com.nickmenshikov.flux.core.exception.ProjectNotFoundException;
import com.nickmenshikov.flux.core.exception.TaskNotFoundException;
import com.nickmenshikov.flux.core.model.*;
import com.nickmenshikov.flux.projects.repository.ProjectRepository;
import com.nickmenshikov.flux.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;


    @Transactional
    public Task createTask(CreateTaskRequest request, Long userId) {
        User user = userRepository.getReferenceById(userId);

        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setCreator(user);
        task.setCreatedAt(Instant.now());
        task.setDueDate(request.dueDate());

        if (request.projectId() != null) {
            Project project = projectRepository.findProjectByIdAndUser(request.projectId(), user)
                    .orElseThrow(() -> new ProjectNotFoundException("Project not found: " + request.projectId()));
            task.setProject(project);
        }

        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Page<Task> getAllTasks(Long creatorId, Pageable pageable, Status status, Priority priority, Long projectId) {
        User creator = userRepository.getReferenceById(creatorId);
        return taskRepository.findAllFiltered(creator, status, priority, pageable, projectId);
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long id, Long userId) {
        User user = userRepository.getReferenceById(userId);
        return taskRepository.findTaskByIdAndCreator(id, user).orElseThrow(
                () -> new TaskNotFoundException("Task not found " + id)
        );
    }

    @Transactional
    public Task updateTask(Long id, Long userId, UpdateTaskRequest request) {
        User user = userRepository.getReferenceById(userId);
        Task task = taskRepository.findTaskByIdAndCreator(id, user).orElseThrow(
                () -> new TaskNotFoundException("Task not found: " + id)
        );

        request.applyTo(task);

        return task;
    }

    @Transactional
    public void deleteTask(Long id, Long userId) {
        User user = userRepository.getReferenceById(userId);
        Task task = taskRepository.findTaskByIdAndCreator(id, user).orElseThrow(
                () -> new TaskNotFoundException("Task not found: " + id)
        );

        taskRepository.deleteTaskById(task.getId());
    }
}
