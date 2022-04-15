package ro.creativeplus.learningplatformbackend.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.config.AppConfig;
import ro.creativeplus.learningplatformbackend.exception.EmailNotSentException;
import ro.creativeplus.learningplatformbackend.model.User.User;
import ro.creativeplus.learningplatformbackend.model.User.UserActivationToken;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class EmailService {

  private final JavaMailSender javaMailSender;
  private final AppConfig appConfig;

  EmailService(JavaMailSender javaMailSender, AppConfig appConfig) {
    this.javaMailSender = javaMailSender;
    this.appConfig = appConfig;
  }

  public void sendSimpleMessage(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(this.appConfig.getMailFromAddress());
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    javaMailSender.send(message);
  }

  public void sendHtmlMessage(String to, String subject, String html) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      message.setFrom(this.appConfig.getMailFromAddress());
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
      message.setSubject(subject);
      message.setContent(html, "text/html");
      javaMailSender.send(message);
    } catch (Exception err) {
      throw new EmailNotSentException("Email not sent.");
    }
  }

  public void sendWelcomeMessage(User user, UserActivationToken token) {
    // This is junk, we'll come back later to this.
    String html = "<html>" +
        "Hello, " + user.getFirstName() + " " + user.getLastName() + "!<br>" +
        "Here is your token: " + token.getToken() +
        "</html>";
    sendHtmlMessage(user.getEmail(), "Welcome to the training platform!", html);
  }
}
