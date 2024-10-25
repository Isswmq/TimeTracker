package org.krainet.timetracker.service;

import org.krainet.timetracker.model.task.TaskSession;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface TaskSessionService {

    TaskSession startSession(Long taskId, Long userId);

    TaskSession pauseSession(Long sessionId);

    TaskSession resumeSession(Long sessionId);

    TaskSession endSession(Long sessionId);

    List<TaskSession> getSessionsByTaskId(Long taskId);

    List<TaskSession> getSessionsByUserId(Long userId);

    Optional<Duration> getTotalTimeSpentByUserOnTask(Long userId, Long taskId);

    Optional<Duration> getTotalTimeSpentByUser(Long userId);
}
