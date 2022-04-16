package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.exception.ObjectAlreadyExistsException;
import ro.creativeplus.learningplatformbackend.exception.ObjectNotFoundException;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;
import ro.creativeplus.learningplatformbackend.model.User.UserActivationToken;
import ro.creativeplus.learningplatformbackend.repository.TraineeRepository;
import ro.creativeplus.learningplatformbackend.utils.EmailService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TraineeService {

  private final TraineeRepository traineeRepository;
  private final UserActivationTokenService userActivationTokenService;
  private final EmailService emailService;

  TraineeService(TraineeRepository traineeRepository,
                 UserActivationTokenService userActivationTokenService,
                 EmailService emailService) {
    this.traineeRepository = traineeRepository;
    this.userActivationTokenService = userActivationTokenService;
    this.emailService = emailService;
  }

  public List<Trainee> getTrainees() {
    return this.traineeRepository.findAll();
  }

  public Trainee getTrainee(int id) {
    if(id <= 0) {
      throw new ObjectNotFoundException("Trainee does not exist.");
    }
    Optional<Trainee> existingTrainee = this.traineeRepository.findById(id);
    if(existingTrainee.isEmpty()) {
      throw new ObjectNotFoundException("Trainee does not exist.");
    }
    return existingTrainee.get();
  }

  @Transactional
  public Trainee addTrainee(Trainee trainee) {
    if(trainee.getId() > 0) {
      Optional<Trainee> existingTrainee = this.traineeRepository.findById(trainee.getId());
      if(existingTrainee.isPresent()) {
        throw new ObjectAlreadyExistsException("Trainee already exists.");
      }
    }
    if(trainee.getEmail() != null) {
      Optional<Trainee> existingTrainee = this.traineeRepository.findByEmail(trainee.getEmail());
      if(existingTrainee.isPresent()) {
        throw new ObjectAlreadyExistsException("Trainee already exists.");
      }
    }
    trainee.setPassword("");
    trainee.setActive(false);
    this.traineeRepository.save(trainee);
    UserActivationToken activationToken = this.userActivationTokenService.generateUserActivationToken(trainee);
    this.emailService.sendWelcomeMessage(trainee, activationToken);
    return trainee;
  }

  public Trainee editTrainee(Trainee trainee) {
    Trainee existingTrainee = this.getTrainee(trainee.getId());
    trainee.setPassword(existingTrainee.getPassword());
    trainee.setEmail(existingTrainee.getEmail());
    existingTrainee.setProjects(trainee.getProjects());
    return this.traineeRepository.save(trainee);
  }

  public void deleteTraineeById(int id) {
    Trainee existingTrainee = this.getTrainee(id);
    this.traineeRepository.delete(existingTrainee);
  }
}
