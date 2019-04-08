package hello.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hello.service.Greeting;
import hello.service.GreetingService;

/** Class that exposes the Greetings REST endpoint from an OSGi container. */
public class GreetingRestEndpoint {
  private static final Logger LOGGER = LoggerFactory.getLogger(GreetingRestEndpoint.class);

  private final GreetingService greetingService;

  /**
   * Constructs a new REST endpoint for the Greetings service.
   *
   * @param greetingService service where requests will be forwarded
   */
  public GreetingRestEndpoint(GreetingService greetingService) {
    this.greetingService = greetingService;
  }

  /**
   * Gets a new greeting message using the name provided.
   *
   * @param name name to add to the greeting
   * @return greeting message
   */
  @GET
  @Path("/")
  public String greet(@QueryParam("name") String name) throws JsonProcessingException {
    LOGGER.info("greet \"{}\" called", name);
    ObjectMapper objectMapper = new ObjectMapper();
    Greeting greeting = greetingService.greet(name);
    return objectMapper.writeValueAsString(greeting);
  }
}
