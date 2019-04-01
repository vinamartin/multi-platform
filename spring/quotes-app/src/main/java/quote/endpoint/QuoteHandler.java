package quote.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import quote.service.QuoteService;
import reactor.core.publisher.Mono;

/** Handler class for Spring Reactive WebFlux implementation. */
public class QuoteHandler {
  private final QuoteService quoteService;

  public QuoteHandler(QuoteService quoteService) {
    this.quoteService = quoteService;
  }

  public Mono<ServerResponse> quote(ServerRequest serverRequest) {
    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(fromObject(quoteService.getQuote()));
  }
}
