package ro.creativeplus.learningplatformbackend.exception;

import java.util.HashMap;

public class WrongQuizAnswerException extends RuntimeException {
  private final HashMap<Integer, Boolean> correctQuestionAnswers;

  public WrongQuizAnswerException(String message, HashMap<Integer, Boolean> correctQuestionAnswers) {
    super(message);
    this.correctQuestionAnswers = correctQuestionAnswers;
  }

  public HashMap<Integer, Boolean> getCorrectQuestionAnswers() {
    return correctQuestionAnswers;
  }
}
