package org.krainet.timetracker.mapper.task.view;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.krainet.timetracker.dto.task.TaskSessionDto;
import org.krainet.timetracker.model.task.Task;
import org.krainet.timetracker.model.task.TaskSession;
import org.krainet.timetracker.model.user.User;
import org.krainet.timetracker.repository.TaskRepository;
import org.krainet.timetracker.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskSessionViewMapperImpl implements TaskSessionViewMapper {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public TaskSessionDto toDto(TaskSession entity) {
        return TaskSessionDto.builder()
                .userId(entity.getUser().getId())
                .taskId(entity.getTask().getId())
                .startTime(entity.getStartTime())
                .pauseStartTime(entity.getPauseStartTime())
                .pauseEndTime(entity.getPauseEndTime())
                .endTime(entity.getEndTime())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public TaskSession toEntity(TaskSessionDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(EntityNotFoundException::new);

        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(EntityNotFoundException::new);

        return TaskSession.builder()
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .pauseStartTime(dto.getPauseStartTime())
                .pauseEndTime(dto.getPauseEndTime())
                .user(user)
                .task(task)
                .status(dto.getStatus())
                .build();
    }
}
