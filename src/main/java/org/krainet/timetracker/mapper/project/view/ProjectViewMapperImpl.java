package org.krainet.timetracker.mapper.project.view;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.krainet.timetracker.dto.project.ProjectViewDto;
import org.krainet.timetracker.dto.task.TaskViewDto;
import org.krainet.timetracker.mapper.task.view.TaskViewMapper;
import org.krainet.timetracker.model.project.Project;
import org.krainet.timetracker.model.task.Task;
import org.krainet.timetracker.model.user.User;
import org.krainet.timetracker.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectViewMapperImpl implements ProjectViewMapper {

    private final UserRepository userRepository;
    private final TaskViewMapper taskViewMapper;

    @Override
    public ProjectViewDto toDto(Project entity) {
        List<TaskViewDto> tasks = new ArrayList<>();

        if (entity.getTasks() != null) {
            tasks = entity.getTasks().stream()
                    .map(taskViewMapper::toDto)
                    .toList();
        }

        return ProjectViewDto.builder()
                .tasks(tasks)
                .description(entity.getDescription())
                .name(entity.getName())
                .ownerEmail(entity.getOwner().getEmail())
                .build();
    }

    @Override
    public Project toEntity(ProjectViewDto dto) {
        List<Task> tasks = new ArrayList<>();

        if (dto.getTasks() != null) {
            tasks = dto.getTasks().stream()
                    .map(taskViewMapper::toEntity)
                    .toList();
        }

        User owner = userRepository.findByEmail(dto.getOwnerEmail())
                .orElseThrow(EntityNotFoundException::new);
        return Project.builder()
                .tasks(tasks)
                .name(dto.getName())
                .description(dto.getDescription())
                .owner(owner)
                .build();
    }
}
