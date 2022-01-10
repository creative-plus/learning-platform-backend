package ro.creativeplus.learningplatformbackend.utils;

import ro.creativeplus.learningplatformbackend.model.Leaderboard.LeaderboardTrainee;

import java.util.Comparator;

public class LeaderboardSorter implements Comparator<LeaderboardTrainee> {
  public int compare(LeaderboardTrainee a, LeaderboardTrainee b) {
    if (b.getAnswerPoints() == a.getAnswerPoints()) {
      return b.getSectionPoints() - a.getSectionPoints();
    }
    return b.getAnswerPoints() - a.getAnswerPoints();
  }
}
