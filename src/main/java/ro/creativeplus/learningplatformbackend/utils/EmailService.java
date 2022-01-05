package ro.creativeplus.learningplatformbackend.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.exception.EmailNotSentException;
import ro.creativeplus.learningplatformbackend.model.User.User;
import ro.creativeplus.learningplatformbackend.model.User.UserActivationToken;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class EmailService {

  private final JavaMailSender javaMailSender;

  EmailService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  public void sendSimpleMessage(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("change.this.later@example.org");
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    javaMailSender.send(message);
  }

  public void sendHtmlMessage(String to, String subject, String html) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      message.setFrom("change.this.later@example.org");
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
