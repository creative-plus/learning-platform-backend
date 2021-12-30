package ro.creativeplus.learningplatformbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.creativeplus.learningplatformbackend.dto.User.Trainee.TraineeRequestDto;
import ro.creativeplus.learningplatformbackend.dto.User.Trainee.TraineeResponseDto;
import ro.creativeplus.learningplatformbackend.mapper.TraineeMapper;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;
import ro.creativeplus.learningplatformbackend.service.TraineeService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trainees")
@RolesAllowed("ROLE_TRAINER")
public class TraineeController {

  private final TraineeMapper traineeMapper;
  private final TraineeService traineeService;

  TraineeController(TraineeMapper traineeMapper, TraineeService traineeService) {
    this.traineeMapper = traineeMapper;
    this.traineeService = traineeService;
  }

  @GetMapping
  public ResponseEntity<List<TraineeResponseDto>> getTrainees() {
    return ResponseEntity.ok().body(
        this.traineeService.getTrainees().stream()
          .map(this.traineeMapper::toDto)
          .collect(Collectors.toList())
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<TraineeResponseDto> getTrainee(@PathVariable int id) {
    return ResponseEntity.ok().body(
        this.traineeMapper.toDto(this.traineeService.getTrainee(id))
    );
  }

  @PostMapping
  public ResponseEntity<TraineeResponseDto> addTrainee(@Valid @RequestBody TraineeRequestDto traineeDto) {
    Trainee trainee = this.traineeMapper.toTrainee(traineeDto);
    Trainee result = this.traineeService.addTrainee(trainee);
    return ResponseEntity
        .created(URI.create("/trainees/" + result.getId()))
        .body(this.traineeMapper.toDto(result));
  }

  @PutMapping("/{id}")
  public ResponseEntity<TraineeResponseDto> editTrainee(@PathVariable int id, @Valid @RequestBody TraineeRequestDto traineeDto) {
    Trainee trainee = this.traineeMapper.toTrainee(traineeDto);
    trainee.setId(id);
    Trainee result = this.traineeService.editTrainee(trainee);
    return ResponseEntity.ok().body(this.traineeMapper.toDto(result));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteTrainee(@PathVariable int id) {
    this.traineeService.deleteTraineeById(id);
    return ResponseEntity.noContent().build();
  }


}
