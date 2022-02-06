package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseRequestDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseResponseDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.CourseSectionResponseDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseWithTraineeRegistrationDto;
import ro.creativeplus.learningplatformbackend.model.Course;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.model.CourseSection.CourseSection;
import ro.creativeplus.learningplatformbackend.model.Media;

import java.util.Optional;
import java.util.function.Function;
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

  public CourseResponseDto toResponseDto(Course course) {
    return this.toResponseDto(course, false);
  }

  public CourseResponseDto toResponseDto(Course course, boolean hideSectionContent) {
    CourseResponseDto dto = new CourseResponseDto();
    dto.setId(course.getId());
    dto.setName(course.getName());
    dto.setDescription(course.getDescription());
    dto.setSectionNumber(course.getCourseSections().size());
    Function<CourseSection, CourseSectionResponseDto> mapper;
    if(hideSectionContent) {
      mapper = this.courseSectionMapper::toLightDto;
    } else {
      mapper = this.courseSectionMapper::toResponseDto;
    }
    dto.setSections(
        course.getCourseSections().stream()
            .map(mapper)
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
            .map(this.courseSectionMapper::toCourseSection)
            .collect(Collectors.toList())
    );
    if(dto.getCoverImage() != null) {
      course.setCoverImage(this.mediaMapper.toMedia(dto.getCoverImage()));
    }
    return course;
  }

  public CourseWithTraineeRegistrationDto toCourseWithTraineeRegistrationDto(Course course, Optional<CourseRegistration> registration) {
    CourseWithTraineeRegistrationDto dto = new CourseWithTraineeRegistrationDto();
    dto.setId(course.getId());
    dto.setName(course.getName());
    dto.setDescription(course.getDescription());
    dto.setSectionNumber(course.getCourseSections().size());
    dto.setSections(
        course.getCourseSections().stream().map(this.courseSectionMapper::toLightDto).collect(Collectors.toList())
    );
    registration.ifPresent(courseRegistration -> dto.setRegistration(this.courseRegistrationMapper.toDto(courseRegistration)));
    return dto;
  }
}
