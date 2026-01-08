package com.eprocure.project.service;

import com.eprocure.project.dto.request.CreateProjectRequest;
import com.eprocure.project.dto.response.ProjectDTO;
import com.eprocure.project.dto.response.ProjectStatisticsDTO;
import com.eprocure.project.entity.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for Project operations.
 */
public interface ProjectService {

    /**
     * Create a new project.
     *
     * @param request the project creation request
     * @return the created project DTO
     */
    ProjectDTO createProject(CreateProjectRequest request);

    /**
     * Update an existing project.
     *
     * @param id the project ID
     * @param request the project update request
     * @return the updated project DTO
     */
    ProjectDTO updateProject(UUID id, CreateProjectRequest request);

    /**
     * Get a project by ID.
     *
     * @param id the project ID
     * @return the project DTO
     */
    ProjectDTO getProject(UUID id);

    /**
     * List all projects with pagination.
     *
     * @param pageable pagination information
     * @return page of project DTOs
     */
    Page<ProjectDTO> listProjects(Pageable pageable);

    /**
     * List projects by status with pagination.
     *
     * @param status the project status
     * @param pageable pagination information
     * @return page of project DTOs
     */
    Page<ProjectDTO> listProjectsByStatus(ProjectStatus status, Pageable pageable);

    /**
     * Delete a project by ID.
     *
     * @param id the project ID
     */
    void deleteProject(UUID id);

    /**
     * Get dashboard statistics.
     *
     * @return project statistics
     */
    ProjectStatisticsDTO getStatistics();
}
