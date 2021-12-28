package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseRequestDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseResponseDto;
import ro.creativeplus.learningplatformbackend.model.Course;
import ro.creativeplus.learningplatformbackend.model.CourseSection;

import java.util.stream.Collectors;

@Component
public class CourseMapper {

  private CourseSectionMapper courseSectionMapper;

  CourseMapper(CourseSectionMapper courseSectionMapper) {
    this.courseSectionMapper = courseSectionMapper;
  }

  public CourseResponseDto courseToCourseResponseDto(Course course) {
    CourseResponseDto dto = new CourseResponseDto();
    dto.setId(course.getId());
    dto.setName(course.getName());
    dto.setDescription(course.getDescription());
    dto.setSections(
        course.getCourseSections().stream()
            .map(this.courseSectionMapper::courseSectionToCourseSectionResponseDto)
            .collect(Collectors.toList())
    );
    return dto;
  }

  public Course toCourse(CourseRequestDto dto) {
    Course course = new Course();
    course.setName(dto.getName());
    course.setDescription(dto.getDescription());
    course.setCourseSections(
        dto.getSections().stream()
            .map(this.courseSectionMapper::courseSectionRequestDtoToCourseSection)
            .collect(Collectors.toList())
    );
    return course;
  }
}
