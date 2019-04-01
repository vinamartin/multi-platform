package hello;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import hello.endpoint.GreetingController;
import hello.endpoint.GreetingHandler;
import hello.service.GreetingService;
import hello.service.impl.GreetingServiceImpl;
import io.micrometer.core.instrument.Metrics;
import quote.service.QuoteService;
import quote.service.impl.QuoteServiceImpl;

@Configuration
public class ApplicationContext {
  @Bean
  public Application application() {
    return new Application();
  }

  @Bean
  public QuoteService quoteService() {
    return new QuoteServiceImpl();
  }

  @Bean
  public GreetingService greetingService(QuoteService quoteService) {
    return new GreetingServiceImpl(quoteService, Metrics.globalRegistry);
  }

  // Sprint Web
  @Bean
  public GreetingController greetingController(GreetingService greetingService) {
    return new GreetingController(greetingService);
  }

  // Spring Reactive WebFlux
  // @Bean
  public GreetingHandler greetingHandler(GreetingService greetingService) {
    return new GreetingHandler(greetingService);
  }

  // @Bean
  public RouterFunction<ServerResponse> greetingRoute(GreetingHandler greetingHandler) {
    return route(GET("/greeting").and(accept(MediaType.TEXT_PLAIN)), greetingHandler::greet);
  }
}
