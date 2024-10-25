package org.krainet.timetracker.controller;


import lombok.RequiredArgsConstructor;
import org.krainet.timetracker.dto.task.TaskCreateEditDto;
import org.krainet.timetracker.dto.task.TaskViewDto;
import org.krainet.timetracker.mapper.task.view.TaskViewMapper;
import org.krainet.timetracker.model.task.Task;
import org.krainet.timetracker.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskViewMapper taskViewMapper;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskViewDto> createTask(@RequestBody TaskCreateEditDto dto) {
        Task task = taskService.createTask(dto);
        TaskViewDto taskDto = taskViewMapper.toDto(task);
        return ResponseEntity.ok(taskDto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskViewDto> updateTask(@PathVariable Long id, @RequestBody TaskCreateEditDto dto) {
        Optional<Task> updatedTask = taskService.updateTask(id, dto);
        return updatedTask.map(task -> ResponseEntity.ok(taskViewMapper.toDto(task)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
