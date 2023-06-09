package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.project.ProjectRequestDto;
import ro.creativeplus.learningplatformbackend.dto.project.ProjectResponseDto;
import ro.creativeplus.learningplatformbackend.model.Project;

@Component
public class ProjectMapper {
  public Project toProject(ProjectRequestDto dto) {
    Project project = new Project();
    project.setName(dto.getName());
    project.setDescription(dto.getDescription());
    project.setStartDate(dto.getStartDate());
    project.setEndDate(dto.getEndDate());
    project.setTotalBudget(dto.getTotalBudget());
    project.setFinanceMean(dto.getFinanceMean());
    project.setMinAge(dto.getMinAge());
    project.setMaxAge(dto.getMaxAge());
    return project;
  }

  public ProjectResponseDto toDto(Project project) {
    ProjectResponseDto dto = new ProjectResponseDto();
    dto.setId(project.getId());
    dto.setName(project.getName());
    dto.setDescription(project.getDescription());
    dto.setStartDate(project.getStartDate());
    dto.setEndDate(project.getEndDate());
    dto.setTotalBudget(project.getTotalBudget());
    dto.setFinanceMean(project.getFinanceMean());
    dto.setMinAge(project.getMinAge());
    dto.setMaxAge(project.getMaxAge());
    return dto;
  }
}
