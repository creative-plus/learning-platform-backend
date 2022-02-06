# Learning Platform Backend
An API a learning platform, where trainers can add courses and trainees can attend them.


### Features
1. Trainers add projects in the platform.
2. Trainers add trainees in the platform.
3. Trainers add courses, containing sections that can be either learning sections or quizez.
4. Trainees attend courses, and have the possibility of seeing their progress.
5. Trainers can see the progress of each trainee individually.
6. Trainers and trainees can see leaderboards, showing who did best in each course/project or overall.
7. Trainers can add media files, like images, PDF files, etc.

### Business requirements
1. Projects have a name, a start date and an end date.
2. Trainees can be in multiple projects, at least one project is required for the trainee to be in.
3. Each course must have at least one section.
   - For quiz sections, each quiz must have at least one question;
   - Each question must have at least two answers. Questions can have a single correct answer or multiple correct answers;
   - A question is considered correctly answered if the given answers match the correct answer set perfectly (no partially correct answers accepted);
   - Each quiz section has a threshold, a positive number representing the least correctly answered questions needed for the quiz to be considered passed.
4. Trainees are able to see the course list and may enroll to each course they want.
   - Enrollments are saved in the database along with the dates when the trainee started and finished the course, and each section they passed.
   - After the enrollment, a trainee can only see the sections in order.
   - If the section is a quiz, trainees may pass the section following the given rules:
     - If all the questions are answered correctly, the trainee can pass to the next section.
     - If the quiz threshold is reached, the trainee is given a second try and can see what questions were answered incorrectly.
     - If the quiz threshold is not reached, the trainee is given a second try but can not see which questions were wrong.
     - After the second attempt, a trainee may pass the quiz with no respect to the correcly answered questions.
     - Each quiz attempt is stored with the timestamp and the number of correcly answered questions.
5. The trainee progress can be queried at any time by the trainers and will include the start/end dates and each section the trainee passed, 
   and quiz attempts for each course.
6. Trainee leaderboards are defined as shown below:
   - There are two types of points: section points and quiz points:
     - Section points represent the number of sections the trainee has passed (including quizez);
     - Quiz points represent the number of correcly answered questions given on the quizez;
   - The leaderboard is sorted descending by quiz points and then section points;
   - Leaderboards are aggregated either overall, or by project, or by course.
7. Media files can be uploaded by trainers, and trainees can only see them. The exposed IDs mush be hashed, so they cannot be seen sequentially.
