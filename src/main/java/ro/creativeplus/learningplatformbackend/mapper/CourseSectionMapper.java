package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.CourseSectionRequestDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.CourseSectionResponseDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.LearningResponseDto;
import ro.creativeplus.learningplatformbackend.model.CourseSection;
import ro.creativeplus.learningplatformbackend.model.courseSection.Learning;
import ro.creativeplus.learningplatformbackend.model.courseSection.Quiz.Quiz;

import java.util.Objects;

@Component
public class CourseSectionMapper {
  public CourseSectionResponseDto courseSectionToCourseSectionResponseDto(CourseSection courseSection) {
    CourseSectionResponseDto dto = new CourseSectionResponseDto();
    if (courseSection instanceof Learning) {
      dto = new LearningResponseDto();
      ((LearningResponseDto) dto).setContent(((Learning) courseSection).getContent());
    } else if(courseSection instanceof Quiz) {
      // pass
      dto = new LearningResponseDto();
    }
    dto.setId(courseSection.getId());
    dto.setTitle(courseSection.getTitle());
    dto.setOrder(courseSection.getOrderInCourse());
    return dto;
  }

  public CourseSection courseSectionRequestDtoToCourseSection(CourseSectionRequestDto dto) {
    CourseSection courseSection = new CourseSection();
    if(Objects.equals(dto.getType(), "learning")) {
      courseSection = new Learning();
      ((Learning) courseSection).setContent(dto.getContent());
    } else if(Objects.equals(dto.getType(), "quiz")) {
      // pass
    }
    courseSection.setId(dto.getId());
    courseSection.setTitle(dto.getTitle());
    courseSection.setOrderInCourse(dto.getOrder());
    System.out.println(courseSection instanceof Learning);
    return courseSection;
  }
}
