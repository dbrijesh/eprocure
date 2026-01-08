package com.eprocure.project.dto.mapper;

import com.eprocure.project.dto.request.CreateProjectRequest;
import com.eprocure.project.dto.response.ProjectDTO;
import com.eprocure.project.entity.Project;
import org.mapstruct.*;

/**
 * MapStruct mapper for Project entity and DTOs.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {

    /**
     * Maps Project entity to ProjectDTO.
     *
     * @param project the project entity
     * @return project DTO
     */
    ProjectDTO toDTO(Project project);

    /**
     * Maps CreateProjectRequest to Project entity.
     * Status will be set to DRAFT by default in the entity.
     *
     * @param request the create project request
     * @return project entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Project toEntity(CreateProjectRequest request);

    /**
     * Updates an existing Project entity from CreateProjectRequest.
     * Preserves id, status, and audit fields.
     *
     * @param request the update request
     * @param project the existing project to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntityFromRequest(CreateProjectRequest request, @MappingTarget Project project);
}
