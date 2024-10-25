package org.krainet.timetracker.repository;

import org.krainet.timetracker.model.task.TaskSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskSessionRepository extends JpaRepository<TaskSession, Long> {

    List<TaskSession> findByUserId(Long userId);

    List<TaskSession> findByTaskId(Long taskId);

    List<TaskSession> findByUserIdAndTaskId(Long userId, Long taskId);

}
