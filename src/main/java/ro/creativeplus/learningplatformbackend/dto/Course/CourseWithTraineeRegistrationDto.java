package ro.creativeplus.learningplatformbackend.dto.Course;

import ro.creativeplus.learningplatformbackend.dto.Course.CourseRegistration.CourseRegistrationDto;

public class CourseWithTraineeRegistrationDto extends CourseResponseDto {
  CourseRegistrationDto registration;

  public CourseRegistrationDto getRegistration() {
    return registration;
  }

  public void setRegistration(CourseRegistrationDto registration) {
    this.registration = registration;
  }
}
