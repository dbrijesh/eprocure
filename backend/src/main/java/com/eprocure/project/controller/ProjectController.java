package com.eprocure.project.controller;

import com.eprocure.project.dto.request.CreateProjectRequest;
import com.eprocure.project.dto.response.ApiResponse;
import com.eprocure.project.dto.response.ProjectDTO;
import com.eprocure.project.dto.response.ProjectStatisticsDTO;
import com.eprocure.project.entity.ProjectStatus;
import com.eprocure.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller for Project operations.
 */
@RestController
@RequestMapping("/v1/projects")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "project-controller", description = "Project Management API")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @Operation(summary = "Create a new project")
    public ResponseEntity<ApiResponse<ProjectDTO>> createProject(
            @Valid @RequestBody CreateProjectRequest request) {
        log.info("POST /v1/projects - Creating new project: {}", request.getTitle());

        ProjectDTO createdProject = projectService.createProject(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdProject, "Project created successfully"));
    }

    @GetMapping
    @Operation(summary = "List all projects with pagination")
    public ResponseEntity<ApiResponse<Page<ProjectDTO>>> listProjects(
            @Parameter(description = "Page number (0-indexed)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filter by status (optional)")
            @RequestParam(required = false) ProjectStatus status) {
        log.info("GET /v1/projects - page: {}, size: {}, status: {}", page, size, status);

        Pageable pageable = PageRequest.of(page, size);
        Page<ProjectDTO> projects;

        if (status != null) {
            projects = projectService.listProjectsByStatus(status, pageable);
        } else {
            projects = projectService.listProjects(pageable);
        }

        return ResponseEntity.ok(ApiResponse.success(projects));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project by ID")
    public ResponseEntity<ApiResponse<ProjectDTO>> getProject(
            @Parameter(description = "Project ID")
            @PathVariable UUID id) {
        log.info("GET /v1/projects/{} - Fetching project", id);

        ProjectDTO project = projectService.getProject(id);
        return ResponseEntity.ok(ApiResponse.success(project));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing project")
    public ResponseEntity<ApiResponse<ProjectDTO>> updateProject(
            @Parameter(description = "Project ID")
            @PathVariable UUID id,
            @Valid @RequestBody CreateProjectRequest request) {
        log.info("PUT /v1/projects/{} - Updating project", id);

        ProjectDTO updatedProject = projectService.updateProject(id, request);
        return ResponseEntity.ok(ApiResponse.success(updatedProject, "Project updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a project")
    public ResponseEntity<ApiResponse<Void>> deleteProject(
            @Parameter(description = "Project ID")
            @PathVariable UUID id) {
        log.info("DELETE /v1/projects/{} - Deleting project", id);

        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.successVoid("Project deleted successfully"));
    }

    @GetMapping("/statistics")
    @Operation(summary = "Get dashboard statistics")
    public ResponseEntity<ApiResponse<ProjectStatisticsDTO>> getStatistics() {
        log.info("GET /v1/projects/statistics - Fetching statistics");

        ProjectStatisticsDTO statistics = projectService.getStatistics();
        return ResponseEntity.ok(ApiResponse.success(statistics));
    }
}
