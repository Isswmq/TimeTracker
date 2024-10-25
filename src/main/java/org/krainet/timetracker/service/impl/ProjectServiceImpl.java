package org.krainet.timetracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.krainet.timetracker.dto.project.ProjectCreateEditDto;
import org.krainet.timetracker.mapper.project.createEdit.ProjectCreateEditMapper;
import org.krainet.timetracker.mapper.task.view.TaskViewMapper;
import org.krainet.timetracker.model.project.Project;
import org.krainet.timetracker.model.task.Task;
import org.krainet.timetracker.repository.ProjectRepository;
import org.krainet.timetracker.service.ProjectService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectCreateEditMapper projectCreateEditMapper;
    private final TaskViewMapper taskViewMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Project createProject(ProjectCreateEditDto dto) {
        Project project = projectCreateEditMapper.toEntity(dto);
        return projectRepository.save(project);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> findById(Integer id) {
        return projectRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProject(Integer id) {
        projectRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<Project> updateProject(Integer id, ProjectCreateEditDto dto) {
        log.info("Updating project with ID: {}", id);

        return projectRepository.findById(id)
                .map(existingProject -> {
                    log.info("Found project: {}", existingProject);

                    existingProject.setName(dto.getName());
                    existingProject.setDescription(dto.getDescription());
                    log.debug("Updated project name to '{}' and description to '{}'", dto.getName(), dto.getDescription());

                    if (dto.getTasks() != null && !dto.getTasks().isEmpty()) {
                        log.info("Updating tasks for the project");

                        Set<Task> newTasks = dto.getTasks().stream()
                                .map(taskDto -> {
                                    Task task = taskViewMapper.toEntity(taskDto);
                                    task.setProject(existingProject);
                                    log.debug("Mapped task DTO to entity: {}", task);
                                    return task;
                                })
                                .collect(Collectors.toSet());

                        existingProject.getTasks().removeIf(task -> !newTasks.contains(task));
                        log.debug("Removed outdated tasks");

                        for (Task newTask : newTasks) {
                            if (!existingProject.getTasks().contains(newTask)) {
                                existingProject.getTasks().add(newTask);
                                log.debug("Added new task: {}", newTask);
                            }
                        }
                    } else {
                        log.info("Clearing all tasks for the project as no tasks were provided");
                        existingProject.getTasks().clear();
                    }

                    Project savedProject = projectRepository.save(existingProject);
                    log.info("Project successfully updated and saved: {}", savedProject);

                    return savedProject;
                });
    }
}
