package com.eprocure.project.service.impl;

import com.eprocure.project.dto.mapper.ProjectMapper;
import com.eprocure.project.dto.request.CreateProjectRequest;
import com.eprocure.project.dto.response.ProjectDTO;
import com.eprocure.project.dto.response.ProjectStatisticsDTO;
import com.eprocure.project.entity.Project;
import com.eprocure.project.entity.ProjectStatus;
import com.eprocure.project.exception.InvalidProjectDataException;
import com.eprocure.project.exception.ProjectNotFoundException;
import com.eprocure.project.repository.ProjectRepository;
import com.eprocure.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of ProjectService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDTO createProject(CreateProjectRequest request) {
        log.debug("Creating new project with title: {}", request.getTitle());

        // Validate dates
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new InvalidProjectDataException("End date must be equal to or after start date");
        }

        // Map to entity and save
        Project project = projectMapper.toEntity(request);
        project.setStatus(ProjectStatus.DRAFT);
        project.setCreatedBy("system"); // TODO: Get from security context
        project.setUpdatedBy("system");

        Project savedProject = projectRepository.save(project);
        log.info("Created project with id: {}", savedProject.getId());

        return projectMapper.toDTO(savedProject);
    }

    @Override
    public ProjectDTO updateProject(UUID id, CreateProjectRequest request) {
        log.debug("Updating project with id: {}", id);

        // Validate dates
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new InvalidProjectDataException("End date must be equal to or after start date");
        }

        // Find existing project
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        // Update fields from request
        projectMapper.updateEntityFromRequest(request, existingProject);
        existingProject.setUpdatedBy("system"); // TODO: Get from security context

        Project updatedProject = projectRepository.save(existingProject);
        log.info("Updated project with id: {}", id);

        return projectMapper.toDTO(updatedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDTO getProject(UUID id) {
        log.debug("Fetching project with id: {}", id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        return projectMapper.toDTO(project);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectDTO> listProjects(Pageable pageable) {
        log.debug("Listing projects with page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<Project> projectPage = projectRepository.findAllByOrderByCreatedAtDesc(pageable);
        return projectPage.map(projectMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectDTO> listProjectsByStatus(ProjectStatus status, Pageable pageable) {
        log.debug("Listing projects with status: {}", status);

        Page<Project> projectPage = projectRepository.findByStatus(status, pageable);
        return projectPage.map(projectMapper::toDTO);
    }

    @Override
    public void deleteProject(UUID id) {
        log.debug("Deleting project with id: {}", id);

        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException(id);
        }

        projectRepository.deleteById(id);
        log.info("Deleted project with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectStatisticsDTO getStatistics() {
        log.debug("Calculating project statistics");

        // Count total projects
        long totalProjects = projectRepository.count();

        // Count by status
        long activeProjects = projectRepository.countByStatus(ProjectStatus.ACTIVE);
        long completedProjects = projectRepository.countByStatus(ProjectStatus.COMPLETED);
        long draftProjects = projectRepository.countByStatus(ProjectStatus.DRAFT);

        // Calculate total budget
        BigDecimal totalBudget = projectRepository.sumTotalBudget();

        // Calculate percentage change for active projects (vs last month)
        Double activeProjectsChangePercent = calculateActiveProjectsChangePercent();

        return ProjectStatisticsDTO.builder()
                .totalProjects(totalProjects)
                .activeProjects(activeProjects)
                .activeProjectsChangePercent(activeProjectsChangePercent)
                .completedProjects(completedProjects)
                .draftProjects(draftProjects)
                .totalBudget(totalBudget != null ? totalBudget : BigDecimal.ZERO)
                .currency("EUR") // Default currency for display
                .build();
    }

    /**
     * Calculate the percentage change in active projects compared to last month.
     *
     * @return percentage change, or null if cannot calculate
     */
    private Double calculateActiveProjectsChangePercent() {
        try {
            LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

            // Count active projects from last month
            long activeProjectsLastMonth = projectRepository.countByStatusAndCreatedAtAfter(
                    ProjectStatus.ACTIVE,
                    oneMonthAgo.minusMonths(1)
            ) - projectRepository.countByStatusAndCreatedAtAfter(
                    ProjectStatus.ACTIVE,
                    oneMonthAgo
            );

            // Count active projects from this month
            long activeProjectsThisMonth = projectRepository.countByStatusAndCreatedAtAfter(
                    ProjectStatus.ACTIVE,
                    oneMonthAgo
            );

            // Calculate percentage change
            if (activeProjectsLastMonth == 0) {
                return activeProjectsThisMonth > 0 ? 100.0 : 0.0;
            }

            double change = ((double) (activeProjectsThisMonth - activeProjectsLastMonth) / activeProjectsLastMonth) * 100;
            return Math.round(change * 10.0) / 10.0; // Round to 1 decimal place
        } catch (Exception e) {
            log.warn("Error calculating active projects change percent", e);
            return null;
        }
    }
}
