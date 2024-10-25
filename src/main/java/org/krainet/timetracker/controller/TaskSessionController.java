package org.krainet.timetracker.controller;

import lombok.RequiredArgsConstructor;
import org.krainet.timetracker.dto.task.TaskSessionDto;
import org.krainet.timetracker.mapper.task.view.TaskSessionViewMapper;
import org.krainet.timetracker.model.task.TaskSession;
import org.krainet.timetracker.service.TaskSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
    @RequestMapping("/api/v1/tasks/sessions")
public class TaskSessionController {

    private final TaskSessionService taskSessionService;
    private final TaskSessionViewMapper taskSessionViewMapper;

    @PostMapping("/start")
    public ResponseEntity<TaskSessionDto> startSession(@RequestParam Long taskId, @RequestParam Long userId) {
        TaskSession session = taskSessionService.startSession(taskId, userId);
        return ResponseEntity.ok(taskSessionViewMapper.toDto(session));
    }

    @PostMapping("/pause/{sessionId}")
    public ResponseEntity<TaskSessionDto> pauseSession(@PathVariable Long sessionId) {
        TaskSession session = taskSessionService.pauseSession(sessionId);
        return ResponseEntity.ok(taskSessionViewMapper.toDto(session));
    }

    @PostMapping("/resume/{sessionId}")
    public ResponseEntity<TaskSessionDto> resumeSession(@PathVariable Long sessionId) {
        TaskSession session = taskSessionService.resumeSession(sessionId);
        return ResponseEntity.ok(taskSessionViewMapper.toDto(session));
    }

    @PostMapping("/end/{sessionId}")
    public ResponseEntity<TaskSessionDto> endSession(@PathVariable Long sessionId) {
        TaskSession session = taskSessionService.endSession(sessionId);
        return ResponseEntity.ok(taskSessionViewMapper.toDto(session));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskSessionDto>> getSessionsByTask(@PathVariable Long taskId) {
        List<TaskSession> sessions = taskSessionService.getSessionsByTaskId(taskId);
        return ResponseEntity.ok(sessions.stream().map(taskSessionViewMapper::toDto).toList());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskSessionDto>> getSessionsByUser(@PathVariable Long userId) {
        List<TaskSession> sessions = taskSessionService.getSessionsByUserId(userId);
        return ResponseEntity.ok(sessions.stream().map(taskSessionViewMapper::toDto).toList());
    }
}
