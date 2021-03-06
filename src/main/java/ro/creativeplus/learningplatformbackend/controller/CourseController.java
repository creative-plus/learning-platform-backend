package ro.creativeplus.learningplatformbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseRequestDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseResponseDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.CourseSectionResponseDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseWithTraineeRegistrationDto;
import ro.creativeplus.learningplatformbackend.mapper.CourseMapper;
import ro.creativeplus.learningplatformbackend.mapper.CourseSectionMapper;
import ro.creativeplus.learningplatformbackend.model.Course;
import ro.creativeplus.learningplatformbackend.model.CourseSection.CourseSection;
import ro.creativeplus.learningplatformbackend.model.auth.AuthUser;
import ro.creativeplus.learningplatformbackend.model.keys.CourseRegistrationKey;
import ro.creativeplus.learningplatformbackend.service.AuthService;
import ro.creativeplus.learningplatformbackend.service.CourseRegistrationService;
import ro.creativeplus.learningplatformbackend.service.CourseService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

  private final CourseService courseService;
  private final CourseRegistrationService courseRegistrationService;
  private final AuthService authService;
  private final CourseMapper courseMapper;
  private final CourseSectionMapper courseSectionMapper;


  CourseController(CourseService courseService, CourseRegistrationService courseRegistrationService,
                   AuthService authService, CourseMapper courseMapper, CourseSectionMapper courseSectionMapper) {
    this.courseService = courseService;
    this.courseRegistrationService = courseRegistrationService;
    this.authService = authService;
    this.courseMapper = courseMapper;
    this.courseSectionMapper = courseSectionMapper;
  }

  @GetMapping()
  public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
    return ResponseEntity.ok().body(
        this.courseService.getCourses().stream()
          .map(this.courseMapper::toResponseDto)
          .collect(Collectors.toList())
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<CourseResponseDto> getCourse(@PathVariable int id) {
    Course result = this.courseService.getCourse(id);
    AuthUser authUser = this.authService.getCurrentUser();
    boolean hideSectionContent = Objects.equals(authUser.getType(), "trainee");
    return ResponseEntity.ok().body(this.courseMapper.toResponseDto(result, hideSectionContent));
  }

  @PostMapping()
  public ResponseEntity<CourseResponseDto> addCourse(@Valid @RequestBody CourseRequestDto dto) {
    Course course = this.courseMapper.toCourse(dto);
    Course result = this.courseService.addCourse(course);
    return ResponseEntity
        .created(URI.create("/courses/" + result.getId()))
        .body(this.courseMapper.toResponseDto(result));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CourseResponseDto> editCourse(@PathVariable int id, @Valid @RequestBody CourseRequestDto dto) {
    Course course = this.courseMapper.toCourse(dto);
    course.setId(id);
    Course result = this.courseService.editCourse(course);
    return ResponseEntity.ok().body(this.courseMapper.toResponseDto(result));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCourse(@PathVariable int id) {
    this.courseService.deleteCourseById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{courseId}/sections/{sectionId}")
  public ResponseEntity<CourseSectionResponseDto> viewCourseSection(@PathVariable int courseId,
                                                                    @PathVariable int sectionId) {
    AuthUser authUser = this.authService.getCurrentUser();
    CourseRegistrationKey key = new CourseRegistrationKey(authUser.getId(), courseId);
    CourseSection result = this.courseRegistrationService.viewCourseSection(key, sectionId);
    return ResponseEntity.ok().body(
        this.courseSectionMapper.toResponseDto(result, true)
    );
  }

  @GetMapping("/with-enrollment")
  public ResponseEntity<List<CourseWithTraineeRegistrationDto>> getAllCoursesForTrainee() {
    int traineeId = this.authService.getCurrentUser().getId();
    return ResponseEntity.ok().body(this.courseRegistrationService.getAllCoursesForTrainee(traineeId));
  }

//  @GetMapping("/student/{id}")
//  public List<CourseRegistration> getCoursesForStudent() {
//    this.courseService
//  }
}
