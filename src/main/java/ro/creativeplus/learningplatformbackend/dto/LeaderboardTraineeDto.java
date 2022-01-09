package ro.creativeplus.learningplatformbackend.dto;

import ro.creativeplus.learningplatformbackend.dto.User.Trainee.TraineeResponseDto;

public class LeaderboardTraineeDto {
  TraineeResponseDto trainee;
  int sectionPoints;
  int answerPoints;

  public TraineeResponseDto getTrainee() {
    return trainee;
  }

  public void setTrainee(TraineeResponseDto trainee) {
    this.trainee = trainee;
  }

  public int getSectionPoints() {
    return sectionPoints;
  }

  public void setSectionPoints(int sectionPoints) {
    this.sectionPoints = sectionPoints;
  }

  public int getAnswerPoints() {
    return answerPoints;
  }

  public void setAnswerPoints(int answerPoints) {
    this.answerPoints = answerPoints;
  }
}
