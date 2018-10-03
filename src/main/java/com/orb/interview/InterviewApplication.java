package com.orb.interview;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main class for interview application
 */
@SpringBootApplication
@EnableSwagger2
public class InterviewApplication {
  public static void main(String[] args) {
    SpringApplication.run(InterviewApplication.class, args);
  }

  @Configuration
  public static class ApplicationConfiguration
      implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    @Value("${interview.server.port}")
    private int interviewServerPort;

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
      factory.setPort(interviewServerPort);
    }
  }
}
