package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.project.ProjectRequestDto;
import ro.creativeplus.learningplatformbackend.dto.project.ProjectResponseDto;
import ro.creativeplus.learningplatformbackend.model.Project;

@Component
public class ProjectMapper {
  public Project ProjectRequestDtoToProject(ProjectRequestDto dto) {
    Project project = new Project();
    project.setName(dto.getName());
    project.setDescription(dto.getDescription());
    project.setStartDate(dto.getStartDate());
    project.setEndDate(dto.getEndDate());
    return project;
  }

  public ProjectResponseDto projectToProjectResponseDto(Project project) {
    ProjectResponseDto dto = new ProjectResponseDto();
    dto.setId(project.getId());
    dto.setName(project.getName());
    dto.setDescription(project.getDescription());
    dto.setStartDate(project.getStartDate());
    dto.setEndDate(project.getEndDate());
    return dto;
  }
}
