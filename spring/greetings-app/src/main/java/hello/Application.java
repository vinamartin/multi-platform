package hello;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import hello.endpoint.GreetingController;
import hello.endpoint.GreetingHandler;
import hello.service.GreetingService;
import hello.service.impl.GreetingServiceImpl;
import io.micrometer.core.instrument.Metrics;
import quote.client.QuoteClientImpl;
import quote.service.QuoteService;

/**
 * Main Spring Boot application class. Uses Spring's <a
 * href="https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-java">Java
 * Bean Configuration</a>. If needed, configuration can be split up between by creating other
 * classes and annotating them with {@code @Configuration}.
 */
@SpringBootApplication
@SuppressWarnings("unused")
public class Application {
  /**
   * URL of the Quote service used to get quotes. Injected from the environment by Spring. See <a
   * href="https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html">Spring's
   * Externalized Configuration</a>
   */
  @Value("${services.quote.url}")
  private URL quoteServiceUrl;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /** @return main application bean */
  @Bean
  public Application app() {
    return new Application();
  }

  /**
   * @return {@link QuoteClientImpl} instance to use to communicate with the remote {@link
   *     QuoteService}.
   */
  @Bean
  public QuoteService quoteService() {
    return new QuoteClientImpl(quoteServiceUrl);
  }

  /** @return {@link GreetingService} instance to inject into {@link GreetingController}. */
  @Bean
  public GreetingService greetingService(QuoteService quoteService) {
    return new GreetingServiceImpl(quoteService, Metrics.globalRegistry);
  }

  // Sprint Web

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

  // Spring Reactive WebFlux

  // Uncomment the @Bean annotations on greetingHandler() and greetingRoute() and comment out
  // the @Bean annotations on greetingController() to use Spring Reactive WebFlux instead of Spring
  // Web.

  // @Bean
  public GreetingHandler greetingHandler(GreetingService greetingService) {
    return new GreetingHandler(greetingService);
  }

  // @Bean
  public RouterFunction<ServerResponse> greetingRoute(GreetingHandler greetingHandler) {
    return route(GET("/greeting").and(accept(MediaType.TEXT_PLAIN)), greetingHandler::greet);
  }
}
