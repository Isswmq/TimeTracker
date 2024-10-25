package org.krainet.timetracker.dto.project;

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
public class ProjectViewDto {
    private String name;
    private String description;
    private String ownerEmail;
    private List<TaskViewDto> tasks;
}
