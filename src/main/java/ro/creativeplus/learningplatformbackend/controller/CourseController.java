package ro.creativeplus.learningplatformbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseRequestDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseResponseDto;
import ro.creativeplus.learningplatformbackend.mapper.CourseMapper;
import ro.creativeplus.learningplatformbackend.model.Course;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.service.CourseService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

  private final CourseService courseService;
  private final CourseMapper courseMapper;

  CourseController(CourseService courseService, CourseMapper courseMapper) {
    this.courseService = courseService;
    this.courseMapper = courseMapper;
  }

  @GetMapping()
  public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
    return ResponseEntity.ok().body(
        this.courseService.getCourses().stream()
          .map(this.courseMapper::courseToCourseResponseDto)
          .collect(Collectors.toList())
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<CourseResponseDto> getCourse(@PathVariable int id) {
    Course result = this.courseService.getCourse(id);
    return ResponseEntity.ok().body(this.courseMapper.courseToCourseResponseDto(result));
  }

  @PostMapping()
  public ResponseEntity<CourseResponseDto> addCourse(@Valid @RequestBody CourseRequestDto dto) {
    Course course = this.courseMapper.toCourse(dto);
    Course result = this.courseService.addCourse(course);
    return ResponseEntity
        .created(URI.create("/courses/" + result.getId()))
        .body(this.courseMapper.courseToCourseResponseDto(result));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CourseResponseDto> editCourse(@PathVariable int id, @Valid @RequestBody CourseRequestDto dto) {
    Course course = this.courseMapper.toCourse(dto);
    course.setId(id);
    Course result = this.courseService.editCourse(course);
    return ResponseEntity.ok().body(this.courseMapper.courseToCourseResponseDto(result));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCourse(@PathVariable int id) {
    this.courseService.deleteCourseById(id);
    return ResponseEntity.noContent().build();
  }

//  @GetMapping("/student/{id}")
//  public List<CourseRegistration> getCoursesForStudent() {
//    this.courseService
//  }
}
