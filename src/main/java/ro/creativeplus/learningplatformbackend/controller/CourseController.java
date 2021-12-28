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
  @PostMapping()
  public ResponseEntity<CourseResponseDto> addCourse(@Valid @RequestBody CourseRequestDto dto) {
    Course course = this.courseMapper.toCourse(dto);
    Course result = this.courseService.addCourse(course);
    return ResponseEntity.ok().body(this.courseMapper.courseToCourseResponseDto(result));
  }

//  @GetMapping("/student/{id}")
//  public List<CourseRegistration> getCoursesForStudent() {
//    this.courseService
//  }
}
