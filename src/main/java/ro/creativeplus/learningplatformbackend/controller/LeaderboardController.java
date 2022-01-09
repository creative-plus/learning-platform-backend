package ro.creativeplus.learningplatformbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.creativeplus.learningplatformbackend.dto.LeaderboardTraineeDto;
import ro.creativeplus.learningplatformbackend.mapper.LeaderboardTraineeMapper;
import ro.creativeplus.learningplatformbackend.model.Leaderboard.LeaderboardTrainee;
import ro.creativeplus.learningplatformbackend.service.LeaderboardService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

  private final LeaderboardService leaderboardService;
  private final LeaderboardTraineeMapper leaderboardTraineeMapper;

  public LeaderboardController(LeaderboardService leaderboardService,
                               LeaderboardTraineeMapper leaderboardTraineeMapper) {
    this.leaderboardService = leaderboardService;
    this.leaderboardTraineeMapper = leaderboardTraineeMapper;
  }

  @GetMapping
  ResponseEntity<List<LeaderboardTraineeDto>> getLeaderboard() {
    return ResponseEntity.ok().body(
      this.leaderboardService.getLeaderboard().stream()
        .map(this.leaderboardTraineeMapper::toDto)
        .collect(Collectors.toList())
    );
  }

  @GetMapping("/project/{projectId}")
  ResponseEntity<List<LeaderboardTraineeDto>> getLeaderboardForProject(@PathVariable int projectId) {
    return ResponseEntity.ok().body(
      this.leaderboardService.getLeaderboardForProject(projectId).stream()
        .map(this.leaderboardTraineeMapper::toDto)
        .collect(Collectors.toList())
    );
  }

  @GetMapping("/course/{courseId}")
  ResponseEntity<List<LeaderboardTraineeDto>> getLeaderboardForCourse(@PathVariable int courseId) {
    return ResponseEntity.ok().body(
      this.leaderboardService.getLeaderboardForCourse(courseId).stream()
        .map(this.leaderboardTraineeMapper::toDto)
        .collect(Collectors.toList())
    );
  }

  @GetMapping("/project/{projectId}/course/{courseId}")
  ResponseEntity<List<LeaderboardTraineeDto>> getLeaderboardForProjectAndCourse(@PathVariable int projectId,
                                                                                @PathVariable int courseId) {
    return ResponseEntity.ok().body(
      this.leaderboardService.getLeaderboardForProjectAndCourse(projectId, courseId).stream()
        .map(this.leaderboardTraineeMapper::toDto)
        .collect(Collectors.toList())
    );
  }
}
