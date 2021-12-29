package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.QuizQuestionAnswer;

public interface QuizQuestionAnswerRepository extends JpaRepository<QuizQuestionAnswer, Integer> {
}
