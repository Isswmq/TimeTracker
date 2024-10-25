package org.krainet.timetracker.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.krainet.timetracker.model.task.SessionStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskSessionDto {
    private Long userId;
    private Long taskId;
    private LocalDateTime startTime;
    private LocalDateTime pauseStartTime;
    private LocalDateTime pauseEndTime;
    private LocalDateTime endTime;
    private SessionStatus status;
}
