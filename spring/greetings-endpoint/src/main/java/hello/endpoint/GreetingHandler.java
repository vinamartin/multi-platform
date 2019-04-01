package hello.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import hello.service.GreetingService;
import reactor.core.publisher.Mono;

/** Handler class for Spring Reactive WebFlux implementation. */
public class GreetingHandler {

  private final GreetingService greetingService;

  public GreetingHandler(GreetingService greetingService) {
    this.greetingService = greetingService;
  }

  public Mono<ServerResponse> greet(ServerRequest serverRequest) {
    String name =
        serverRequest
            .queryParam("name")
            .orElseThrow(() -> new IllegalArgumentException("Name missing!"));
    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(fromObject(greetingService.greet(name)));
  }
}
