package org.krainet.timetracker.dto.task;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateEditDto {

    @NotNull(message = "Task title cannot be null")
    @NotEmpty(message = "Task title cannot be empty")
    @Size(max = 255, message = "Task title must not exceed 255 characters")
    private String title;

    @NotNull(message = "Task description cannot be null")
    @NotEmpty(message = "Task description cannot be empty")
    @Size(max = 500, message = "Task description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Assigned to ID cannot be null")
    private Long assignedToId;

    @NotNull(message = "Created by ID cannot be null")
    private Long createdById;

    @NotNull(message = "Project ID cannot be null")
    private Integer projectId;
}
