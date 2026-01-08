package com.eprocure.project.dto.mapper;

import com.eprocure.project.dto.request.CreateProjectRequest;
import com.eprocure.project.dto.response.ProjectDTO;
import com.eprocure.project.entity.Project;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-08T21:39:20+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public ProjectDTO toDTO(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectDTO.ProjectDTOBuilder projectDTO = ProjectDTO.builder();

        projectDTO.id( project.getId() );
        projectDTO.title( project.getTitle() );
        projectDTO.description( project.getDescription() );
        projectDTO.budget( project.getBudget() );
        projectDTO.currency( project.getCurrency() );
        projectDTO.startDate( project.getStartDate() );
        projectDTO.endDate( project.getEndDate() );
        projectDTO.status( project.getStatus() );
        projectDTO.departmentId( project.getDepartmentId() );
        projectDTO.projectManagerId( project.getProjectManagerId() );
        projectDTO.createdAt( project.getCreatedAt() );
        projectDTO.createdBy( project.getCreatedBy() );
        projectDTO.updatedAt( project.getUpdatedAt() );
        projectDTO.updatedBy( project.getUpdatedBy() );

        return projectDTO.build();
    }

    @Override
    public Project toEntity(CreateProjectRequest request) {
        if ( request == null ) {
            return null;
        }

        Project.ProjectBuilder project = Project.builder();

        project.title( request.getTitle() );
        project.description( request.getDescription() );
        project.budget( request.getBudget() );
        project.currency( request.getCurrency() );
        project.startDate( request.getStartDate() );
        project.endDate( request.getEndDate() );
        project.departmentId( request.getDepartmentId() );
        project.projectManagerId( request.getProjectManagerId() );

        return project.build();
    }

    @Override
    public void updateEntityFromRequest(CreateProjectRequest request, Project project) {
        if ( request == null ) {
            return;
        }

        project.setTitle( request.getTitle() );
        project.setDescription( request.getDescription() );
        project.setBudget( request.getBudget() );
        project.setCurrency( request.getCurrency() );
        project.setStartDate( request.getStartDate() );
        project.setEndDate( request.getEndDate() );
        project.setDepartmentId( request.getDepartmentId() );
        project.setProjectManagerId( request.getProjectManagerId() );
    }
}
