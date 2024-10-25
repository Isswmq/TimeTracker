package org.krainet.timetracker.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.krainet.timetracker.model.task.SessionStatus;
import org.krainet.timetracker.model.task.Task;
import org.krainet.timetracker.model.task.TaskSession;
import org.krainet.timetracker.model.user.User;
import org.krainet.timetracker.repository.TaskRepository;
import org.krainet.timetracker.repository.TaskSessionRepository;
import org.krainet.timetracker.repository.UserRepository;
import org.krainet.timetracker.service.TaskSessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskSessionServiceImpl implements TaskSessionService {

    private final TaskSessionRepository taskSessionRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TaskSession startSession(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        TaskSession session = TaskSession.builder()
                .task(task)
                .user(user)
                .startTime(LocalDateTime.now())
                .status(SessionStatus.IN_PROGRESS)
                .build();

        return taskSessionRepository.save(session);
    }

    @Override
    @Transactional
    public TaskSession pauseSession(Long sessionId) {
        TaskSession session = taskSessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Task session not found"));

        session.setPauseStartTime(LocalDateTime.now());
        session.setStatus(SessionStatus.PAUSED);
        return taskSessionRepository.save(session);
    }

    @Override
    @Transactional
    public TaskSession resumeSession(Long sessionId) {
        TaskSession session = taskSessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Task session not found"));

        session.setPauseEndTime(LocalDateTime.now());
        session.setStatus(SessionStatus.IN_PROGRESS);
        return taskSessionRepository.save(session);
    }

    @Override
    @Transactional
    public TaskSession endSession(Long sessionId) {
        TaskSession session = taskSessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Task session not found"));

        session.setEndTime(LocalDateTime.now());
        session.setStatus(SessionStatus.COMPLETED);
        return taskSessionRepository.save(session);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskSession> getSessionsByTaskId(Long taskId) {
        return taskSessionRepository.findByTaskId(taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskSession> getSessionsByUserId(Long userId) {
        return taskSessionRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Optional<Duration> getTotalTimeSpentByUserOnTask(Long userId, Long taskId) {
        log.info("Calculating total time spent by user with ID: {} on task with ID: {}", userId, taskId);

        Optional<Duration> totalDuration = taskSessionRepository.findByUserIdAndTaskId(userId, taskId).stream()
                .filter(session -> session.getEndTime() != null)
                .map(this::calculateSessionDuration)
                .reduce(Duration::plus);

        log.info("Total time spent by user with ID: {} on task with ID: {} is: {}", userId, taskId, totalDuration.orElse(Duration.ZERO));
        return totalDuration;
    }

    @Override
    @Transactional
    public Optional<Duration> getTotalTimeSpentByUser(Long userId) {
        log.info("Calculating total time spent by user with ID: {}", userId);

        Optional<Duration> totalDuration = taskSessionRepository.findByUserId(userId).stream()
                .filter(session -> session.getEndTime() != null)
                .map(this::calculateSessionDuration)
                .reduce(Duration::plus);

        log.info("Total time spent by user with ID: {} is: {}", userId, totalDuration.orElse(Duration.ZERO));
        return totalDuration;
    }
    private Duration calculateSessionDuration(TaskSession session) {
        Duration totalDuration = Duration.between(session.getStartTime(), session.getEndTime());
        if (session.getPauseStartTime() != null && session.getPauseEndTime() != null) {
            Duration pauseDuration = Duration.between(session.getPauseStartTime(), session.getPauseEndTime());
            totalDuration = totalDuration.minus(pauseDuration);
        }
        return totalDuration;
    }
}
