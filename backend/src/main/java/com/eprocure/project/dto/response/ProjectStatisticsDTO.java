package com.eprocure.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for project statistics (dashboard).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectStatisticsDTO {

    private long totalProjects;
    private long activeProjects;
    private Double activeProjectsChangePercent;
    private long completedProjects;
    private long draftProjects;
    private BigDecimal totalBudget;
    private String currency;
}
