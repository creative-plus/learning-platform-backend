package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.creativeplus.learningplatformbackend.model.courseSection.Quiz.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer> {
}
