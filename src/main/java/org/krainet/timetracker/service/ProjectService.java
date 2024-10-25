package org.krainet.timetracker.service;

import org.krainet.timetracker.dto.project.ProjectCreateEditDto;
import org.krainet.timetracker.model.project.Project;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Project createProject(ProjectCreateEditDto dto);

    Optional<Project> findById(Integer id);

    List<Project> findAll(Pageable pageable);

    void deleteProject(Integer id);

    Optional<Project> updateProject(Integer id, ProjectCreateEditDto dto);
}
