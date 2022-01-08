package ro.creativeplus.learningplatformbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseProgressDto;
import ro.creativeplus.learningplatformbackend.mapper.CourseProgressMapper;
import ro.creativeplus.learningplatformbackend.model.CourseProgress;
import ro.creativeplus.learningplatformbackend.service.CourseProgressService;

@RestController
@RequestMapping("/courses/{courseId}/progress")
public class CourseProgressController {
  private final CourseProgressService courseProgressService;
  private final CourseProgressMapper courseProgressMapper;

  public CourseProgressController(CourseProgressService courseProgressService, CourseProgressMapper courseProgressMapper) {
    this.courseProgressService = courseProgressService;
    this.courseProgressMapper = courseProgressMapper;
  }

  @GetMapping("/{traineeId}")
  ResponseEntity<CourseProgressDto> getProgressForTrainee(@PathVariable int courseId, @PathVariable int traineeId) {
    CourseProgress courseProgress = this.courseProgressService.getCourseProgress(courseId, traineeId);
    return ResponseEntity.ok().body(this.courseProgressMapper.toDto(courseProgress));
  }
}
