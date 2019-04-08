package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hello.endpoint.GreetingController;
import hello.service.GreetingService;
import hello.service.impl.GreetingServiceImpl;
import io.micrometer.core.instrument.Metrics;
import quote.service.QuoteService;
import quote.service.impl.QuoteServiceImpl;

/**
 * Main Spring Boot application class. Uses Spring's <a
 * href="https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-java">Java
 * Bean Configuration</a>. If needed, configuration can be split up between by creating other
 * classes and annotating them with {@code @Configuration}.
 */
@SpringBootApplication
@SuppressWarnings("unused")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /** @return main application bean */
  @Bean
  public Application app() {
    return new Application();
  }

  /** @return {@link QuoteService} instance to inject in {@link GreetingServiceImpl}. */
  @Bean
  public QuoteService quoteService() {
    return new QuoteServiceImpl();
  }

  /** @return {@link GreetingService} instance to inject into {@link GreetingController}. */
  @Bean
  public GreetingService greetingService(QuoteService quoteService) {
    return new GreetingServiceImpl(quoteService, Metrics.globalRegistry);
  }

  /**
   * Creates the {@link GreetingController} bean that exposes the greeting service's REST endpoint.
   *
   * @param greetingService {@link QuoteService} to inject into {@link GreetingController}
   * @return {@link GreetingController} instance that exposes the REST endpoint
   */
  @Bean
  public GreetingController greetingController(GreetingService greetingService) {
    return new GreetingController(greetingService);
  }
}
