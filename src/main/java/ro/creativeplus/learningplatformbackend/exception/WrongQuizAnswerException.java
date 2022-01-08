package ro.creativeplus.learningplatformbackend.exception;

import java.util.HashMap;

public class WrongQuizAnswerException extends RuntimeException {
  private final HashMap<Integer, Boolean> correctQuestionAnswers;
  private final Integer remainingAttempts;
  private final boolean showCorrectQuestionAnswers;

  public WrongQuizAnswerException(String message, HashMap<Integer, Boolean> correctQuestionAnswers) {
    this(message, correctQuestionAnswers, null, false);
  }

  public WrongQuizAnswerException(String message, HashMap<Integer, Boolean> correctQuestionAnswers,
                                  Integer remainingAttempts, boolean showCorrectQuestionAnswers) {
    super(message);
    this.correctQuestionAnswers = correctQuestionAnswers;
    this.remainingAttempts = remainingAttempts;
    this.showCorrectQuestionAnswers = showCorrectQuestionAnswers;
  }

  public HashMap<Integer, Boolean> getCorrectQuestionAnswers() {
    return correctQuestionAnswers;
  }

  public Integer getRemainingAttempts() {
    return remainingAttempts;
  }

  public boolean isShowCorrectQuestionAnswers() {
    return showCorrectQuestionAnswers;
  }
}
