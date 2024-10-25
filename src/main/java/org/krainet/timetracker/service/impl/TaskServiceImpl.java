package org.krainet.timetracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.krainet.timetracker.dto.task.TaskCreateEditDto;
import org.krainet.timetracker.mapper.task.createEdit.TaskCreateEditMapper;
import org.krainet.timetracker.model.task.Task;
import org.krainet.timetracker.repository.TaskRepository;
import org.krainet.timetracker.service.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskCreateEditMapper taskCreateEditMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Task createTask(TaskCreateEditDto dto) {
        log.info("Creating task with details: {}", dto);
        Task task = taskCreateEditMapper.toEntity(dto);
        Task savedTask = taskRepository.save(task);
        log.info("Task created successfully: {}", savedTask);
        return savedTask;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> findById(Long id) {
        log.info("Finding task by ID: {}", id);
        return taskRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll(Pageable pageable) {
        log.info("Fetching all tasks with pagination: {}", pageable);
        return taskRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTask(Long id) {
        log.info("Deleting task with ID: {}", id);
        taskRepository.deleteById(id);
        log.info("Task with ID: {} deleted successfully", id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<Task> updateTask(Long id, TaskCreateEditDto dto) {
        log.info("Updating task with ID: {} with new details: {}", id, dto);
        return taskRepository.findById(id)
                .map(existingTask -> {
                    Task updatedTask = taskCreateEditMapper.toEntity(dto);
                    updatedTask.setId(existingTask.getId());
                    Task savedTask = taskRepository.save(updatedTask);
                    log.info("Task with ID: {} updated successfully: {}", id, savedTask);
                    return savedTask;
                });
    }
}
