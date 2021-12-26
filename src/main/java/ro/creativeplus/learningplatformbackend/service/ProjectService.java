package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.exception.ProjectAlreadyExistsException;
import ro.creativeplus.learningplatformbackend.exception.ProjectNotFoundException;
import ro.creativeplus.learningplatformbackend.model.Project;
import ro.creativeplus.learningplatformbackend.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

  private ProjectRepository projectRepository;

  ProjectService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  public List<Project> getProjects() {
    return projectRepository.findAll();
  }

  public Project getProject(int id) {
    Optional<Project> project = projectRepository.findById(id);
    if(project.isEmpty()) {
      throw new ProjectNotFoundException();
    }
    return project.get();
  }

  public Project addProject(Project project) {
    if(project.getId() > 0) {
      Optional<Project> existingProject = projectRepository.findById(project.getId());
      if(existingProject.isPresent()) {
        throw new ProjectAlreadyExistsException();
      }
    }
    return projectRepository.save(project);
  }

  public Project editProject(Project project) {
    if(project.getId() == 0) {
      throw new ProjectNotFoundException();
    }
    Optional<Project> existingProject = projectRepository.findById(project.getId());
    if(existingProject.isEmpty()) {
      throw new ProjectNotFoundException();
    }

    return projectRepository.save(project);
  }

  public void deleteProject(Project project) {
    projectRepository.delete(project);
  }

  public void deleteProjectById(int id) {
    Project project = this.getProject(id);
    this.deleteProject(project);
  }

}
