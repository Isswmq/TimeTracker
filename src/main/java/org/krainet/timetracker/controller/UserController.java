package org.krainet.timetracker.controller;

import lombok.RequiredArgsConstructor;
import org.krainet.timetracker.service.TaskSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final TaskSessionService taskSessionService;

    @GetMapping("/{userId}/tasks/{taskId}/time-spent")
    public ResponseEntity<String> getTimeSpentByUserOnTask(@PathVariable Long userId, @PathVariable Long taskId) {
        Optional<Duration> timeSpent = taskSessionService.getTotalTimeSpentByUserOnTask(userId, taskId);
        return timeSpent.map(duration -> ResponseEntity.ok("Time spent on task: " + formatDuration(duration)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/total-time")
    public ResponseEntity<String> getTotalTimeSpentByUser(@PathVariable Long userId) {
        Optional<Duration> totalTime = taskSessionService.getTotalTimeSpentByUser(userId);
        return totalTime.map(duration -> ResponseEntity.ok("Total time spent: " + formatDuration(duration)))
                .orElseGet(() -> ResponseEntity.ok("No time tracked."));
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return String.format("%d hours, %d minutes", hours, minutes);
    }
}
