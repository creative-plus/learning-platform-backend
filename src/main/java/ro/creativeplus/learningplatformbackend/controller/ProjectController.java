package ro.creativeplus.learningplatformbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.creativeplus.learningplatformbackend.dto.project.ProjectRequestDto;
import ro.creativeplus.learningplatformbackend.dto.project.ProjectResponseDto;
import ro.creativeplus.learningplatformbackend.mapper.ProjectMapper;
import ro.creativeplus.learningplatformbackend.model.Project;
import ro.creativeplus.learningplatformbackend.service.ProjectService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
@RolesAllowed("ROLE_TRAINER")
public class ProjectController {

  private final ProjectService projectService;
  private final ProjectMapper projectMapper;

  ProjectController(ProjectService projectService, ProjectMapper projectMapper) {
    this.projectService = projectService;
    this.projectMapper = projectMapper;
  }

  @GetMapping
  ResponseEntity<List<ProjectResponseDto>> getProjects() {
    List<ProjectResponseDto> dtos = projectService.getProjects().stream()
        .map(projectMapper::projectToProjectResponseDto)
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(dtos);
  }

  @GetMapping("/{id}")
  ResponseEntity<ProjectResponseDto> getProject(@PathVariable int id) {
    Project project = projectService.getProject(id);
    return ResponseEntity.ok().body(projectMapper.projectToProjectResponseDto(project));
  }

  @PostMapping
  ResponseEntity<ProjectResponseDto> addProject(@Valid @RequestBody ProjectRequestDto dto) {
    Project project = projectMapper.ProjectRequestDtoToProject(dto);
    Project result = projectService.addProject(project);
    ProjectResponseDto dtoResult = projectMapper.projectToProjectResponseDto(result);
    return ResponseEntity.created(URI.create("/projects/" + result.getId())).body(dtoResult);
  }

  @PutMapping("/{id}")
  ResponseEntity<ProjectResponseDto> editProject(@PathVariable int id, @Valid @RequestBody ProjectRequestDto dto) {
    Project project = projectMapper.ProjectRequestDtoToProject(dto);
    project.setId(id);
    Project result = projectService.editProject(project);
    ProjectResponseDto dtoResult = projectMapper.projectToProjectResponseDto(result);
    return ResponseEntity.ok().body(dtoResult);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<?> deleteProject(@PathVariable int id) {
    projectService.deleteProjectById(id);
    return ResponseEntity.noContent().build();
  }
}
