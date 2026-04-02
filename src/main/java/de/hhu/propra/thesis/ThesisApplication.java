package de.hhu.propra.thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories("de.hhu.propra.thesis.infrastructurelayer.reposimplementation")
public class ThesisApplication {
  public static void main(String[] args) {
    SpringApplication.run(ThesisApplication.class, args);
  }
}

