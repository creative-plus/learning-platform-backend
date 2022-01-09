package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.LeaderboardTraineeDto;
import ro.creativeplus.learningplatformbackend.model.Leaderboard.LeaderboardTrainee;

@Component
public class LeaderboardTraineeMapper {

  private final TraineeMapper traineeMapper;

  public LeaderboardTraineeMapper(TraineeMapper traineeMapper) {
    this.traineeMapper = traineeMapper;
  }

  public LeaderboardTraineeDto toDto(LeaderboardTrainee leaderboardTrainee) {
    LeaderboardTraineeDto dto = new LeaderboardTraineeDto();
    dto.setTrainee(this.traineeMapper.toDto(leaderboardTrainee.getTrainee()));
    dto.setSectionPoints(leaderboardTrainee.getSectionPoints());
    dto.setAnswerPoints(leaderboardTrainee.getAnswerPoints());
    return dto;
  }
}
