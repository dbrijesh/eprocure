package com.eprocure.project.dto.response;

import com.eprocure.project.entity.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for project response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {

    private UUID id;
    private String title;
    private String description;
    private BigDecimal budget;
    private String currency;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
    private UUID departmentId;
    private UUID projectManagerId;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
