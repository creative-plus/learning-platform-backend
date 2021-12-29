package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.exception.ObjectAlreadyExistsException;
import ro.creativeplus.learningplatformbackend.model.CourseSection;
import ro.creativeplus.learningplatformbackend.repository.CourseSectionRepository;

import java.util.Optional;

@Service
public class CourseSectionService {

  private CourseSectionRepository courseSectionRepository;

  CourseSectionService(CourseSectionRepository courseSectionRepository) {
    this.courseSectionRepository = courseSectionRepository;
  }

  public CourseSection addOrEditCourseSection(CourseSection courseSection) {
      return this.courseSectionRepository.save(courseSection);
  }

  public CourseSection addCourseSection(CourseSection courseSection) {
    if(courseSection.getId() > 0) {
      Optional<CourseSection> existingSection = this.courseSectionRepository.findById(courseSection.getId());
      if(existingSection.isPresent()) {
        throw new ObjectAlreadyExistsException("Section already exists.");
      }
    }
    return this.courseSectionRepository.save(courseSection);
  }
}
