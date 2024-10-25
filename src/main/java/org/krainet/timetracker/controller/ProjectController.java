package org.krainet.timetracker.controller;

import lombok.RequiredArgsConstructor;
import org.krainet.timetracker.dto.project.ProjectCreateEditDto;
import org.krainet.timetracker.dto.project.ProjectViewDto;
import org.krainet.timetracker.mapper.project.view.ProjectViewMapper;
import org.krainet.timetracker.model.project.Project;
import org.krainet.timetracker.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectViewMapper projectViewMapper;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProjectViewDto> createProject(@RequestBody ProjectCreateEditDto dto) {
        Project project = projectService.createProject(dto);
        return ResponseEntity.ok(projectViewMapper.toDto(project));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProjectViewDto> updateProject(@PathVariable Integer id, @RequestBody ProjectCreateEditDto dto) {
        Optional<Project> updatedProject = projectService.updateProject(id, dto);
        return updatedProject.map(project -> ResponseEntity.ok(projectViewMapper.toDto(project)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
