package com.nickmenshikov.flux.core.dto;

import com.nickmenshikov.flux.core.model.Priority;
import com.nickmenshikov.flux.core.model.Status;
import com.nickmenshikov.flux.core.model.Task;

import java.time.Instant;

public record TaskResponse(
        Long id,
        String title,
        String description,
        Status status,
        Priority priority,
        Instant createdAt
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getCreatedAt()
        );
    }
}
