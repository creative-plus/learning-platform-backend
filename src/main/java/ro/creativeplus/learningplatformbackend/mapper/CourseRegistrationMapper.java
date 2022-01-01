package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseRegistration.CourseRegistrationDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseRegistration.CourseRegistrationSectionDto;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class CourseRegistrationMapper {
  public CourseRegistrationDto toDto(CourseRegistration courseRegistration) {
    CourseRegistrationDto dto = new CourseRegistrationDto();
    dto.setId(courseRegistration.getId());
    dto.setDateStarted(courseRegistration.getDateStarted());
    dto.setDateFinished(courseRegistration.getDateFinished());
    List<CourseRegistrationSectionDto> sectionsDto = courseRegistration.getCourseSections().stream()
        .map(section -> {
          CourseRegistrationSectionDto sectionDto = new CourseRegistrationSectionDto();
          sectionDto.setId(section.getId());
          sectionDto.setTitle(section.getTitle());
          sectionDto.setOrder(section.getOrderInCourse());
          sectionDto.setType(section.getClass().getSimpleName().toLowerCase(Locale.ROOT));
          return sectionDto;
        })
        .collect(Collectors.toList());
    dto.setSections(sectionsDto);
    return dto;
  }
}
