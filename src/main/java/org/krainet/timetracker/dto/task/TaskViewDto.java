package org.krainet.timetracker.dto.task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.krainet.timetracker.model.task.TaskStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskViewDto {
    private String title;
    private String description;
    private Integer projectId;
    private String assignedToEmail;
    private String createdByEmail;
    private TaskStatus status;
}
