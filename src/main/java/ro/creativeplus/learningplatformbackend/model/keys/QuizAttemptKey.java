package ro.creativeplus.learningplatformbackend.model.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class QuizAttemptKey implements Serializable {

  public QuizAttemptKey() { }

  public QuizAttemptKey(int traineeId, int quizId, Timestamp timestamp) {
    this.traineeId = traineeId;
    this.quizId = quizId;
    this.timestamp = timestamp;
  }

  @Column(name = "trainee_id")
  int traineeId;

  @Column(name = "quiz_id")
  int quizId;

  @Column
  Timestamp timestamp;

  public int getTraineeId() {
    return traineeId;
  }

  public void setTraineeId(int traineeId) {
    this.traineeId = traineeId;
  }

  public int getQuizId() {
    return quizId;
  }

  public void setQuizId(int quizId) {
    this.quizId = quizId;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    QuizAttemptKey that = (QuizAttemptKey) o;
    return traineeId == that.traineeId && quizId == that.quizId && timestamp.getTime() == that.timestamp.getTime();
  }

  @Override
  public int hashCode() {
    return Objects.hash(traineeId, quizId, timestamp.getTime());
  }
}
