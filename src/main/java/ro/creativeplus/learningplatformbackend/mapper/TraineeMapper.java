package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.User.Trainee.TraineeRequestDto;
import ro.creativeplus.learningplatformbackend.dto.User.Trainee.TraineeResponseDto;
import ro.creativeplus.learningplatformbackend.dto.project.ProjectResponseDto;
import ro.creativeplus.learningplatformbackend.model.Project;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;
import ro.creativeplus.learningplatformbackend.service.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TraineeMapper {

  private final ProjectService projectService;
  private final ProjectMapper projectMapper;

  TraineeMapper(ProjectService projectService, ProjectMapper projectMapper) {
    this.projectService = projectService;
    this.projectMapper = projectMapper;
  }

  public Trainee toTrainee(TraineeRequestDto dto) {
    Trainee trainee = new Trainee();
    trainee.setFirstName(dto.getFirstName());
    trainee.setLastName(dto.getLastName());
    trainee.setEmail(dto.getEmail());
    trainee.setCountry(dto.getCountry());
    trainee.setPhoneNumber(dto.getPhoneNumber());
    List<Project> projects = this.projectService.getProjectsByIds(dto.getProjectIds());
    trainee.setProjects(projects);
    return trainee;
  }

  public TraineeResponseDto toDto(Trainee trainee) {
    TraineeResponseDto dto = new TraineeResponseDto();
    dto.setId(trainee.getId());
    dto.setFirstName(trainee.getFirstName());
    dto.setLastName(trainee.getLastName());
    dto.setEmail(trainee.getEmail());
    dto.setCountry(trainee.getCountry());
    dto.setPhoneNumber(trainee.getPhoneNumber());
    List<ProjectResponseDto> projectsDto = trainee.getProjects().stream()
        .map(this.projectMapper::toDto)
        .collect(Collectors.toList());
    dto.setProjects(projectsDto);
    return dto;
  }
}
