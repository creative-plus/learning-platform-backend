package ro.creativeplus.learningplatformbackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "learning")
public class AppConfig {
  private String mailFromAddress;
  private String frontendUrl;

  public String getMailFromAddress() {
    return mailFromAddress;
  }

  public void setMailFromAddress(String mailFromAddress) {
    this.mailFromAddress = mailFromAddress;
  }

  public String getFrontendUrl() {
    return frontendUrl;
  }

  public void setFrontendUrl(String frontendUrl) {
    this.frontendUrl = frontendUrl;
  }
}
