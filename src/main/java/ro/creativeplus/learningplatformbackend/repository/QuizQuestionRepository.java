package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer> {
}
