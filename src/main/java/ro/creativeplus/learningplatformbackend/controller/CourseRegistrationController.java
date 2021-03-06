package ro.creativeplus.learningplatformbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseRegistration.CourseRegistrationDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizGivenAnswers.QuizGivenAnswersDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizGivenAnswers.QuizGivenQuestionAnswerDto;
import ro.creativeplus.learningplatformbackend.mapper.CourseRegistrationMapper;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.model.auth.AuthUser;
import ro.creativeplus.learningplatformbackend.model.keys.CourseRegistrationKey;
import ro.creativeplus.learningplatformbackend.service.AuthService;
import ro.creativeplus.learningplatformbackend.service.CourseRegistrationService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/courses/{courseId}/enrollment")
public class CourseRegistrationController {

  private final CourseRegistrationService courseRegistrationService;
  private final AuthService authService;
  private final CourseRegistrationMapper mapper;

  CourseRegistrationController(CourseRegistrationService courseRegistrationService, AuthService authService,
                               CourseRegistrationMapper mapper) {
    this.courseRegistrationService = courseRegistrationService;
    this.authService = authService;
    this.mapper = mapper;
  }

  @GetMapping()
  public ResponseEntity<CourseRegistrationDto> getCourseRegistrationForTrainee(@PathVariable int courseId) {
    AuthUser authUser = this.authService.getCurrentUser();
    CourseRegistration result = this.courseRegistrationService.getCourseRegistration(
        new CourseRegistrationKey(authUser.getId(), courseId)
    );
    return ResponseEntity.ok().body(this.mapper.toDto(result));
  }

  @PostMapping()
  public ResponseEntity<CourseRegistrationDto> enrollToCourse(@PathVariable int courseId) {
    AuthUser authUser = this.authService.getCurrentUser();
    CourseRegistration result = this.courseRegistrationService.enrollToCourse(
        new CourseRegistrationKey(authUser.getId(), courseId)
    );
    return ResponseEntity.ok().body(this.mapper.toDto(result));
  }

  @PostMapping("/sections/{sectionId}")
  public ResponseEntity<CourseRegistrationDto> passSection(
      @PathVariable int courseId, @PathVariable int sectionId,
      @Valid @RequestBody(required = false) Optional<QuizGivenAnswersDto> quizGivenAnswersDto) {
    AuthUser authUser = this.authService.getCurrentUser();
    CourseRegistration result = this.courseRegistrationService.markSectionAsViewed(
        new CourseRegistrationKey(authUser.getId(), courseId),
        sectionId,
        quizGivenAnswersDto
    );
    return ResponseEntity.ok().body(this.mapper.toDto(result));
  }

}
