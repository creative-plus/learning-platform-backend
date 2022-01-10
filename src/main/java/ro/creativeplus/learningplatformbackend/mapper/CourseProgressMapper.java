package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseProgressDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizAttemptDto;
import ro.creativeplus.learningplatformbackend.model.CourseProgress;

import java.util.stream.Collectors;

@Component
public class CourseProgressMapper {
  private final CourseRegistrationMapper courseRegistrationMapper;
  private final CourseMapper courseMapper;

  public CourseProgressMapper(CourseRegistrationMapper courseRegistrationMapper, CourseMapper courseMapper) {
    this.courseRegistrationMapper = courseRegistrationMapper;
    this.courseMapper = courseMapper;
  }

  public CourseProgressDto toDto(CourseProgress courseProgress) {
    CourseProgressDto dto = new CourseProgressDto();
    dto.setRegistration(this.courseRegistrationMapper.toDto(courseProgress.getCourseRegistration()));
    dto.setCourse(this.courseMapper.toResponseDto(courseProgress.getCourse(), true));
    dto.setQuizAttempts(
        courseProgress.getQuizAttempts().stream().map(QuizAttemptDto::new).collect(Collectors.toList())
    );
    return dto;
  }
}
