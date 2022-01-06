package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseRequestDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseResponseDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseWithTraineeRegistrationDto;
import ro.creativeplus.learningplatformbackend.model.Course;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.model.Media;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

  private final CourseSectionMapper courseSectionMapper;
  private final CourseRegistrationMapper courseRegistrationMapper;
  private final MediaMapper mediaMapper;

  CourseMapper(CourseSectionMapper courseSectionMapper, CourseRegistrationMapper courseRegistrationMapper,
               MediaMapper mediaMapper) {
    this.courseSectionMapper = courseSectionMapper;
    this.courseRegistrationMapper = courseRegistrationMapper;
    this.mediaMapper = mediaMapper;
  }

  public CourseResponseDto courseToCourseResponseDto(Course course) {
    CourseResponseDto dto = new CourseResponseDto();
    dto.setId(course.getId());
    dto.setName(course.getName());
    dto.setDescription(course.getDescription());
    dto.setSectionNumber(course.getCourseSections().size());
    dto.setSections(
        course.getCourseSections().stream()
            .map(this.courseSectionMapper::courseSectionToCourseSectionResponseDto)
            .collect(Collectors.toList())
    );
    Media coverImage = course.getCoverImage();
    if(coverImage != null) {
      dto.setCoverImage(this.mediaMapper.toUrl(coverImage));
    }
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

  public CourseWithTraineeRegistrationDto toCourseWithTraineeRegistrationDto(Course course, Optional<CourseRegistration> registration) {
    CourseWithTraineeRegistrationDto dto = new CourseWithTraineeRegistrationDto();
    dto.setId(course.getId());
    dto.setName(course.getName());
    dto.setDescription(course.getDescription());
    dto.setSectionNumber(course.getCourseSections().size());
    registration.ifPresent(courseRegistration -> dto.setRegistration(this.courseRegistrationMapper.toDto(courseRegistration)));
    return dto;
  }
}
