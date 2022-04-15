package ro.creativeplus.learningplatformbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ro.creativeplus.learningplatformbackend.config.AppConfig;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class LearningPlatformBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningPlatformBackendApplication.class, args);
    }

}
