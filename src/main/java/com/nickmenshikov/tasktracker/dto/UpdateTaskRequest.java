package com.nickmenshikov.tasktracker.dto;

import com.nickmenshikov.tasktracker.model.Priority;
import com.nickmenshikov.tasktracker.model.Status;
import com.nickmenshikov.tasktracker.model.Task;

import java.util.Optional;

public record UpdateTaskRequest(String title, String description, Priority priority, Status status) {

    public void applyTo(Task task) {
        Optional.ofNullable(title).ifPresent(task::setTitle);
        Optional.ofNullable(description).ifPresent(task::setDescription);
        Optional.ofNullable(priority).ifPresent(task::setPriority);
        Optional.ofNullable(status).ifPresent(task::setStatus);
    }
}
