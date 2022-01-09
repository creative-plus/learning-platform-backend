package ro.creativeplus.learningplatformbackend.model.Leaderboard;

import ro.creativeplus.learningplatformbackend.model.User.Trainee;

public class LeaderboardTrainee {
  Trainee trainee;
  int sectionPoints;
  int answerPoints;

  public LeaderboardTrainee(Trainee trainee, int sectionPoints, int answerPoints) {
    this.trainee = trainee;
    this.sectionPoints = sectionPoints;
    this.answerPoints = answerPoints;
  }

  public Trainee getTrainee() {
    return trainee;
  }

  public void setTrainee(Trainee trainee) {
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
