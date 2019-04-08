package hello.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import hello.service.GreetingService;
import reactor.core.publisher.Mono;

/**
 * Spring Reactive WebFlux handler class that exposes the {@code /greeting} REST endpoint. This
 * class' purpose is to convert incoming REST requests into calls to the underlying {@link
 * GreetingService} instance.
 *
 * <p>Note: This class is only for illustration purposes and is not currently used by any of the
 * Spring applications.
 */
public class GreetingHandler {

  private final GreetingService greetingService;

  /**
   * Constructs a new REST Reactive WebFlux handler.
   *
   * @param greetingService service where requests will be forwarded
   */
  public GreetingHandler(GreetingService greetingService) {
    this.greetingService = greetingService;
  }

  /**
   * Gets a new greeting message using the name provided.
   *
   * @param serverRequest content of the GET request. Must contain a name, which cannot be {@code
   *     null}, empty, or have more than {@value GreetingService#MAX_NAME_LENGTH} characters.
   * @return new {@link hello.service.Greeting} object that will be serialized out to JSON
   */
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
