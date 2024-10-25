package org.krainet.timetracker.mapper.task.view;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.krainet.timetracker.dto.task.TaskViewDto;
import org.krainet.timetracker.model.project.Project;
import org.krainet.timetracker.model.task.Task;
import org.krainet.timetracker.model.user.User;
import org.krainet.timetracker.repository.ProjectRepository;
import org.krainet.timetracker.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskViewMapperImpl implements TaskViewMapper{

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public TaskViewDto toDto(Task entity) {
        return TaskViewDto.builder()
                .assignedToEmail(entity.getAssignedTo().getEmail())
                .createdByEmail(entity.getCreatedBy().getEmail())
                .description(entity.getDescription())
                .projectId(entity.getProject().getId())
                .title(entity.getTitle())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public Task toEntity(TaskViewDto dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(EntityNotFoundException::new);

        User createdBy = userRepository.findByEmail(dto.getCreatedByEmail())
                .orElseThrow(EntityNotFoundException::new);

        User assignedTo = userRepository.findByEmail(dto.getAssignedToEmail())
                .orElseThrow(EntityNotFoundException::new);

        return Task.builder()
                .status(dto.getStatus())
                .project(project)
                .createdBy(createdBy)
                .assignedTo(assignedTo)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }
}
