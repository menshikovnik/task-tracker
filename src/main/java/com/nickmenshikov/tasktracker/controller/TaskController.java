package com.nickmenshikov.tasktracker.controller;

import com.nickmenshikov.tasktracker.dto.CreateTaskRequest;
import com.nickmenshikov.tasktracker.model.Task;
import com.nickmenshikov.tasktracker.model.User;
import com.nickmenshikov.tasktracker.service.TaskService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping(version = "1.0")
    public ResponseEntity<Void> create(@RequestBody CreateTaskRequest request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Task task = taskService.createTask(request, user.getId());
        return ResponseEntity.created(URI.create("/api/tasks/" + task.getId())).build();
    }

    @GetMapping(version = "1.0")
    public ResponseEntity<List<Task>> getAllTasks(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Task> tasks = taskService.getAllTasks(user.getId());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping(value = "/{id}", version = "1.0")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");

        Task task = taskService.getTaskById(id, user.getId());
        return ResponseEntity.ok(task);
    }
}
