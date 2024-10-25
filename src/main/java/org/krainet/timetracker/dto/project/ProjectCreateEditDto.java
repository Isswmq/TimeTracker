package org.krainet.timetracker.dto.project;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.krainet.timetracker.dto.task.TaskViewDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreateEditDto {
    @NotNull(message = "Project name cannot be null")
    @NotEmpty(message = "Project name cannot be empty")
    @Size(max = 255, message = "Project name must not exceed 255 characters")
    private String name;

    @NotNull(message = "Project description cannot be null")
    @NotEmpty(message = "Project description cannot be empty")
    @Size(max = 500, message = "Project description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Owner ID cannot be null")
    private Long ownerId;

    private List<TaskViewDto> tasks;
}
