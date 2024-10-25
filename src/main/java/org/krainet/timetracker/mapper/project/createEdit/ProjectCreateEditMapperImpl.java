package org.krainet.timetracker.mapper.project.createEdit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.krainet.timetracker.dto.project.ProjectCreateEditDto;
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
public class ProjectCreateEditMapperImpl implements ProjectCreateEditMapper {

    private final UserRepository userRepository;
    private final TaskViewMapper taskViewMapper;

    @Override
    public ProjectCreateEditDto toDto(Project entity) {
        List<TaskViewDto> list = new ArrayList<>();

        if (entity.getTasks() != null) {
            List<TaskViewDto> dtos = entity.getTasks().stream()
                    .map(taskViewMapper::toDto)
                    .toList();
            list.addAll(dtos);
        }

        return ProjectCreateEditDto.builder()
                .tasks(list)
                .name(entity.getName())
                .description(entity.getDescription())
                .ownerId(entity.getOwner().getId())
                .build();
    }

    @Override
    public Project toEntity(ProjectCreateEditDto dto) {
        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(EntityNotFoundException::new);

        List<Task> tasks = new ArrayList<>();

        if(dto.getTasks() != null) {
            List<Task> taskList = dto.getTasks().stream()
                    .map(taskViewMapper::toEntity)
                    .toList();
            tasks.addAll(taskList);
        }

        return Project.builder()
                .tasks(tasks)
                .owner(owner)
                .description(dto.getDescription())
                .name(dto.getName())
                .build();
    }
}
