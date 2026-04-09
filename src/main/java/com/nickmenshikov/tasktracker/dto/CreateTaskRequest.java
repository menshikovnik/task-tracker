package com.nickmenshikov.tasktracker.dto;

import com.nickmenshikov.tasktracker.model.Priority;
import com.nickmenshikov.tasktracker.model.Status;
import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(
        @NotBlank(message = "Title must not be empty")
        String title,


        String description,

        @NotBlank(message = "Priority must not be empty")
        Priority priority,

        @NotBlank(message = "Status must not be empty")
        Status status
) {
}
