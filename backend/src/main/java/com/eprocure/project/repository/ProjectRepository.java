package com.eprocure.project.repository;

import com.eprocure.project.entity.Project;
import com.eprocure.project.entity.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Spring Data JPA repository for Project entities.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    /**
     * Count projects by status.
     *
     * @param status the project status
     * @return count of projects with the given status
     */
    long countByStatus(ProjectStatus status);

    /**
     * Calculate the sum of all project budgets.
     *
     * @return total budget across all projects, or 0 if no projects exist
     */
    @Query("SELECT COALESCE(SUM(p.budget), 0) FROM Project p")
    BigDecimal sumTotalBudget();

    /**
     * Find all projects ordered by creation date descending (most recent first).
     *
     * @param pageable pagination information
     * @return page of projects
     */
    Page<Project> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /**
     * Find projects by status with pagination.
     *
     * @param status the project status to filter by
     * @param pageable pagination information
     * @return page of projects with the given status
     */
    Page<Project> findByStatus(ProjectStatus status, Pageable pageable);

    /**
     * Count projects by status created after a specific date.
     * Used for calculating percentage change statistics.
     *
     * @param status the project status
     * @param since the date to count from
     * @return count of projects created after the given date
     */
    long countByStatusAndCreatedAtAfter(ProjectStatus status, LocalDateTime since);

    /**
     * Count projects created after a specific date.
     * Used for calculating total project growth.
     *
     * @param since the date to count from
     * @return count of projects created after the given date
     */
    long countByCreatedAtAfter(LocalDateTime since);
}
