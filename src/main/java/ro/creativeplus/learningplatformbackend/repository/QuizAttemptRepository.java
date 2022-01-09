package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.creativeplus.learningplatformbackend.model.QuizAttempt;
import ro.creativeplus.learningplatformbackend.model.keys.QuizAttemptKey;

import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, QuizAttemptKey> {
  List<QuizAttempt> findAllByQuiz_IdAndTrainee_Id(int quizId, int traineeId);
  List<QuizAttempt> findAllByQuiz_Course_IdAndTrainee_Id(int courseId, int traineeId);
  List<QuizAttempt> findAllByTrainee_Id(int traineeId);
}
