package com.nickmenshikov.flux.tasks.service;

import com.nickmenshikov.flux.core.dto.CreateTaskRequest;
import com.nickmenshikov.flux.core.dto.UpdateTaskRequest;
import com.nickmenshikov.flux.core.exception.TaskNotFoundException;
import com.nickmenshikov.flux.core.model.Priority;
import com.nickmenshikov.flux.core.model.Status;
import com.nickmenshikov.flux.core.model.Task;
import com.nickmenshikov.flux.tasks.repository.TaskRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;


    @Transactional
    public Task createTask(CreateTaskRequest request, Long userId) {
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setCreatorId(userId);
        task.setCreatedAt(Instant.now());
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Page<Task> getAllTasks(Long creatorId, Pageable pageable, Status status, Priority priority) {
        Specification<Task> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("creatorId"), creatorId));

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (priority != null) {
                predicates.add(cb.equal(root.get("priority"), priority));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return taskRepository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long id, Long userId) {
        return taskRepository.findTaskByIdAndCreatorId(id, userId).orElseThrow(
                () -> new TaskNotFoundException("Task not found " + id)
        );
    }

    @Transactional
    public Task updateTask(Long id, Long userId, UpdateTaskRequest request) {
        Task task = taskRepository.findTaskByIdAndCreatorId(id, userId).orElseThrow(
                () -> new TaskNotFoundException("Task not found" + id)
        );

        request.applyTo(task);

        return task;
    }

    @Transactional
    public void deleteTask(Long id, Long userId) {
        Task task = taskRepository.findTaskByIdAndCreatorId(id, userId).orElseThrow(
                () -> new TaskNotFoundException("Task not found" + id)
        );

        taskRepository.deleteTaskById(task.getId());
    }
}
