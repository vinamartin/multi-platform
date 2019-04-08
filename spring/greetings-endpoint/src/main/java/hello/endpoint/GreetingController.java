package hello.endpoint;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.immutables.value.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

import hello.service.Greeting;
import hello.service.GreetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Spring Web controller class that exposes the {@code /greeting} REST endpoint. This class' purpose
 * is to convert incoming REST requests into calls to the underlying {@link GreetingService}
 * instance.
 *
 * <p>It also provides the REST endpoint documentation using Swagger 2 annotations.
 */
@Api(value = "Greetings API")
@RestController
public class GreetingController {

  private static final String NAME_TOO_LONG =
      "Bad request: 'name' is null, empty, or more than "
          + GreetingService.MAX_NAME_LENGTH
          + " characters.";

  private final GreetingService greetingService;

  /**
   * Constructs a new REST Spring Web controller.
   *
   * @param greetingService service where requests will be forwarded
   */
  public GreetingController(GreetingService greetingService) {
    Preconditions.checkNotNull(greetingService, "GreetingService cannot be null");
    this.greetingService = greetingService;
  }

  /**
   * Gets a new greeting message using the name provided.
   *
   * @param name name to add to the greeting
   * @return new greeting model that will be serialized out to JSON
   */
  @SuppressWarnings("unused")
  @ApiOperation(value = "Generates a greeting", response = GreetingModel.class)
  @ApiResponses(value = {@ApiResponse(code = 400, message = NAME_TOO_LONG)})
  @GetMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public GreetingModel greeting(
      @RequestParam(value = "name")
          @ApiParam(value = "Name to add to the greeting", required = true)
          @Valid
          @Size(min = 1, max = GreetingService.MAX_NAME_LENGTH)
          String name) {
    return ImmutableGreetingModel.builder().from(greetingService.greet(name)).build();
  }

  /**
   * Class that documents and defines how greetings will be returned and serialized to JSON in the
   * responses. Extends from the existing {@link Greeting} interface to add Swagger 2 documentation.
   */
  @ApiModel(description = "Greeting")
  @Value.Immutable
  public interface GreetingModel extends Greeting {

    @Override
    @ApiModelProperty("Greeting's unique ID")
    long getId();

    @Override
    @ApiModelProperty("Greeting's content")
    String getContent();
  }
}
