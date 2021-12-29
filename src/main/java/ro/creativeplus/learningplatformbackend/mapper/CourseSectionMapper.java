package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.CourseSectionRequestDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.CourseSectionResponseDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.LearningResponseDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizQuestionAnswerResponseDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizQuestionResponseDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizResponseDto;
import ro.creativeplus.learningplatformbackend.model.CourseSection;
import ro.creativeplus.learningplatformbackend.model.courseSection.Learning;
import ro.creativeplus.learningplatformbackend.model.courseSection.Quiz.Quiz;
import ro.creativeplus.learningplatformbackend.model.courseSection.Quiz.QuizQuestion;
import ro.creativeplus.learningplatformbackend.model.courseSection.Quiz.QuizQuestionAnswer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CourseSectionMapper {
  public CourseSectionResponseDto courseSectionToCourseSectionResponseDto(CourseSection courseSection) {
    return courseSectionToCourseSectionResponseDto(courseSection, false);
  }
  public CourseSectionResponseDto courseSectionToCourseSectionResponseDto(CourseSection courseSection, boolean hideAnswers) {
    CourseSectionResponseDto dto = new CourseSectionResponseDto();
    if (courseSection instanceof Learning) {
      dto = new LearningResponseDto();
      ((LearningResponseDto) dto).setContent(((Learning) courseSection).getContent());
    } else if(courseSection instanceof Quiz) {
      dto = new QuizResponseDto();
      List<QuizQuestionResponseDto> questionsDto = ((Quiz) courseSection).getQuizQuestions().stream()
          .map(question -> {
            QuizQuestionResponseDto questionDto = new QuizQuestionResponseDto();
            questionDto.setText(question.getText());
            questionDto.setMultipleAnswer(question.isMultipleAnswer());
            List<QuizQuestionAnswerResponseDto> answersDto = question.getAnswers().stream()
                .map(answer -> {
                  QuizQuestionAnswerResponseDto answerDto = new QuizQuestionAnswerResponseDto();
                  answerDto.setText(answer.getText());
                  if(!hideAnswers) {
                    answerDto.setCorrect(answer.isCorrect());
                  }
                  return answerDto;
                })
                .collect(Collectors.toList());
            questionDto.setAnswers(answersDto);
            return questionDto;
          })
          .collect(Collectors.toList());
      ((QuizResponseDto) dto).setQuestions(questionsDto);
    }
    dto.setId(courseSection.getId());
    dto.setTitle(courseSection.getTitle());
    dto.setOrder(courseSection.getOrderInCourse());
    return dto;
  }

  public CourseSection courseSectionRequestDtoToCourseSection(CourseSectionRequestDto dto) {
    CourseSection courseSection = new CourseSection();
    if(Objects.equals(dto.getType(), "learning")) {
      courseSection = new Learning();
      ((Learning) courseSection).setContent(dto.getContent());
    } else if(Objects.equals(dto.getType(), "quiz")) {
      courseSection = new Quiz();
      List<QuizQuestion> questions = dto.getQuestions().stream()
          .map(questionDto -> {
            QuizQuestion question = new QuizQuestion();
            question.setText(questionDto.getText());
            question.setMultipleAnswer(questionDto.isMultipleAnswer());
            List<QuizQuestionAnswer> answers = questionDto.getAnswers().stream()
                .map(answerDto -> {
                  QuizQuestionAnswer answer = new QuizQuestionAnswer();
                  answer.setText(answerDto.getText());
                  answer.setCorrect(answerDto.isCorrect());
                  return answer;
                })
                .collect(Collectors.toList());
            question.setAnswers(answers);
            return question;
          })
          .collect(Collectors.toList());
      ((Quiz) courseSection).setQuizQuestions(questions);
    }
    courseSection.setId(dto.getId());
    courseSection.setTitle(dto.getTitle());
    courseSection.setOrderInCourse(dto.getOrder());
    System.out.println(courseSection instanceof Learning);
    return courseSection;
  }
}
