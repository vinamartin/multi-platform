package quote;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import quote.endpoint.QuoteController;
import quote.endpoint.QuoteHandler;
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

  // Sprint Web
  @Bean
  public QuoteController quoteController(QuoteService quoteService) {
    return new QuoteController(quoteService);
  }

  // Spring Reactive WebFlux
  // @Bean
  public QuoteHandler quoteHandler(QuoteService quoteService) {
    return new QuoteHandler(quoteService);
  }

  // @Bean
  public RouterFunction<ServerResponse> quoteRoute(QuoteHandler quoteHandler) {
    return route(GET("/quote"), quoteHandler::quote);
  }
}
