package org.krainet.timetracker.service;

import org.krainet.timetracker.dto.task.TaskCreateEditDto;
import org.krainet.timetracker.model.task.Task;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task createTask(TaskCreateEditDto dto);

    Optional<Task> findById(Long id);

    List<Task> findAll(Pageable pageable);

    void deleteTask(Long id);

    Optional<Task> updateTask(Long id, TaskCreateEditDto dto);
}
