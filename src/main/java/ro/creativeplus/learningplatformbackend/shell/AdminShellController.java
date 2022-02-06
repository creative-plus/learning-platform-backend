package ro.creativeplus.learningplatformbackend.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AdminShellController {
  @ShellMethod("Add a trainer")
  public String addTrainer(String email, String password, String fullName) {
    return email + " " + password;
  }
}
