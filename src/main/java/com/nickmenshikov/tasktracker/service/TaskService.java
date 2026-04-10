package com.nickmenshikov.tasktracker.service;

import com.nickmenshikov.tasktracker.dao.TaskDao;
import com.nickmenshikov.tasktracker.dto.CreateTaskRequest;
import com.nickmenshikov.tasktracker.dto.UpdateTaskRequest;
import com.nickmenshikov.tasktracker.exception.TaskNotFoundException;
import com.nickmenshikov.tasktracker.model.Priority;
import com.nickmenshikov.tasktracker.model.Status;
import com.nickmenshikov.tasktracker.model.Task;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TaskService {
    private final TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public Task createTask(CreateTaskRequest request, Long userId) {
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setCreatorId(userId);
        task.setCreatedAt(Instant.now());
        return taskDao.save(task);
    }

    public List<Task> getAllTasks(Long creatorId, int page, int size, Status status, Priority priority) {
        return taskDao.findAll(creatorId, page, size, status, priority);
    }

    public Task getTaskById(Long id, Long userId) {
        return taskDao.getById(id, userId).orElseThrow(
                () -> new TaskNotFoundException("Task not found " + id)
        );
    }

    public Task updateTask(Long id, Long userId, UpdateTaskRequest request) {
        Task task = taskDao.getById(id, userId).orElseThrow(
                () -> new TaskNotFoundException("Task not found" + id)
        );

        request.applyTo(task);

        return taskDao.update(task);
    }

    public void deleteTask(Long id, Long userId) {
        Task task = taskDao.getById(id, userId).orElseThrow(
                () -> new TaskNotFoundException("Task not found" + id)
        );

        taskDao.delete(task.getId());
    }
}
