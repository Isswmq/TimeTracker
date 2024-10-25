package org.krainet.timetracker.mapper.task.createEdit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.krainet.timetracker.dto.task.TaskCreateEditDto;
import org.krainet.timetracker.model.project.Project;
import org.krainet.timetracker.model.task.Task;
import org.krainet.timetracker.model.task.TaskStatus;
import org.krainet.timetracker.model.user.User;
import org.krainet.timetracker.repository.ProjectRepository;
import org.krainet.timetracker.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskCreateEditMapperImpl implements TaskCreateEditMapper{

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public TaskCreateEditDto toDto(Task entity) {
        return TaskCreateEditDto.builder()
                .assignedToId(entity.getAssignedTo().getId())
                .createdById(entity.getCreatedBy().getId())
                .description(entity.getDescription())
                .projectId(entity.getProject().getId())
                .title(entity.getTitle())
                .build();
    }

    @Override
    public Task toEntity(TaskCreateEditDto dto) {
        User assignedTo = userRepository.findById(dto.getAssignedToId())
                .orElseThrow(EntityNotFoundException::new);

        User createdBy = userRepository.findById(dto.getCreatedById())
                .orElseThrow(EntityNotFoundException::new);

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(EntityNotFoundException::new);

        return Task.builder()
                .description(dto.getDescription())
                .title(dto.getTitle())
                .assignedTo(assignedTo)
                .status(TaskStatus.PENDING)
                .createdBy(createdBy)
                .project(project)
                .build();
    }
}
