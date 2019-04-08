package quote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import quote.endpoint.QuoteController;
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

  /** @return {@link QuoteService} instance to inject into {@link QuoteController}. */
  @Bean
  public QuoteService quoteService() {
    return new QuoteServiceImpl();
  }

  /**
   * Creates the {@link QuoteController} bean that exposes the quote service's REST endpoint.
   *
   * @param quoteService {@link QuoteService} to inject into {@link QuoteController}
   * @return {@link QuoteController} instance that exposes the REST endpoint
   */
  @Bean
  public QuoteController quoteController(QuoteService quoteService) {
    return new QuoteController(quoteService);
  }
}
